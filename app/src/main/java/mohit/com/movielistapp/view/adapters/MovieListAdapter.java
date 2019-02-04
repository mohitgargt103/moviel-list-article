package mohit.com.movielistapp.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import mohit.com.movielistapp.R;
import mohit.com.movielistapp.model.pojo.MovieInfo;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ArticleHolder> {

    Context context;
    ArrayList<MovieInfo> articleList = new ArrayList<>();
    OnBottomReachedListener reachedListener;

    public MovieListAdapter(Context context, ArrayList<MovieInfo> articleList) {
        this.context = context;
        this.articleList = articleList;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_article_item,
                viewGroup,
                false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder articleHolder, int position) {
        articleHolder.bind(articleList.get(position));
        // Bottom reached listener
        if (reachedListener != null && position == getItemCount() - 1) {
            reachedListener.OnReached(getItemCount());
        }
    }

    @Override
    public int getItemCount() {
        return articleList.size();
    }

    public void addAll(ArrayList<MovieInfo> productItemList) {
        this.articleList.clear();
        this.articleList.addAll(productItemList);
        notifyDataSetChanged();
    }

    public void add(ArrayList<MovieInfo> productItemList, int oldPos) {
        this.articleList.addAll(productItemList);
        notifyItemChanged(oldPos);
    }

    public void setOnBottomReachedListener(OnBottomReachedListener reachedListener) {
        this.reachedListener = reachedListener;

    }

    public interface OnBottomReachedListener {
        void OnReached(int offset);
    }

    class ArticleHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private TextView descriptionTv;

        ArticleHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_article_title);
            descriptionTv = itemView.findViewById(R.id.tv_article_desc);
        }

        void bind(MovieInfo articleInfo) {
            titleTv.setText("Title: \n" + articleInfo.getTitle());
            descriptionTv.setText("Description: \n" + articleInfo.getOverview());
        }
    }

}
