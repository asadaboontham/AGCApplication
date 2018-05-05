package com.example.asadaboomtham.agcapplication;

/**
 * Created by ravi on 16/11/17.
 */

public class NewsItem {

    public String news_head;
    public int news_id;
    public String news_des;
    public String news_pics;
    public String news_ref;
    private String news_link;

    public String getNews_ref() {
        return news_ref;
    }

    public void setNews_ref(String news_ref) {
        this.news_ref = news_ref;
    }

    public String getNews_head() {
        return news_head;
    }

    public void setNews_head(String news_head) {
        this.news_head = news_head;
    }

    public int getNews_id() {
        return news_id;
    }

    public void setNews_id(int news_id) {
        this.news_id = news_id;
    }

    public String getNews_des() {
        return news_des;
    }

    public void setNews_des(String news_des) {
        this.news_des = news_des;
    }

    public String getNews_pics() {
        return news_pics;
    }

    public void setNews_pics(String news_pics) {
        this.news_pics = news_pics;
    }

    public String getNews_link() {
        return news_link;
    }

    public void setNews_link(String news_link) {
        this.news_link = news_link;
    }
}
