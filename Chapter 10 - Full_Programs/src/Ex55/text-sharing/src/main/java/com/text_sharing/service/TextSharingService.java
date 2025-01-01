package com.text_sharing.service;

import com.text_sharing.model.TextDao;
import org.springframework.stereotype.Service;

@Service
public interface TextSharingService {

    String saveText(TextDao textDao);
    TextDao getTextDao(String id);
}
