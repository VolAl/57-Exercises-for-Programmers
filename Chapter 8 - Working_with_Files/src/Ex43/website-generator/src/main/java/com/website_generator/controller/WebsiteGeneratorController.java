package com.website_generator.controller;

import com.website_generator.model.Website;
import com.website_generator.service.WebGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/website-generator")
public class WebsiteGeneratorController {

    @Autowired
    private WebGeneratorService webGeneratorService;

    @ModelAttribute("website")
    public Website getWebsiteObject() {
        return new Website("","",false,false);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String handleRequest() {
        return "website-generator";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView processSubmit(@ModelAttribute("website") Website website){
        webGeneratorService.downloadZip(website);
        ModelAndView mv = new ModelAndView();
        mv.addObject("website", website);
        mv.addObject("message", "The zip folder is in your Downloads");
        mv.addObject("alertClass", "alert-success");
        return mv;
    }
}
