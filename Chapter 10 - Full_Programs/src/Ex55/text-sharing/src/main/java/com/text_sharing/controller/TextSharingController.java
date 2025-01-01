package com.text_sharing.controller;

import com.text_sharing.model.TextDao;
import com.text_sharing.service.TextSharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/text-sharing")
public class TextSharingController {

    @Autowired
    private TextSharingService textSharingService;

    @RequestMapping(value = "/save",
            method= RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String saveText(@RequestBody TextDao textJSon) {
        String textUrl = textSharingService.saveText(textJSon);
        return "{\"textUrl\": \"" + textUrl + "\"}";
    }

    @RequestMapping(value = "/{hashedId}",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TextDao getTextDao(@PathVariable String hashedId) {
        return textSharingService.getTextDao(hashedId);
    }
}
