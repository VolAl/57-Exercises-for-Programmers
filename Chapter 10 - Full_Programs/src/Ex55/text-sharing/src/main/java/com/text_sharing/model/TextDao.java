package com.text_sharing.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TextDao implements Serializable {

    private String textIdHash;
    private String text;
    private String textUrl;

    public TextDao(String textIdHash, String text, String textUrl) {
        this.textIdHash = textIdHash;
        this.text = text;
        this.textUrl = textUrl;
    }

    public String getTextIdHash() {
        return textIdHash;
    }

    public void setTextIdHash(String textIdHash) {
        this.textIdHash = textIdHash;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTextUrl() {
        return textUrl;
    }

    public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
    }
}
