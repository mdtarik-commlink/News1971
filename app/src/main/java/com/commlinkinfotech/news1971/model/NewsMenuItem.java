package com.commlinkinfotech.news1971.model;

import java.util.ArrayList;

/**
 * Created by CommlinkT on 1/1/2017.
 */

public class NewsMenuItem {
    String id, title, icon_url, menu_url;
    ArrayList<NewsMenuItem> sub_menus;

    public NewsMenuItem(){

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

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public ArrayList<NewsMenuItem> getSub_menus() {
        return sub_menus;
    }

    public void setSub_menus(ArrayList<NewsMenuItem> sub_menus) {
        this.sub_menus = sub_menus;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }
}
