package eu.epfc.nytimesreader.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jedepaepe on 30/04/2018.
 */

public class ArticleDatabaseHelper extends SQLiteOpenHelper {

    public ArticleDatabaseHelper(Context context) {
        super(context, DbConst.DB_NAME, null, DbConst.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlInstruction =
                "CREATE TABLE ARTICLES (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "TITLE TEXT, " +
                        "ABSTRACT TEXT)";
        sqLiteDatabase.execSQL(sqlInstruction);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void clearArticles() {
        getWritableDatabase().delete(DbConst.TABLE_ARTICLE, null, null);
    }

    public void addArticle(Article article) {
        addArticle(article.title, article.articleAbstract);
    }

    public void addArticle(String title, String articleAbstract) {
        ContentValues values = new ContentValues();
        values.put(DbConst.FIELD_TITLE, title);
        values.put(DbConst.FIELD_ABSTRACT, articleAbstract);
        getWritableDatabase().insert(DbConst.TABLE_ARTICLE, null, values);
    }

}
