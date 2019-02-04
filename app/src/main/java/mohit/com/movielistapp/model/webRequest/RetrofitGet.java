package mohit.com.movielistapp.model.webRequest;


import mohit.com.movielistapp.common.Constants;
import mohit.com.movielistapp.model.callbacks.MovieCallback;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetrofitGet {

    @GET(Constants.ARTICLES_URL)
    Call<MovieCallback> getArticleList(@Query("api_key") String api_key, @Query("page") String page);

}
