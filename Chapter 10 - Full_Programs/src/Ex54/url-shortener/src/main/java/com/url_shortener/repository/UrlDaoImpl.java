package com.url_shortener.repository;

import com.url_shortener.service.IUrlDao;
import com.url_shortener.model.UrlDao;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UrlDaoImpl implements IUrlDao {

    private final String hashReference= "Url";

    @Resource(name="redisTemplate")
    private HashOperations<String, Integer, UrlDao> hashOperations;


    @Override
    public void saveUrl(UrlDao urlDao) {
        hashOperations.putIfAbsent(hashReference, urlDao.getUrlId(), urlDao);
    }

    @Override
    public UrlDao getOneUrl(Integer id) {
        return hashOperations.get(hashReference, id);
    }

    @Override
    public void updateUrl(UrlDao urlDao) {
        hashOperations.put(hashReference, urlDao.getUrlId(), urlDao);
    }

    @Override
    public Map<Integer, UrlDao> getAllUrls() {
        Map<Integer, UrlDao> map = hashOperations.entries(hashReference);

        List<Map.Entry<Integer, UrlDao>> list = new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparing(t -> t.getValue().getUrlId()));

        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    @Override
    public void deleteUrl(Integer id) {
        hashOperations.delete(hashReference, id);
    }

    @Override
    public void saveAllUrls(Map<Integer, UrlDao> map) {
        hashOperations.putAll(hashReference, map);
    }
}
