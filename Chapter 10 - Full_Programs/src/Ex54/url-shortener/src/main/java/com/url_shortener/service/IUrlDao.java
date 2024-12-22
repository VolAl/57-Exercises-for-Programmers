package com.url_shortener.service;

import com.url_shortener.model.UrlDao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface IUrlDao {
    void saveUrl(UrlDao urlDao);
    UrlDao getOneUrl(Integer id);
    void updateUrl(UrlDao urlDao);
    Map<Integer, UrlDao> getAllUrls();
    void deleteUrl(Integer id);
    void saveAllUrls(Map<Integer, UrlDao> map);
}
