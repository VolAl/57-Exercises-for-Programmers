package com.text_sharing.service;

import com.text_sharing.model.TextDao;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface ITextDao {
    void saveText(TextDao textDao);
    TextDao getOneText(String id);
    void updateText(TextDao textDao);
    Map<String, TextDao> getAllTexts();
    void deleteText(String id);
    void deleteAllTexts(Map<String, TextDao> map);
    void saveAllTexts(Map<String, TextDao> map);
}
