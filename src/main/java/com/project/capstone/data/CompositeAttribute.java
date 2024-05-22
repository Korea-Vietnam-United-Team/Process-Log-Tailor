package com.project.capstone.data;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.deckfour.xes.classification.XEventClassifier;
import org.deckfour.xes.model.XAttribute;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Scope(value = "prototype")
@Component
@Getter
public class CompositeAttribute {
    private List<XAttribute> globalTraceAttributes;
    private List<XAttribute> globalEventAttributes;
    private List<XEventClassifier> classifiers;

    public void setGlobalTraceAttributes(List<XAttribute> globalTraceAttributes) {
        this.globalTraceAttributes = globalTraceAttributes;
    }

    public void setGlobalEventAttributes(List<XAttribute> globalEventAttributes) {
        this.globalEventAttributes = globalEventAttributes;
    }

    public void setClassifiers(List<XEventClassifier> classifiers) {
        this.classifiers = classifiers;

    }

    private String key;
    private String value;
    private String type;

    @Override
    public String toString() {
        return "CompositeAttribute{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}
