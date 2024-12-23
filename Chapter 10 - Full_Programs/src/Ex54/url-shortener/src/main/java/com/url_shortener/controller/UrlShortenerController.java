package com.url_shortener.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.url_shortener.model.UrlDao;
import com.url_shortener.service.IUrlDao;
import com.url_shortener.service.UrlShortenerService;
import com.url_shortener.util.URLValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;

@Controller
@RequestMapping("/url-shortener")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;
    @Autowired
    private IUrlDao iUrlDao;

    @RequestMapping(value = "/shortener",
            method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shortenUrl(@RequestBody UrlDao urlJson) throws Exception {
        String longUrl = urlJson.getLongUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl)) {
            return urlShortenerService.longUrlToShortUrl(longUrl);
        }
        throw new Exception("Please enter a valid URL");
    }

    @RequestMapping(value = "/{id}",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String redirectUrl(@PathVariable String id, HttpServletResponse response) throws Exception {
        String longUrl = urlShortenerService.shortUrlToLongUrl(id);
        response.sendRedirect(longUrl);
        return "{\"longUrl\": \"" + longUrl + "\"}";
    }

    @RequestMapping(value = "/{id}/stats",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shortUrlStats(@PathVariable String id) {
        UrlDao urlDao = iUrlDao.getOneUrl(Integer.parseInt(id));
        return "{\"longUrl\": \"" + urlDao.getLongUrl()
               + "\", \"shortUrl\": \"" + urlDao.getShortUrl()
               + "\", \"timesShortUrlHasBeenAccessed\": \"" + urlDao.getShortUrlStats().getTimesUrlHasBeenAccessed() + "\"}";
    }
}

@Setter
@Getter
class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

}
