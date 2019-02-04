package mohit.com.movielistapp.interfaces;


import java.util.ArrayList;

import mohit.com.movielistapp.model.pojo.MovieInfo;

public interface MovieInterface extends BaseInterface {

    void onArticleList(ArrayList<MovieInfo> articleList);

}
