package com.project.capstone.service;

import org.deckfour.xes.in.XesXmlParser;
import org.deckfour.xes.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

@Service
public class XmlConverter {
    public XLog service(MultipartFile userfile) {

        try {
            File file = File.createTempFile("upload-", ".xes");
            userfile.transferTo(file);
            XesXmlParser parser = new XesXmlParser();

            if (parser.canParse(file)) {
                List<XLog> logs = parser.parse(file);
                XLog log = logs.get(0); // Assuming there is at least one log in the file
                return log;
            } else {
                System.out.println("The file cannot be parsed as an XES file.");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
