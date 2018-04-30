package eu.epfc.nytimesreader;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Quentin on 18/04/2018.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>{

    private List<Article> articles;

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        // get a layoutInflater from the context attached to the parent view
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // inflate the layout item_layout in a view
        View articleView = layoutInflater.inflate(R.layout.item_layout,parent,false);

        // create a new ViewHolder with the view articleView
        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {

        Article article = articles.get(position);

        ViewGroup itemViewGroup = (ViewGroup)holder.itemView;

        TextView titleTextView = itemViewGroup.findViewById(R.id.text_item_title);
        titleTextView.setText(article.getTitle());

        TextView abstractTextView = itemViewGroup.findViewById(R.id.text_item_abstract);
        abstractTextView.setText(article.getArticleAbstract());
    }

    @Override
    public int getItemCount() {
        if (articles != null) {
            return articles.size();
        }
        else {
            return 0;
        }
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        this.notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {

        private ArticleViewHolder(View itemView) {
            super(itemView);
        }

    }
}
