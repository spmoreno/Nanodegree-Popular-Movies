package cl.sebapincheira.android.popularmovies;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seba on 12/12/2015.
 * Examples provided by http://www.exoguru.com/android/ui/recyclerview/custom-android-grids-using-recyclerview.html
 */
public class AdapterGrid extends RecyclerView.Adapter<AdapterGrid.ViewHolder> {

    public final List<ItemGrid> vMovieList = new ArrayList<ItemGrid>();


    public AdapterGrid() {
        super();

        //vMovieList = new ArrayList<ItemGrid>();

    }


    @Override
    public AdapterGrid.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.activity_main_grid_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ItemGrid nature = vMovieList.get(i);

        //viewHolder.vMovieName.setText("");//nature.getName());
        //viewHolder.imgThumbnail.setImageBitmap(null);
        Glide.with(viewHolder.imgThumbnail.getContext()).load(nature.mImageURI).into(viewHolder.imgThumbnail);
        viewHolder.imgThumbnail.setTransitionName("photodos" + String.valueOf(i));
        viewHolder.itemView.setTag(nature);

        //viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {

        return vMovieList.size();
    }

    public List<ItemGrid> getMovieList() {
        return this.vMovieList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgThumbnail;
        public TextView vMovieName;
        public AlphaAnimation vAnimationAlpha;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgThumbnail = (ImageView) itemView.findViewById(R.id.img_thumbnail);


            //vAnimationAlpha = new AlphaAnimation(0.7f, 0.99f);
            //vAnimationAlpha.setDuration(300);
            //vMovieName = (TextView)itemView.findViewById(R.id.tv_species);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(v.getContext(), "inside viewholder position = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();

                    ItemGrid vItem = vMovieList.get(getAdapterPosition());

                    //v.setTransitionName("roboto"+ String.valueOf(getAdapterPosition()));

                    //v.startAnimation(vAnimationAlpha);

                    //boolean isVeggie = ((ColorDrawable)v.getBackground()) != null && ((ColorDrawable)v.getBackground()).getColor() == R.color.accent;

                    int finalRadius = (int) Math.hypot(v.getWidth() / 2, v.getHeight() / 2);


                    //Animator anim = ViewAnimationUtils.createCircularReveal(v, (int) v.getWidth() / 2, (int) v.getHeight() / 2, 0, finalRadius);
                    //anim.start();

                    ImageView iV = (ImageView) v.findViewById(R.id.img_thumbnail);

                    ActivityMain myActivity = (ActivityMain) v.getContext();

                    Log.i("TRANSITION NAME", "VALOR: " + iV.getTransitionName());


                    final View androidRobotView = v.findViewById(R.id.img_thumbnail);

                    Bundle vBundle = ActivityOptions
                            .makeSceneTransitionAnimation(myActivity, iV, iV.getTransitionName())
                            .toBundle();


                    /*Intent vDetailMovieActivity = new Intent(myActivity, ActivityMovieDetail.class)
                            .putExtra(Intent.EXTRA_KEY_EVENT, vItem.mMovieId)
                            .putExtra(Intent.EXTRA_TITLE, vItem.mMovieName)
                            .putExtra(Intent.EXTRA_REFERRER_NAME, vItem.mImageURI)
                            .putExtra(Intent.EXTRA_INTENT, String.valueOf(iV.getTransitionName()));

                    v.getContext().startActivity(vDetailMovieActivity, vBundle);
                    */
                }
            });
        }
    }


}
