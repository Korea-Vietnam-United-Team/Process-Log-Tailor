package com.project.capstone.service;

import org.deckfour.xes.model.XLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.capstone.data.CompositeAttribute;

@Service
public class GlobalAttributesAndClassifer {
    @Autowired
    CompositeAttribute compositeAttribute;
    public CompositeAttribute service(XLog log) {
        compositeAttribute.setGlobalTraceAttributes(log.getGlobalTraceAttributes());
        compositeAttribute.setGlobalEventAttributes(log.getGlobalEventAttributes());
        compositeAttribute.setClassifiers(log.getClassifiers());
        return compositeAttribute;
    }
}
