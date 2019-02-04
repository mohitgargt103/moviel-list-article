package mohit.com.movielistapp.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import mohit.com.movielistapp.R;
import mohit.com.movielistapp.interfaces.MovieInterface;
import mohit.com.movielistapp.model.pojo.MovieInfo;
import mohit.com.movielistapp.presenter.MoviePresenter;
import mohit.com.movielistapp.view.adapters.MovieListAdapter;

public class MainActivity extends AppCompatActivity implements MovieInterface {

    private TextView loadingTv;
    private RecyclerView articleRv;
    int page = 1;
    private MovieListAdapter articleListAdapter;
    private MoviePresenter articlePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setAdapter(new ArrayList<MovieInfo>());
        handleEvents();
    }

    //region utility methods
    private void initViews() {
        getSupportActionBar().setTitle("Movie List");
        loadingTv = findViewById(R.id.tv_loading);
        articleRv = findViewById(R.id.rv_articles);
        articleRv.setLayoutManager(new LinearLayoutManager(this));
    }

    private void handleEvents() {
        articlePresenter = new MoviePresenter(this);
        articlePresenter.getArticleList(page);
    }

    private void showMessage(boolean show, String message) {
        loadingTv.setText(message);
        loadingTv.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void setAdapter(ArrayList<MovieInfo> articleList) {
        if (articleListAdapter == null) {
            articleListAdapter = new MovieListAdapter(this, articleList);
            articleRv.setAdapter(articleListAdapter);
            articleListAdapter.setOnBottomReachedListener(new MovieListAdapter.OnBottomReachedListener() {
                @Override
                public void OnReached(int offset) {
                    page++;
                    articlePresenter.getArticleList(page);
                }
            });
        }
        if (articleListAdapter.getItemCount() <= 0) {
            articleListAdapter.addAll(articleList);
        } else {
            int old = articleListAdapter.getItemCount();
            articleListAdapter.add(articleList, old);
        }

    }

    //end region

    //region API callbacks
    @Override
    public void onArticleList(ArrayList<MovieInfo> articleList) {
        if (articleList.isEmpty()) {
            if (articleListAdapter.getItemCount() > 0) {
                showMessage(false, "");
            } else {
                showMessage(true, "No articles right now");
            }

        } else {
            showMessage(false, "");
            setAdapter(articleList);
        }
    }

    @Override
    public void onApiStart() {
        showMessage(true, "Loading...");
    }

    @Override
    public void onFailure(String message) {
        showMessage(true, message);
    }
    //endregion
}
