package com.flickrphotosearchapp.models;

import android.graphics.drawable.Drawable;

public class SubjectData {

    private String name;
    private String link;
    private String image;

    public SubjectData(String name, String link, String image) {
        this.name = name;
        this.link = link;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
