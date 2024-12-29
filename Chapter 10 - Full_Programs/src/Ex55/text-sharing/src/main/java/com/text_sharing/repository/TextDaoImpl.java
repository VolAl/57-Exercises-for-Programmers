package com.text_sharing.repository;

import com.text_sharing.model.TextDao;
import com.text_sharing.service.ITextDao;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TextDaoImpl implements ITextDao {

    private final String hashReference= "TextShare";

    @Resource(name="redisTemplate")
    private HashOperations<String, String, TextDao> hashOperations;


    @Override
    public void saveText(TextDao textDao) {
        hashOperations.putIfAbsent(hashReference, textDao.getTextIdHash(), textDao);
    }

    @Override
    public TextDao getOneText(String id) {
        return hashOperations.get(hashReference, id);
    }

    @Override
    public void updateText(TextDao textDao) {
        hashOperations.put(hashReference, textDao.getTextIdHash(), textDao);
    }

    @Override
    public Map<String, TextDao> getAllTexts() {
        Map<String, TextDao> map = hashOperations.entries(hashReference);

        List<Map.Entry<String, TextDao>> list = new LinkedList<>(map.entrySet());
        list.sort(Comparator.comparing(t -> t.getValue().getTextIdHash()));

        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
    }

    @Override
    public void deleteText(String id) {
        hashOperations.delete(hashReference, id);
    }

    @Override
    public void deleteAllTexts(Map<String, TextDao> map) {
        for(Map.Entry<String, TextDao> entry : map.entrySet()) {
            this.deleteText(entry.getValue().getTextIdHash());
        }
    }

    @Override
    public void saveAllTexts(Map<String, TextDao> map) {
        hashOperations.putAll(hashReference, map);
    }
}
