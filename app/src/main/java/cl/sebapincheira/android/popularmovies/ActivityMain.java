package cl.sebapincheira.android.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class ActivityMain extends AppCompatActivity {

    private final String LOG_TAG = ActivityMain.class.getSimpleName();
    public RecyclerView.Adapter vAdapterLocal = new AdapterGrid();
    RecyclerView vRecyclerView;
    RecyclerView.LayoutManager vLayoutManager;
    CloudFetchMovieList vCloudMovieList;
    private Toolbar vToolbar_main;
    private ProgressBar vProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vToolbar_main = (Toolbar) findViewById(R.id.xml_activity_main_toolbar); // Attaching the layout to the vToolbar_main object
        setSupportActionBar(vToolbar_main); // Setting vToolbar_main as the ActionBar with setSupportActionBar() call

        //Progress bar
        vProgressBar = (ProgressBar) findViewById(R.id.xml_activity_main_progress_bar);

        // Calling the RecyclerView
        vRecyclerView = (RecyclerView) findViewById(R.id.xml_activity_main_recycler_view);
        vRecyclerView.setHasFixedSize(true);

        // The number of Columns
        vLayoutManager = new GridLayoutManager(this, 2);
        vRecyclerView.setLayoutManager(vLayoutManager);


        //Load data from theMovieDB only when I don't have it.
        if (vAdapterLocal.getItemCount() == 0) {

            //Load MovieList
            vCloudMovieList = new FetchGrid("popular");
            vCloudMovieList.getMovieList();
            vProgressBar.setVisibility(View.VISIBLE);

        } else {
            vProgressBar.setVisibility(View.GONE);
        }


        //vAdapterLocal.getItemCount();

        //Set adapter
        vRecyclerView.setAdapter(vAdapterLocal);
    }

    public class FetchGrid extends CloudFetchMovieList {

        public FetchGrid(String iMovieTypeList) {
            super(iMovieTypeList);
        }


        @Override
        protected void onPostExecute(AdapterGrid iResult) {


            if (iResult != null) {
                Log.i(LOG_TAG, "HOLA: " + "Movie list fetch finished! " + iResult.getItemCount());


                vAdapterLocal = iResult;


                vRecyclerView.setAdapter(vAdapterLocal);


                Toast.makeText(getApplicationContext(), "Movie list fetch finished!", Toast.LENGTH_SHORT).show();


            } else {
                Log.i(LOG_TAG, "HOLA: " + "There was a problem fetching movie list");
                Toast.makeText(getApplicationContext(), "There was a problem fetching movie list", Toast.LENGTH_SHORT).show();
            }

            vProgressBar.setVisibility(View.GONE);

        }

    }
}
