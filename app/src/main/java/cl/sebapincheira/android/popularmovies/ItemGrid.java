package cl.sebapincheira.android.popularmovies;

import android.widget.ImageView;

/**
 * Created by Seba on 12/12/2015.
 */
public class ItemGrid {

    String mMovieName;
    String mMovieId;
    String mImageURI;
    String mImageURIBackdrop;
    String mReleaseDate;
    String mVoteAverage;

    ImageView mImageView;

    public ItemGrid(String iMovieId, String iMovieName, String iImageURI,
                    String iImageURIBackdrop, String iReleaseDate, String iVoteAverage) {
        this.mMovieId = iMovieId;
        this.mMovieName = iMovieName;
        this.mImageURI = "http://image.tmdb.org/t/p/w500" + iImageURI;
        this.mImageURIBackdrop = "http://image.tmdb.org/t/p/w500" + iImageURIBackdrop;
        this.mReleaseDate = iReleaseDate;
        this.mVoteAverage = iVoteAverage;
    }

    public String getName() {
        return mMovieName;
    }


}
