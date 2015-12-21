package cl.sebapincheira.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMainMovieGrid extends Fragment {

    protected AdapterGrid mReclyclerAdapter = new AdapterGrid();
    protected RecyclerView.LayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;

    public FragmentMainMovieGrid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_main_movie_grid, container, false);

        setRecyclerView();

        return mRecyclerView;
    }

    private void setRecyclerView() {

        mRecyclerView.setAdapter(mReclyclerAdapter);

        // The columns number of the grid
        mLayoutManager = new GridLayoutManager(this.getContext(), 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
