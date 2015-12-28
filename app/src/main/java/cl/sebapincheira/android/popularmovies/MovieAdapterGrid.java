package cl.sebapincheira.android.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Seba on 12/12/2015.
 * Examples provided by http://www.exoguru.com/android/ui/recyclerview/custom-android-grids-using-recyclerview.html
 */
public class MovieAdapterGrid extends RecyclerView.Adapter<MovieAdapterGrid.ViewHolder> {

    public static final String LOG_TAG = MovieAdapterGrid.class.getName();
    public final _Util mUtil = new _Util();
    public List<MovieItemGrid> vMovieList = new ArrayList<MovieItemGrid>();

    public MovieAdapterGrid() {
        super();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_grid_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final MovieItemGrid mMovieItemGridSelected = vMovieList.get(position);

        if (mUtil.isMaterial()) {
            //Set TransitionName for Material animation
            viewHolder.mImageViewMovie.setTransitionName("pt" + String.valueOf(position));
        }

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                String mTransitionName = "";
                ImageView iV = (ImageView) v.findViewById(R.id.img_thumbnail);

                if (mUtil.isMaterial()) {
                    mTransitionName = iV.getTransitionName();
                }

                //Data to send to movie detail activity
                Intent intent = new Intent(context, ActivityMovieDetail.class);
                intent.putExtra(ActivityMovieDetail.INTENT_MOVIE_ID, mMovieItemGridSelected.getMovieId());
                intent.putExtra(ActivityMovieDetail.INTENT_MOVIE_TITLE, mMovieItemGridSelected.getMovieTitle());
                intent.putExtra(ActivityMovieDetail.INTENT_POSTER_URI, mMovieItemGridSelected.getPosterURI());
                intent.putExtra(ActivityMovieDetail.INTENT_BACKDROP_URI, mMovieItemGridSelected.getBackdropURI());
                Date releaseDateTemp = mMovieItemGridSelected.getReleaseDate();
                Long releaseDate = null;
                if (releaseDateTemp != null) {
                    releaseDate = releaseDateTemp.getTime();
                }
                intent.putExtra(ActivityMovieDetail.INTENT_RELEASE_DATE, releaseDate);
                intent.putExtra(ActivityMovieDetail.INTENT_OVERVIEW, mMovieItemGridSelected.getOverview());
                intent.putExtra(ActivityMovieDetail.INTENT_VOTE_AVERAGE, mMovieItemGridSelected.getVoteAverage());


                if (mUtil.isMaterial()) {


                    intent.putExtra(ActivityMovieDetail.INTENT_TRANSITION_NAME, mTransitionName);

                    /* Shared Object animation -- Used the same TranstionName on both imageviews (origin-target) */
                    Bundle vBundle = ActivityOptionsCompat
                            .makeSceneTransitionAnimation((ActivityMain) context, iV, mTransitionName)
                            .toBundle();

                    context.startActivity(intent, vBundle);

                } else {

                    context.startActivity(intent);
                }
            }
        });

        //Load poster image
        Glide.with(viewHolder.mImageViewMovie.getContext())
                .load(mMovieItemGridSelected.getPosterURI())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.placeholder(R.drawable.alert_outline)
                .into(viewHolder.mImageViewMovie);

        Log.i(LOG_TAG, "MIRA: " + mMovieItemGridSelected.getPosterURI());
    }

    @Override
    public int getItemCount() {
        return vMovieList.size();
    }

    public void setMovieList(List<MovieItemGrid> iMovieList) {
        vMovieList = iMovieList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageViewMovie;
        public final View mView;

        public ViewHolder(final View itemView) {
            super(itemView);
            mImageViewMovie = (ImageView) itemView.findViewById(R.id.img_thumbnail);
            mView = itemView;
        }
    }


}
