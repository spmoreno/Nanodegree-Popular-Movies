package cl.sebapincheira.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class ActivityMovieDetail extends AppCompatActivity {

    public static final _Util mUtil = new _Util();

    /* Share name object between imageview at recyclerview and imageview on this activity */
    public static final String CONS_TRANSITION_NAME = "transition_name";

    /* Movie Info constants used as Intent String names */
    public static final String CONS_MOVIE_ID = "movie_id";
    public static final String CONS_MOVIE_TITLE = "movie_title";
    public static final String CONS_POSTER_URI = "poster_uri";
    public static final String CONS_BACKDROP_URI = "backdrop_uri";

    /* Object with actual movie information */
    protected ItemGrid mActualMovie;
    /* Transition name value */
    protected String mTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        //Setting toolbar
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Retrieve MovieData
        getMovieDataFromIntent();

        /* Floating favourite's button -- Soon */
        /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

    }

    /*
    Retrieve all movie information from intent
     */
    public void getMovieDataFromIntent() {

        Intent mIntent = getIntent();

        mActualMovie.mMovieId = mIntent.getStringExtra(CONS_MOVIE_ID);
        mActualMovie.mMovieTitle = mIntent.getStringExtra(CONS_MOVIE_TITLE);
        mActualMovie.mPosterURI = mIntent.getStringExtra(CONS_POSTER_URI);
        mActualMovie.mBackdropURI = mIntent.getStringExtra(CONS_BACKDROP_URI);


        /* Only if we're running on Material compatible */
        if (mUtil.isMaterialAvailable()) {
            mTransitionName = mIntent.getStringExtra(CONS_TRANSITION_NAME);
        }
    }
}
