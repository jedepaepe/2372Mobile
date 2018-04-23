package com.example.jedepaepe.labo9service;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jedepaepe on 23/04/2018.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {

    private List<Article> articles = null;

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_card in a view
        View articleView = layoutInflater.inflate(R.layout.item_article, parent,false);

        // create a new ViewHolder with the view planetView
        return new ArticleViewHolder(articleView);

    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Article article = articles.get(position);

        // get the TextView of the item
        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;
        TextView articleTitleTextView = itemViewGroup.findViewById(R.id.text_article_title);
        articleTitleTextView.setText(article.getTitle());
        TextView articleAbstractTextView = itemViewGroup.findViewById(R.id.text_article_abstract);
        articleAbstractTextView.setText(article.getAbstract());
    }

    @Override
    public int getItemCount() {
        return (articles != null) ? articles.size() : 0;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        public ArticleViewHolder(View itemView) {
            super(itemView);
        }
    }
}
