package cl.sebapincheira.android.popularmovies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMainMovieGrid extends Fragment {

    public static final int FRAGMENT_TYPE_MOST_POPULAR = 0;
    public static final int FRAGMENT_TYPE_HIGHEST_RATED = 1;
    public static final int FRAGMENT_TYPE_FAVOURITES = 2;

    private final String LOG_TAG = FragmentMainMovieGrid.class.getSimpleName();

    protected MovieAdapterGrid mReclyclerAdapter = new MovieAdapterGrid();
    protected GridLayoutManager mLayoutManager;
    protected RecyclerView mRecyclerView;

    public FragmentMainMovieGrid() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_recycler_view, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.xml_fragment_main_recycler_view);

        setRecyclerView();

        //Setup progress bar
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.xml_fragment_main_progress_bar);
        progressBar.setIndeterminate(true);

        //Query type to the movie db API
        int queryTypeToTMDB = -1;

        //Fragment arguments
        Bundle args = getArguments();
        int fragmentType = args.getInt("fragment_type", queryTypeToTMDB);

        switch (fragmentType) {
            case FRAGMENT_TYPE_MOST_POPULAR:
                queryTypeToTMDB = MovieListFromTMDB.FETCH_TYPE_POPULAR_MOVIES;
                break;
            case FRAGMENT_TYPE_HIGHEST_RATED:
                queryTypeToTMDB = MovieListFromTMDB.FETCH_TYPE_HIGHEST_RATED;
                break;
            default:
                Log.e(LOG_TAG, "fragment type (" + fragmentType + ") not supported.");
                break;

        }

        MovieListFromTMDB mMovieList =
                new MovieListFromTMDB(queryTypeToTMDB, mReclyclerAdapter);

        mMovieList.setProgressBar(progressBar);

        mMovieList.getMovieList();

        /* To implement automatic movie loading, maybe for stage 2
        * Resource: http://stackoverflow.com/questions/26543131/how-to-implement-endless-list-with-recyclerview
        *
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int pastVisiblesItems, visibleItemCount, totalItemCount;
                boolean loading = true;

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    Log.i(LOG_TAG, "Scroll down! visible: " + visibleItemCount + " / total: " + totalItemCount + " - Past: " + pastVisiblesItems);

                    if (loading) {
                        Log.v(LOG_TAG, "Loading true ! visible: " + visibleItemCount + " / total: " + totalItemCount+" - Past: " + pastVisiblesItems);
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        //if ((visibleItemCount) >= totalItemCount) {
                            loading = false;
                            Log.v(LOG_TAG, "Last Item Wow !");
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });
        */

        return rootView;
    }

    private void setRecyclerView() {

        mRecyclerView.setAdapter(mReclyclerAdapter);

        // The columns number of the grid
        mLayoutManager = new GridLayoutManager(this.getContext(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

}
