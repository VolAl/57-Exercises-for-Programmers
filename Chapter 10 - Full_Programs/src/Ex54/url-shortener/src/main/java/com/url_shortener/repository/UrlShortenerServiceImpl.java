package com.url_shortener.repository;

import com.url_shortener.service.IUrlDao;
import com.url_shortener.model.ShortUrlStats;
import com.url_shortener.model.UrlDao;
import com.url_shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@Repository
public class UrlShortenerServiceImpl implements UrlShortenerService {

    @Autowired
    private IUrlDao iUrlDao;

    @Override
    public String longUrlToShortUrl(String longUrl) {
        Map<Integer, UrlDao> allUrls = iUrlDao.getAllUrls();
        int urlIndex = 0;
        String shortenedURL = "";
        for(UrlDao u : allUrls.values()) {
            if(u.getLongUrl().equals(longUrl)) {
               shortenedURL = u.getShortUrl();
               break;
            }
            if (urlIndex <= u.getUrlId()) {
                urlIndex = u.getUrlId() + 1;
            }
        }

        if (shortenedURL.isEmpty()) {
            String baseString = "http://localhost:8080/url-shortener/";
            shortenedURL = baseString + urlIndex;

            iUrlDao.saveUrl(new UrlDao(urlIndex, shortenedURL, longUrl, new ShortUrlStats(0, LocalDateTime.now())));
        }

        return "{\"shortUrl\": \"" + shortenedURL + "\"}";
    }

    @Override
    public String shortUrlToLongUrl(String id) {
        UrlDao urlDao = iUrlDao.getOneUrl(Integer.valueOf(id));
        urlDao.setShortUrlStats(new ShortUrlStats(urlDao.getShortUrlStats().getTimesUrlHasBeenAccessed() + 1, LocalDateTime.now()));
        iUrlDao.updateUrl(urlDao);

        return urlDao.getLongUrl();
    }
}
