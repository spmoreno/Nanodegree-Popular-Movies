package cl.sebapincheira.android.popularmovies;

import android.widget.ImageView;

/**
 * Created by Seba on 12/12/2015.
 */
public class ItemGrid {

    String mMovieTitle;
    String mMovieId;
    String mPosterURI;
    String mBackdropURI;
    String mReleaseDate;
    String mVoteAverage;

    ImageView mImageView;

    public ItemGrid(String iMovieId, String iMovieName, String iImageURI,
                    String iImageURIBackdrop, String iReleaseDate, String iVoteAverage) {
        this.mMovieId = iMovieId;
        this.mMovieTitle = iMovieName;
        this.mPosterURI = "http://image.tmdb.org/t/p/w500" + iImageURI;
        this.mBackdropURI = "http://image.tmdb.org/t/p/w500" + iImageURIBackdrop;
        this.mReleaseDate = iReleaseDate;
        this.mVoteAverage = iVoteAverage;
    }

    public String getName() {
        return mMovieTitle;
    }


}
