package cl.sebapincheira.android.popularmovies;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seba on 12/12/2015.
 * Examples provided by http://www.exoguru.com/android/ui/recyclerview/custom-android-grids-using-recyclerview.html
 */
public class AdapterGrid extends RecyclerView.Adapter<AdapterGrid.ViewHolder> {

    public final List<ItemGrid> vMovieList = new ArrayList<ItemGrid>();
    public final _Util mUtil = new _Util();

    public AdapterGrid() {
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
        final ItemGrid mItemGridSelected = vMovieList.get(position);

        if (mUtil.isMaterialAvailable()) {
            //Set TransitionName for Material animation
            viewHolder.mImageViewMovie.setTransitionName("pt" + String.valueOf(position));
        }

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                String mTransitionName = "";
                ImageView iV = (ImageView) v.findViewById(R.id.img_thumbnail);

                if (mUtil.isMaterialAvailable()) {
                    mTransitionName = iV.getTransitionName();
                }

                Intent intent = new Intent(context, ActivityMovieDetail.class);
                intent.putExtra(ActivityMovieDetail.CONS_MOVIE_ID, mItemGridSelected.mMovieId);
                intent.putExtra(ActivityMovieDetail.CONS_MOVIE_TITLE, mItemGridSelected.mMovieTitle);
                intent.putExtra(ActivityMovieDetail.CONS_POSTER_URI, mItemGridSelected.mPosterURI);
                intent.putExtra(ActivityMovieDetail.CONS_BACKDROP_URI, mItemGridSelected.mBackdropURI);

                if (mUtil.isMaterialAvailable()) {

                    intent.putExtra(ActivityMovieDetail.CONS_TRANSITION_NAME, mTransitionName);

                    /* Transition animation -- Used the same TranstionName on both imageviews (origin-target) */
                    Bundle vBundle = ActivityOptions
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
                .load(mItemGridSelected.mPosterURI)
                .into(viewHolder.mImageViewMovie);
    }

    @Override
    public int getItemCount() {
        return vMovieList.size();
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
