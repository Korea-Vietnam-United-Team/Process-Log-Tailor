package com.project.xesprocessor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trace;
    private String event;
    private String timestamp;
    private String performer;
    private String lifecycleTransition;
    private String sequence;

    // Default constructor
    public EventLog() {
    }

    // Constructor with parameters
    public EventLog(String trace, String event, String timestamp, String performer, String lifecycleTransition) {
        this.trace = trace;
        this.event = event;
        this.timestamp = timestamp;
        this.performer = performer;
        this.lifecycleTransition = lifecycleTransition;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public String getLifecycleTransition() {
        return lifecycleTransition;
    }

    public void setLifecycleTransition(String lifecycleTransition) {
        this.lifecycleTransition = lifecycleTransition;
    }

    public String getSequence() {
        return trace + " ==> " + event; // Example implementation
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public List<String> getEvents() {
        List<String> events = new ArrayList<>();
        events.add(event);
        return events;
    }

    public long getDuration(EventLog nextEvent) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime thisEventTime = LocalDateTime.parse(this.timestamp, formatter);
        LocalDateTime nextEventTime = LocalDateTime.parse(nextEvent.getTimestamp(), formatter);
        return Duration.between(thisEventTime, nextEventTime).toMinutes(); // Example: duration in minutes
    }
}
