package com.text_sharing.model;

import java.io.Serializable;

public class TextElement implements Serializable {
    private Integer textId;
    private String textContent;

    public TextElement(Integer textId, String textContent) {
        this.textId = textId;
        this.textContent = textContent;
    }

    public Integer getTextId() {
        return textId;
    }

    public void setTextId(Integer textId) {
        this.textId = textId;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
