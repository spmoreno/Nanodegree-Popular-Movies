package cl.sebapincheira.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seba on 12/12/2015.
 */

public class MovieListFromTMDB extends AsyncTask<String, Void, List<MovieItemGrid>> {

    public static final int FETCH_TYPE_POPULAR_MOVIES = 0;
    public static final int FETCH_TYPE_HIGHEST_RATED = 1;
    public static final int FECTH_TYPE_MOVIE_DETAIL = 2;


    private static final String LOG_TAG = MovieListFromTMDB.class.getSimpleName();
    private static final String TMDB_BASE_URI = "api.themoviedb.org";
    private static final String TMDB_API_KEY = "";
    private static final String TMDB_IMAGES_BASE_POSTER_URI = "http://image.tmdb.org/t/p/w185";
    private static final String TMDB_IMAGES_BASE_BACKDROP_URI = "http://image.tmdb.org/t/p/w500";

    public MovieAdapterGrid vAdapter;

    protected ProgressBar progressBar;

    // Will contain the raw JSON response as a string.
    String mJsonString = null;
    String mMovieListType = null;

    public MovieListFromTMDB(int iMovieListType, MovieAdapterGrid iMovieAdapterGrid) {
        setTypeMovieList(iMovieListType);
        setAdapter(iMovieAdapterGrid);
    }

    private void setAdapter(MovieAdapterGrid iMovieAdapterGrid) {
        vAdapter = iMovieAdapterGrid;
    }

    @Override
    protected List<MovieItemGrid> doInBackground(String... params) {

        Log.i(LOG_TAG, "Trace doInBackground method");

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {

            String vUrl = null;

            Uri.Builder vBuilder = new Uri.Builder();
            vBuilder.scheme("http")
                    .authority(TMDB_BASE_URI)
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                            //.appendQueryParameter("primary_release_year","2010")
                    .appendQueryParameter("sort_by", mMovieListType)
                    .appendQueryParameter("api_key", TMDB_API_KEY);

            vUrl = vBuilder.build().toString();

            Log.i(LOG_TAG, "URL JSON:" + vUrl);

            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are available at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            URL vUrlFinal = new URL(vUrl);


            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) vUrlFinal.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                //forecastJsonStr = null;
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                mJsonString = null;
            }
            mJsonString = buffer.toString();


            Log.i(LOG_TAG, "JSON: " + mJsonString);


        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            //forecastJsonStr = null;
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }


        try {
            //Pasa los datos traídos desde el API y trata de rescatar solo los datos necesarios

            return getMovieDataFromJson();

        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }


        return null;
    }

    public void setTypeMovieList(final int iType) {

        switch (iType) {
            case FETCH_TYPE_POPULAR_MOVIES:
                mMovieListType = "popularity.desc";
                break;
            case FETCH_TYPE_HIGHEST_RATED:
                mMovieListType = "vote_average.desc";
                break;
            default:
                //By default, the search is by popularity
                mMovieListType = "popularity.desc";
                break;
        }

    }


    /**
     * Take the String representing the complete list of movies in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */

    public List<MovieItemGrid> getMovieDataFromJson()

            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String CONS_ROOT_OBJECT = "results";
        final String CONS_MOVIE_ID = "id";
        final String CONS_MOVIE_TITLE = "original_title";
        final String CONS_POSTER_URI = "poster_path";
        final String CONS_BACKDROP_URI = "backdrop_path";
        final String CONS_RELEASE_DATE = "release_date";
        final String CONS_OVERVIEW = "overview";
        final String CONS_VOTE_AVERAGE = "vote_average";

        JSONObject vMoviesJson = new JSONObject(mJsonString);
        JSONArray vMoviesArray = vMoviesJson.getJSONArray(CONS_ROOT_OBJECT);

        List<MovieItemGrid> mMovieListFromJSON = new ArrayList<MovieItemGrid>();


        for (int i = 0; i < vMoviesArray.length(); i++) {

            // Get the JSON object representing the day
            JSONObject vMovieJSONObject = vMoviesArray.getJSONObject(i);
            MovieItemGrid mMovieItem = new MovieItemGrid();
            String overview;

            //Set up attributes for current movie
            mMovieItem.setMovieId(vMovieJSONObject.getString(CONS_MOVIE_ID));
            mMovieItem.setMovieTitle(vMovieJSONObject.getString(CONS_MOVIE_TITLE));
            mMovieItem.setPosterURI(TMDB_IMAGES_BASE_POSTER_URI + vMovieJSONObject.getString(CONS_POSTER_URI));
            mMovieItem.setBackdropURI(TMDB_IMAGES_BASE_BACKDROP_URI + vMovieJSONObject.getString(CONS_BACKDROP_URI));
            mMovieItem.setReleaseDate(vMovieJSONObject.getString(CONS_RELEASE_DATE));
            mMovieItem.setOverview(vMovieJSONObject.getString(CONS_OVERVIEW));
            mMovieItem.setVoteAverage(vMovieJSONObject.getString(CONS_VOTE_AVERAGE));


            //Add movie to the list
            mMovieListFromJSON.add(mMovieItem);

            Log.i(LOG_TAG, "HOLA: " + vMovieJSONObject.getString(CONS_MOVIE_ID) + " "
                    + vMovieJSONObject.getString("original_title"));
        }

        return mMovieListFromJSON;
    }

    public void getMovieList() {
        this.execute();
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {

        progressBar.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onPostExecute(List<MovieItemGrid> iResult) {

        if (iResult != null) {
            Log.i(LOG_TAG, "Movie list fetch finished! " + iResult.size());

            this.vAdapter.setMovieList(iResult);

            this.vAdapter.notifyDataSetChanged();


        } else {
            Log.i(LOG_TAG, "There was a problem fetching movie list");
        }

        this.progressBar.setVisibility(View.GONE);

    }


}
