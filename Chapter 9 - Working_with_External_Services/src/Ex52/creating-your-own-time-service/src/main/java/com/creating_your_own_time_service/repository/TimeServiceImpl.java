package com.creating_your_own_time_service.repository;

import com.creating_your_own_time_service.service.TimeService;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class TimeServiceImpl implements TimeService {
    @Override
    public String getOwnTime() {
        Date time = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return "{\"currentTime\": \"" + sdf.format(time) + "\"}";
    }
}
