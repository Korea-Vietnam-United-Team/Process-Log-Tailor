package com.project.capstone.controller;

import org.deckfour.xes.model.XLog;
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

@Controller
public class XmlController {

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
        if (userinfo == null) {
            throw new RuntimeException("파일 객체가 null입니다.");
        }
        if (userinfo.isEmpty()) {
            throw new RuntimeException("업로드된 파일이 비어 있습니다.");
        }
        XLog log = xmlConverter.service(userinfo); // 파싱
        CompositeAttribute compositeAttribute = globalAttributesAndClassifer.service(log); // 속성값 추출


        model.addAttribute("dataModel", compositeAttribute);

        return "data-view";
    }


}
