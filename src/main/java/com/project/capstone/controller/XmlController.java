package com.project.capstone.controller;

import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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

            XLog log = xmlConverter.service(userinfo);
            if (log == null) {
                model.addAttribute("error", "파일을 파싱할 수 없습니다.");
                logger.error("Failed to parse the file.");
                return "error";
            }

            CompositeAttribute compositeAttribute = globalAttributesAndClassifer.service(log);
            logger.info("Composite Attribute: " + compositeAttribute);

            List<Map<String, String>> globalTraceAttributes = new ArrayList<>();
            List<Map<String, String>> globalEventAttributes = new ArrayList<>();
            List<Map<String, String>> classifiers = new ArrayList<>();

            // Populate the lists with attributes and log them
            for (XTrace trace : log) {
                for (Map.Entry<String, XAttribute> entry : trace.getAttributes().entrySet()) {
                    Map<String, String> attributeMap = new HashMap<>();
                    attributeMap.put("key", entry.getKey());
                    attributeMap.put("value", entry.getValue().toString());
                    attributeMap.put("type", entry.getValue().getClass().getSimpleName());
                    globalTraceAttributes.add(attributeMap);
                }
            }
            logger.info("Global Trace Attributes: " + globalTraceAttributes);

            for (XTrace trace : log) {
                for (XEvent event : trace) {
                    for (Map.Entry<String, XAttribute> entry : event.getAttributes().entrySet()) {
                        Map<String, String> attributeMap = new HashMap<>();
                        attributeMap.put("key", entry.getKey());
                        attributeMap.put("value", entry.getValue().toString());
                        attributeMap.put("type", entry.getValue().getClass().getSimpleName());
                        globalEventAttributes.add(attributeMap);
                    }
                }
            }
            logger.info("Global Event Attributes: " + globalEventAttributes);

            for (XAttribute attribute : log.getGlobalEventAttributes()) {
                Map<String, String> classifierMap = new HashMap<>();
                classifierMap.put("key", attribute.getKey());
                classifierMap.put("value", attribute.toString());
                classifierMap.put("type", attribute.getClass().getSimpleName());
                classifiers.add(classifierMap);
            }
            logger.info("Classifiers: " + classifiers);

            model.addAttribute("globalTraceSelectAttributes", globalTraceAttributes);
            model.addAttribute("globalEventSelectAttributes", globalEventAttributes);
            model.addAttribute("classifiers", classifiers);

            return "data-view";

        } catch (Exception e) {
            logger.error("Error processing file: ", e);
            return "upload";
        }
    }
}