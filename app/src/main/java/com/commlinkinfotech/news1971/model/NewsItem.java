package com.commlinkinfotech.news1971.model;

import java.util.ArrayList;

/**
 * Created by CommlinkT on 12/12/2016.
 */

public class NewsItem {

    String id, title, short_text, long_text, featured_image_url, news_url;
    ArrayList<String> images;
    NEWS_TYPE news_type;

    public NewsItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShort_text() {
        return short_text;
    }

    public void setShort_text(String short_text) {
        this.short_text = short_text;
    }

    public String getLong_text() {
        return long_text;
    }

    public void setLong_text(String long_text) {
        this.long_text = long_text;
    }

    public String getFeatured_image_url() {
        return featured_image_url;
    }

    public void setFeatured_image_url(String featured_image_url) {
        this.featured_image_url = featured_image_url;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public NEWS_TYPE getNews_type() {
        return news_type;
    }

    public void setNews_type(NEWS_TYPE news_type) {
        this.news_type = news_type;
    }

    public enum NEWS_TYPE{
        FIRST_LEVEL_TOP_NEWS,
        SECOND_LEVEL_TOP_NEWS,
        BREAKING_NEWS
    }
}
