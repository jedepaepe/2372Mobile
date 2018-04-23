package com.example.jedepaepe.labo9service;

/**
 * Created by jedepaepe on 23/04/2018.
 */

public class Article {

    String title;
    String articleAbstract;

    public Article(String title, String articleAbstract) {
        this.title = title;
        this.articleAbstract = articleAbstract;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstract() {
        return articleAbstract;
    }
}
