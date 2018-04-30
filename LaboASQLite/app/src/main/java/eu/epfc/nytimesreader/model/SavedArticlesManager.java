package eu.epfc.nytimesreader.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jedepaepe on 30/04/2018.
 */

public class SavedArticlesManager {
    ArticleDatabaseHelper articleDatabaseHelper = null;

    private static final SavedArticlesManager ourInstance = new SavedArticlesManager();

    static public SavedArticlesManager getInstance() {
        return ourInstance;
    }

    private SavedArticlesManager() {
    }

    public void initWithContext(Context context) {
        if(null == articleDatabaseHelper) {
            articleDatabaseHelper = new ArticleDatabaseHelper(context);
        }
        articleDatabaseHelper.addArticle("fakeTitle", "fakeAbstract");
    }

    public void saveArticles(List<Article> articles) {
        articles.clear();
        for (Article article: articles) {
            articleDatabaseHelper.addArticle(article);
        }
    }

    public List<Article> getAllArticles() {
        Cursor cursor = articleDatabaseHelper.getReadableDatabase().query(
                DbConst.TABLE_ARTICLE,
                new String[] {DbConst.ID, DbConst.FIELD_TITLE, DbConst.FIELD_ABSTRACT},
                null, null, null, null, null);
        ArrayList<Article> articles = new ArrayList<>(cursor.getCount());
        while(cursor.moveToNext()) {
            articles.add(new Article(cursor.getString(1), cursor.getString(2)));
        }
        cursor.close();
        return articles;
    }
}
