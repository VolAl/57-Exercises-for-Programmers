package com.creating_your_own_time_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.creating_your_own_time_service.service.TimeService;

@Controller
@RequestMapping("/time-service")
public class TimeServiceController {

    @Autowired
    private TimeService timeService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String handleRequest(){
        return timeService.getOwnTime();
    }
}
