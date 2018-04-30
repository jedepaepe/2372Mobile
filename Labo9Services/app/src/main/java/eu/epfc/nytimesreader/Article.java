package eu.epfc.nytimesreader;

/**
 * Created by Quentin on 18/04/2018.
 */

public class Article {

    String title;
    String articleAbstract;

    public String getTitle() {
        return title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public Article(String title, String articleAbstract) {
        this.title = title;
        this.articleAbstract = articleAbstract;
    }
}
