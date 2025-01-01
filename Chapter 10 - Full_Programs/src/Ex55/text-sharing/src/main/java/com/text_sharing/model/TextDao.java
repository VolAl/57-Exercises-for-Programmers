package com.text_sharing.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TextDao implements Serializable {

    private String textIdHash;
    private List<TextElement> textList;
    private String textUrl;

    public TextDao(String textIdHash, List<TextElement> textList, String textUrl) {
        this.textIdHash = textIdHash;
        this.textList = textList;
        this.textUrl = textUrl;
    }

    public String getTextIdHash() {
        return textIdHash;
    }

    public void setTextIdHash(String textIdHash) {
        this.textIdHash = textIdHash;
    }

    public List<TextElement> getTextList() {
        return textList;
    }

    public void setTextList(List<TextElement> textList) {
        this.textList = textList;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }
}