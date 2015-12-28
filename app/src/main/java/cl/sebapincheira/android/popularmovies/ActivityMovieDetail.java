package cl.sebapincheira.android.popularmovies;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActivityMovieDetail extends AppCompatActivity {

    /* Share name object between imageview at recyclerview and imageview on this activity */
    public static final String INTENT_TRANSITION_NAME = "transition_name";
    /* Movie Info constants used as Intent String names */
    public static final String INTENT_MOVIE_ID = "movie_id";
    public static final String INTENT_MOVIE_TITLE = "movie_title";
    public static final String INTENT_POSTER_URI = "poster_uri";
    public static final String INTENT_BACKDROP_URI = "backdrop_uri";
    public static final String INTENT_RELEASE_DATE = "release_date";
    public static final String INTENT_OVERVIEW = "overview";
    public static final String INTENT_VOTE_AVERAGE = "vote_average";
    private static final String LOG_TAG = ActivityMain.class.getName();
    private static final _Util mUtil = new _Util();
    /* Object with actual movie information */
    protected MovieItemGrid mActualMovie = new MovieItemGrid();
    ;
    /* Transition name value */
    protected String mTransitionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        //Setting toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.i(LOG_TAG, "MIRA 0: " + mActualMovie.getPosterURI());

        //Retrieve Movie data from intent
        getMovieDataFromIntent();

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)
                findViewById(R.id.toolbar_layout);


        //Set up Poster Image
        ImageView imageViewPoster = (ImageView) findViewById(R.id.image_view_poster);
        ImageView imageViewBackdrop = (ImageView) findViewById(R.id.image_view_backdrop);

        if (mUtil.isMaterial()) {
            /* Setup transition name, only if Material is available */
            imageViewPoster.setTransitionName(mTransitionName);
        }

        Log.i(LOG_TAG, "MIRA 1: " + mActualMovie.getPosterURI());

        //Load poster image
        Glide.with(this)
                .load(mActualMovie.getPosterURI())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageViewPoster);


        //Load backdrop image
        Glide.with(this)
                .load(mActualMovie.getBackdropURI())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageViewBackdrop);


        //Setup the movie title
        TextView movieTitle = (TextView) findViewById(R.id.text_view_movie_title);
        movieTitle.setText(mActualMovie.getMovieTitle());

        //Setup the overview or synopsis
        TextView overview = (TextView) findViewById(R.id.text_view_overview);
        String textOverview = mActualMovie.getOverview();
        if (mActualMovie.getOverview().equals("")) {
            textOverview = "So sorry, no overview available.";

            if (mUtil.isMarshmallow()) {
                overview.setTextAppearance(R.style.ItalicText);
            }
        }
        overview.setText(textOverview);

        //Setup the rating
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rating_bar_indicator);
        TextView voteAverage = (TextView) findViewById(R.id.text_view_vote_average);
        ratingBar.setRating(mActualMovie.getVoteAverage() / 2);
        String stringVoteAverage = "";

        Log.i("...", mActualMovie.getVoteAverage().toString().substring(0, 2));

        if (!mActualMovie.getVoteAverage().toString().substring(0, 2).equals("10")) {
            //Format the number with just 1 decimal
            stringVoteAverage = String.format("%.1f", mActualMovie.getVoteAverage());
        } else {
            //Show only entire number
            stringVoteAverage = String.format("%.0f", mActualMovie.getVoteAverage());
        }

        voteAverage.setText(stringVoteAverage);

        //Setup release date
        TextView releaseDate = (TextView) findViewById(R.id.text_view_release_date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.ENGLISH);
        releaseDate.setText(dateFormat.format(mActualMovie.getReleaseDate()));


        Log.i(LOG_TAG, "MIRA 2:" + mActualMovie.getVoteAverage());

    }

    /*
    Retrieve all movie information from intent
     */
    public void getMovieDataFromIntent() {

        Bundle extras = getIntent().getExtras();

        mActualMovie.setMovieId(extras.getString(INTENT_MOVIE_ID));
        mActualMovie.setMovieTitle(extras.getString(INTENT_MOVIE_TITLE));
        mActualMovie.setPosterURI(extras.getString(INTENT_POSTER_URI));

        mActualMovie.setBackdropURI(extras.getString(INTENT_BACKDROP_URI));
        Date dateObj = new Date(extras.getLong(INTENT_RELEASE_DATE, -1));
        mActualMovie.setReleaseDate(dateObj);
        mActualMovie.setOverview(extras.getString(INTENT_OVERVIEW));
        mActualMovie.setVoteAverage(extras.getFloat(INTENT_VOTE_AVERAGE));

        /* Only if we're running on Material compatible */
        if (mUtil.isMaterial()) {
            mTransitionName = extras.getString(INTENT_TRANSITION_NAME);
        }
    }

    /*
    Override toolbar up button
    http://stackoverflow.com/questions/22182888/actionbar-up-button-destroys-parent-activity-back-does-not
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
