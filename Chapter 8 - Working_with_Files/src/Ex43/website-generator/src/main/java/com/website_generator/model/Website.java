package com.website_generator.model;

public class Website {
    private String siteName;
    private String author;
    private boolean jsFolder;
    private boolean cssFolder;

    public Website(String siteName, String author, boolean jsFolder, boolean cssFolder) {
        this.siteName = siteName;
        this.author = author;
        this.jsFolder = jsFolder;
        this.cssFolder = cssFolder;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isJsFolder() {
        return jsFolder;
    }

    public void setJsFolder(boolean jsFolder) {
        this.jsFolder = jsFolder;
    }

    public boolean isCssFolder() {
        return cssFolder;
    }

    public void setCssFolder(boolean cssFolder) {
        this.cssFolder = cssFolder;
    }
}
