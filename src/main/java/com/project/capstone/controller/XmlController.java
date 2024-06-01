package com.project.capstone.controller;

import org.deckfour.xes.in.XParserRegistry;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.extension.XExtensionManager;
import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.extension.std.XOrganizationalExtension;
import org.deckfour.xes.classification.XEventClassifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import com.project.capstone.data.CompositeAttribute;
import com.project.capstone.service.GlobalAttributesAndClassifer;
import com.project.capstone.service.XmlConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class XmlController {

    private static final Logger logger = LoggerFactory.getLogger(XmlController.class);

    private final XmlConverter xmlConverter;
    private final GlobalAttributesAndClassifer globalAttributesAndClassifer;

    @Autowired
    public XmlController(XmlConverter xmlConverter, GlobalAttributesAndClassifer globalAttributesAndClassifer) {
        this.xmlConverter = xmlConverter;
        this.globalAttributesAndClassifer = globalAttributesAndClassifer;
    }

    private XLog log;  // Lưu trữ log như biến lớp
    private List<Map<String, String>> globalTraceAttributes;
    private List<Map<String, String>> globalEventAttributes;
    private List<Map<String, String>> classifiers;

    @RequestMapping(value = {"/", "/home"})
    public String home() {
        return "home";
    }

    @PostMapping("/upload")
    public String getfileInfo(@RequestParam("xesFile") MultipartFile userinfo, Model model) {
        if (userinfo == null || userinfo.isEmpty()) {
            throw new RuntimeException("파일 객체가 null이거나 비어 있습니다.");
        }

        try {
            logger.info("Received file: " + userinfo.getOriginalFilename());

            // Đăng ký các extension XES
            XExtensionManager.instance().register(XConceptExtension.instance());
            XExtensionManager.instance().register(XTimeExtension.instance());
            XExtensionManager.instance().register(XOrganizationalExtension.instance());

            // Phân tích cú pháp tệp XES
            log = xmlConverter.service(userinfo);
            if (log == null) {
                model.addAttribute("error", "파일을 파싱할 수 없습니다.");
                logger.error("Failed to parse the file.");
                return "error";
            }

            CompositeAttribute compositeAttribute = globalAttributesAndClassifer.service(log);
            logger.info("Composite Attribute: " + compositeAttribute);

            globalTraceAttributes = new ArrayList<>();
            globalEventAttributes = new ArrayList<>();
            classifiers = new ArrayList<>();

            // Lấy thuộc tính toàn cục của trace và log chúng
            for (XAttribute attribute : log.getGlobalTraceAttributes()) {
                Map<String, String> attributeMap = new HashMap<>();
                attributeMap.put("key", attribute.getKey());
                attributeMap.put("value", attribute.toString());
                attributeMap.put("type", attribute.getClass().getSimpleName());
                globalTraceAttributes.add(attributeMap);
            }
            logger.info("Global Trace Attributes: " + globalTraceAttributes);

            // Lấy thuộc tính toàn cục của event và log chúng
            for (XAttribute attribute : log.getGlobalEventAttributes()) {
                Map<String, String> attributeMap = new HashMap<>();
                attributeMap.put("key", attribute.getKey());
                attributeMap.put("value", attribute.toString());
                attributeMap.put("type", attribute.getClass().getSimpleName());
                globalEventAttributes.add(attributeMap);
            }
            logger.info("Global Event Attributes: " + globalEventAttributes);

            // Lấy các classifier và log chúng
            for (XEventClassifier classifier : log.getClassifiers()) {
                Map<String, String> classifierMap = new HashMap<>();
                classifierMap.put("key", classifier.name());
                classifierMap.put("value", classifier.getDefiningAttributeKeys().toString());
                classifierMap.put("type", classifier.getClass().getSimpleName());
                classifiers.add(classifierMap);
            }
            logger.info("Classifiers: " + classifiers);

            // Pass data to select-operation page to select an event
            List<String> eventNames = new ArrayList<>();
            for (XTrace trace : log) {
                for (XEvent event : trace) {
                    eventNames.add(event.getAttributes().get(XConceptExtension.KEY_NAME).toString());
                }
            }
            model.addAttribute("eventNames", eventNames);

            return "select-operation";

        } catch (Exception e) {
            logger.error("Error processing file: ", e);
            return "upload";
        }
    }

    @GetMapping("/event")
    public String getEventDetails(@RequestParam("eventName") String eventName, Model model) {
        Map<String, String> eventAttributes = new HashMap<>();
        for (XTrace trace : log) {
            for (XEvent event : trace) {
                if (eventName.equals(event.getAttributes().get(XConceptExtension.KEY_NAME).toString())) {
                    for (Map.Entry<String, XAttribute> entry : event.getAttributes().entrySet()) {
                        eventAttributes.put(entry.getKey(), entry.getValue().toString());
                    }
                    break;
                }
            }
        }
        model.addAttribute("eventAttributes", eventAttributes);
        model.addAttribute("globalTraceSelectAttributes", globalTraceAttributes);
        model.addAttribute("globalEventSelectAttributes", globalEventAttributes);
        model.addAttribute("classifiers", classifiers);
        return "data-view";
    }
}
