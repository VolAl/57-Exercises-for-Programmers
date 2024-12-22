package com.url_shortener.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UrlDao implements Serializable {

    private Integer urlId;
    private String shortUrl;
    private String longUrl;
    private ShortUrlStats shortUrlStats;

    public UrlDao(Integer urlId, String shortUrl, String longUrl, ShortUrlStats shortUrlStats) {
        this.urlId = urlId;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.shortUrlStats = shortUrlStats;
    }

    public Integer getUrlId() {
        return urlId;
    }

    public void setUrlId(Integer urlId) {
        this.urlId = urlId;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public ShortUrlStats getShortUrlStats() {
        return shortUrlStats;
    }

    public void setShortUrlStats(ShortUrlStats shortUrlStats) {
        this.shortUrlStats = shortUrlStats;
    }
}
