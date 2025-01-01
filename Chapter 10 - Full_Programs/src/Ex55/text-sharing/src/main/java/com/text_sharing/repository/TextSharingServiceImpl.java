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
    public String saveText(TextDao textDao) {
        Map<String, TextDao> allUrls = iTextDao.getAllTexts();
        int textIndex = 0;
        String textUrl = "";
        for(TextDao t : allUrls.values()) {
            if(t.getText().equals(textDao.getText())) {
                textDao = t;
                textUrl = t.getTextUrl();
                break;
            }
            textIndex++;
        }

        if(textUrl.isEmpty()) {
            String md5Hex = DigestUtils.md5Hex(textDao.getText().replace(" ", "-") + "-" + textIndex).toUpperCase();
            textUrl = "http://localhost:4200/text-view/" + md5Hex;
            textDao = new TextDao(md5Hex, textDao.getText(), textUrl);
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
