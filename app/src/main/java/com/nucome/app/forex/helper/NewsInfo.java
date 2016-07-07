package com.nucome.app.forex.helper;

import java.io.Serializable;

/**
 * Created by david on 4/29/2016.
 */
public class NewsInfo implements Serializable {
    public String title;
    public String description;
    public String pubDate;
    public String newsSource;

    public NewsInfo(String title, String description, String pubDate, String newsSource) {
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.newsSource = newsSource;
    }
}
