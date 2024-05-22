package com.project.capstone.service;

import org.deckfour.xes.model.XAttributeMap;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.springframework.stereotype.Service;

@Service
public class StoreXes {
    private Long traceId = 0L;
    private Long eventId = 0L;
    public void service(XLog log) {
        for (XTrace trace : log) {
            traceId++; //db에 사용될 기본키

            for (XEvent event : trace) {
                eventId++; // db에 사용될 기본키
                XAttributeMap eventAttributes = event.getAttributes();

            }
        }
    }
}
