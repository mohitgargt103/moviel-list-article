package mohit.com.movielistapp.presenter;

import android.support.annotation.NonNull;

import mohit.com.movielistapp.common.Constants;
import mohit.com.movielistapp.interfaces.MovieInterface;
import mohit.com.movielistapp.model.callbacks.MovieCallback;
import mohit.com.movielistapp.model.webRequest.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviePresenter {

    private MovieInterface articleInterface;

    public MoviePresenter(MovieInterface articleInterface) {
        this.articleInterface = articleInterface;
    }

    public void getArticleList(int page) {
        articleInterface.onApiStart();
        RetrofitRequest
                .retrofitGetRequest()
                .getArticleList(Constants.API_KEY, String.valueOf(page))
                .enqueue(new Callback<MovieCallback>() {
                    @Override
                    public void onResponse(@NonNull Call<MovieCallback> call,
                                           @NonNull Response<MovieCallback> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            articleInterface.onArticleList(response.body().getResults());
                        } else {
                            articleInterface.onFailure(Constants.SOMETHING_WENT_WRONG);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MovieCallback> call,
                                          @NonNull Throwable t) {
                        articleInterface.onFailure(Constants.SOMETHING_WENT_WRONG);
                    }
                });
    }

}
