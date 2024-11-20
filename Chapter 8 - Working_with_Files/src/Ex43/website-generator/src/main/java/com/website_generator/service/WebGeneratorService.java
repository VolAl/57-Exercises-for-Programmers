package com.website_generator.service;

import com.website_generator.model.Website;
import org.springframework.stereotype.Service;

@Service
public interface WebGeneratorService {

    public void downloadZip(Website website);
}
