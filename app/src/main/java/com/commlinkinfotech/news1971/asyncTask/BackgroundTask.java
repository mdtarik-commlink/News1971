package com.commlinkinfotech.news1971.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import com.commlinkinfotech.news1971.model.NewsItem;
import com.commlinkinfotech.news1971.model.NewsMenuItem;
import com.commlinkinfotech.news1971.utils.Constants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by tarik on 4/20/16.
 */

public class BackgroundTask extends AsyncTask<String, Integer, Object> {

    Context context;
    BackgroundTaskListener caller;
    int identifier;

    public BackgroundTask(Context context, BackgroundTaskListener caller, int identifier) {
        this.context = context;
        this.caller = caller;
        this.identifier = identifier;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        caller.onPreExecuteAsynctask(this.identifier);
    }

    @Override
    protected Object doInBackground(String... params) {

        Object result = null;

        if (this.identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_TOP_NEWS) {
            try {
                ArrayList<NewsItem> topNews = new ArrayList<>();
                Document doc = Jsoup.connect(Constants.WEB_SERVICE.BASE_URL).get();
                Elements topSectionSlider = doc.select("div.front-page-top-section div.widget_slider_area div.widget_slider_area_rotate").get(0).select("div.single-slide");
                for (Element singleSlide : topSectionSlider) {
                    Element news = singleSlide.select("a").get(0);
                    String link = news.attr("href");
                    String title = news.attr("title");
                    String imageLink = news.select("img").get(0).attr("src");

                    NewsItem newsItem = new NewsItem();
                    newsItem.setTitle(title);
                    newsItem.setNews_url(link);
                    newsItem.setFeatured_image_url(imageLink);
                    newsItem.setNews_type(NewsItem.NEWS_TYPE.FIRST_LEVEL_TOP_NEWS);
                    topNews.add(newsItem);
                }

                Elements besideSlider = doc.select("div.front-page-top-section div.widget_beside_slider div.widget_highlighted_post_area").get(0).select("div.single-article figure");
                for (Element singleArticle : besideSlider) {
                    Element news = singleArticle.select("a").get(0);
                    String link = news.attr("href");
                    String title = news.attr("title");
                    String imageLink = news.select("img").get(0).attr("src");

                    NewsItem newsItem = new NewsItem();
                    newsItem.setTitle(title);
                    newsItem.setNews_url(link);
                    newsItem.setFeatured_image_url(imageLink);
                    newsItem.setNews_type(NewsItem.NEWS_TYPE.SECOND_LEVEL_TOP_NEWS);
                    topNews.add(newsItem);
                }
                result = topNews;

            } catch (Exception e) {
                //Log.i("tarik", e.getMessage());
            }
        } else if (this.identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_BREAKING_NEWS) {
            try {
                ArrayList<NewsItem> breakingNews = new ArrayList<>();
                Document doc = Jsoup.connect(Constants.WEB_SERVICE.BASE_URL).get();
                Elements breakingNewsElements = doc.select("div#header-text-nav-container marquee").get(0).select("a");
                for (Element singleNews : breakingNewsElements) {
                    String link = singleNews.attr("href");
                    String title = singleNews.text();

                    NewsItem newsItem = new NewsItem();
                    newsItem.setTitle(title);
                    newsItem.setNews_url(link);
                    newsItem.setNews_type(NewsItem.NEWS_TYPE.BREAKING_NEWS);
                    breakingNews.add(newsItem);
                }
                result = breakingNews;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (this.identifier == Constants.ASYNCTASK_IDENTIFIERS.GET_MENU) {
            try {
                ArrayList<NewsMenuItem> menus = new ArrayList<>();
                Document doc = Jsoup.connect(Constants.WEB_SERVICE.BASE_URL).get();
                Elements menuElements = doc.select("nav#site-navigation ul#menu-menu-main").get(0).select("li a");
                for (Element singleMenu : menuElements) {
                    String link = singleMenu.attr("href");
                    String title = singleMenu.text();
                    String id = String.valueOf(link.charAt(link.length() - 1));

                    NewsMenuItem newsMenuItem = new NewsMenuItem();
                    newsMenuItem.setTitle(title);
                    newsMenuItem.setMenu_url(link);
                    newsMenuItem.setId(id);
                    menus.add(newsMenuItem);
                }
                result = menus;

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return result;

    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        caller.onPostExecuteAsynctask(this.identifier, result);
    }

}
