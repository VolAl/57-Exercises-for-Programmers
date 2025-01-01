package com.text_sharing.repository;

import com.text_sharing.model.TextDao;
import com.text_sharing.service.ITextDao;
import com.text_sharing.service.TextSharingService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class TextSharingServiceImpl implements TextSharingService {

    @Autowired
    private ITextDao iTextDao;

    @Override
    public String saveOrUpdateText(TextDao textDao) {
        Map<String, TextDao> allTexts = iTextDao.getAllTexts();
        int textIndex = 0;
        String textUrl = "";
        for(TextDao t : allTexts.values()) {
            if(t.getTextUrl().equals(textDao.getTextUrl())) {
                textUrl = t.getTextUrl();
                break;
            }
            textIndex++;
        }

        if(textUrl.isEmpty()) {
            String textContent = textDao.getTextList().getFirst().getTextContent();
            String md5Hex = DigestUtils.md5Hex(textContent.replace(" ", "-") + "-" + textIndex).toUpperCase();
            textUrl = "http://localhost:4200/text-view/" + md5Hex;
            textDao = new TextDao(md5Hex, textDao.getTextList(), textUrl);
            iTextDao.saveText(textDao);
        } else {
            iTextDao.updateText(textDao);
        }
        return textUrl;
    }

    @Override
    public TextDao getTextDao(String id) {
        return iTextDao.getOneText(id);
    }
}
