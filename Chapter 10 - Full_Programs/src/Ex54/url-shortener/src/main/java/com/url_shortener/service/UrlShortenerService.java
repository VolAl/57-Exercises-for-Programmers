package com.url_shortener.service;

import org.springframework.stereotype.Service;

@Service
public interface UrlShortenerService {

    String longUrlToShortUrl(String longURL);
    String shortUrlToLongUrl(String shortURL);
}
