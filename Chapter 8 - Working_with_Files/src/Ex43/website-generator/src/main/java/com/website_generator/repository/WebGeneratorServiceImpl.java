package com.website_generator.repository;

import com.website_generator.model.Website;
import com.website_generator.service.WebGeneratorService;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Repository
public class WebGeneratorServiceImpl implements WebGeneratorService {

    @Override
    public void downloadZip(Website website) {

        String siteName = website.getSiteName();
        String author = website.getAuthor();
        boolean jsFolder = website.isJsFolder();
        boolean cssFolder = website.isCssFolder();

        File f = new File(System.getProperty("user.home") + "\\Downloads\\" + siteName + ".zip");
        ZipOutputStream out;
        try {
            String sb = "<!DOCTYPE html>" + "<html>" + "<head>" + "<meta>" + author + "</meta><title>" + siteName + "</title>" + "</head><body></body></html>";

            out = new ZipOutputStream(new FileOutputStream(f));
            ZipEntry e = new ZipEntry("index.html");
            out.putNextEntry(e);
            byte[] data = sb.getBytes();
            out.write(data, 0, data.length);
            out.closeEntry();

            if(jsFolder) {
                e = new ZipEntry("js/");
                out.putNextEntry(e);
                out.closeEntry();
            }
            if(cssFolder) {
                e = new ZipEntry("css/");
                out.putNextEntry(e);
                out.closeEntry();
            }

            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
