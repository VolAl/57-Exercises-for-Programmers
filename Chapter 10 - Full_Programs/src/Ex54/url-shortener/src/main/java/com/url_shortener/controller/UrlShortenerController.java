package com.url_shortener.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.url_shortener.model.ShortUrlStats;
import com.url_shortener.model.UrlDao;
import com.url_shortener.service.IUrlDao;
import com.url_shortener.service.UrlShortenerService;
import com.url_shortener.util.URLValidator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

@Controller
@RequestMapping("/url-shortener")
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;
    @Autowired
    private IUrlDao iUrlDao;

    @RequestMapping(value = "/shortener",
            method=RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shortenUrl(@RequestBody UrlDao urlJson) throws Exception {
        String longUrl = urlJson.getLongUrl();
        if (URLValidator.INSTANCE.validateURL(longUrl)) {
            return urlShortenerService.longUrlToShortUrl(longUrl);
        }
        throw new Exception("Please enter a valid URL");
    }

    @RequestMapping(value = "/{id}",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String redirectUrl(@PathVariable String id, HttpServletResponse response) throws Exception {
        String longUrl = urlShortenerService.shortUrlToLongUrl(id);
        response.sendRedirect(longUrl);
        return "{\"longUrl\": \"" + longUrl + "\"}";
    }

    @RequestMapping(value = "/{id}/stats",
            method=RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String shortUrlStats(@PathVariable String id) {
        UrlDao urlDao = iUrlDao.getOneUrl(Integer.parseInt(id));
        return "{\"longUrl\": \"" + urlDao.getLongUrl()
               + "\", \"shortUrl\": \"" + urlDao.getShortUrl()
               + "\", \"timesShortUrlHasBeenAccessed\": \"" + urlDao.getShortUrlStats().getTimesUrlHasBeenAccessed() + "\"}";
    }

    @RequestMapping(value = "/requestsGraph", method=RequestMethod.GET)
    public void requestsGraph() {
        Map<Integer, UrlDao> allUrls = iUrlDao.getAllUrls();

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Hourly Requests");

        for(Map.Entry<Integer, UrlDao> entry : allUrls.entrySet()) {
            ShortUrlStats shortUrlStats = entry.getValue().getShortUrlStats();
            Date date = Date.from(shortUrlStats.getLastAccessDateTime().atZone(ZoneId.systemDefault()).toInstant());
            Second lSecond = new Second(date);
            series.addOrUpdate(lSecond, shortUrlStats.getTimesUrlHasBeenAccessed());
        }
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Hourly Requests",
                "Hour",
                "Request",
                dataset,
                true,    // legend
                false,   // tooltips
                false);  // no URLs

        ChartPanel chartPanel = new ChartPanel( chart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );

        JFrame frame = new JFrame();
        frame.setSize(700, 500);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

@Setter
@Getter
class ShortenRequest{
    private String url;

    @JsonCreator
    public ShortenRequest() {

    }

    @JsonCreator
    public ShortenRequest(@JsonProperty("url") String url) {
        this.url = url;
    }

}
