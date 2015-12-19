package cl.sebapincheira.android.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Seba on 12/12/2015.
 */

public class CloudFetchMovieList extends AsyncTask<String, Void, AdapterGrid> {

    private final String LOG_TAG = CloudFetchMovieList.class.getSimpleName();

    public AdapterGrid vAdapter = new AdapterGrid();

    // Will contain the raw JSON response as a string.
    String vJsonString = null;
    String vMovieTypeList = null;

    public CloudFetchMovieList(String iMovieTypeList) {
        this.setTypeMovieList(iMovieTypeList);
    }


    @Override
    protected AdapterGrid doInBackground(String... params) {

        Log.i("JAJ", "Trace doInBackground method");


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;


        final String vBaseUrl = "api.themoviedb.org";
        final String vApiKey = "8e71506cc60733a93af716fa009cbc38";

        try {

            String vUrl = null;


            Uri.Builder vBuilder = new Uri.Builder();
            vBuilder.scheme("http")
                    .authority(vBaseUrl)
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by", this.vMovieTypeList)
                            //.appendQueryParameter("with_genres","878")
                            //.appendQueryParameter("sort_by", "vote_average.desc")
                    .appendQueryParameter("api_key", vApiKey);
            //.appendQueryParameter("page", params[0]);

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
                vJsonString = null;
            }
            vJsonString = buffer.toString();


            Log.i(LOG_TAG, "JSON: " + vJsonString);


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

    public void setTypeMovieList(String iType) {

        if (iType == "popular") {
            this.vMovieTypeList = "popularity.desc";

        } else if (iType == "rated") {
            this.vMovieTypeList = "vote_average.desc";

        } else {
            //By default, the search is by popularity
            this.vMovieTypeList = "popularity.desc";
        }

    }


    /**
     * Take the String representing the complete list of movies in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     * <p>
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */

    public AdapterGrid getMovieDataFromJson()

            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String OWM_LIST = "results";
        final String OWM_ID = "id";
        final String OWM_TITLE = "original_title";
        final String OWM_IMAGEURI = "poster_path";
        final String OWM_IMAGEURIBACKDROP = "backdrop_path";

        JSONObject vMoviesJson = new JSONObject(vJsonString);
        JSONArray vMoviesArray = vMoviesJson.getJSONArray(OWM_LIST);

        AdapterGrid vMovieDetailFromJSON = new AdapterGrid();

        for (int i = 0; i < vMoviesArray.length(); i++) {

            // For now, using the format "Day, description, hi/low"
            String vMovieId;
            String vMovieTitle;
            String vImageUri;
            String vImageURIBackdrop;

            // Get the JSON object representing the day
            JSONObject vMovieJSONObject = vMoviesArray.getJSONObject(i);

            vMovieId = vMovieJSONObject.getString(OWM_ID);
            vMovieTitle = vMovieJSONObject.getString(OWM_TITLE);
            vImageUri = vMovieJSONObject.getString(OWM_IMAGEURI);
            vImageURIBackdrop = vMovieJSONObject.getString(OWM_IMAGEURIBACKDROP);

            //Agrego la pelicula al arreglo
            vMovieDetailFromJSON.vMovieList.add(new ItemGrid(vMovieJSONObject.getString("id")
                    , vMovieJSONObject.getString("original_title")
                    , vMovieJSONObject.getString("poster_path")
                    , vMovieJSONObject.getString("backdrop_path")
                    , vMovieJSONObject.getString("release_date")
                    , vMovieJSONObject.getString("vote_average")));

            Log.i(LOG_TAG, "HOLA: " + vMovieJSONObject.getString("original_title"));


        }

        return vMovieDetailFromJSON;

    }

    public void getMovieList() {
        this.execute();
    }

    /*@Override
    protected void onPreExecute() {

        spinner.setVisibility(View.VISIBLE);

    }
    */
    /*
    @Override
    protected void onPostExecute(AdapterGrid iResult) {


        if (iResult != null) {
            Log.i(LOG_TAG, "HOLA: " + "Movie list fetch finished! " + iResult.getItemCount());

            this.vAdapter = iResult;

            this.vAdapter.notifyDataSetChanged();


        } else {
            Log.i(LOG_TAG, "HOLA: " + "There was a problem fetching movie list");
        }

        //spinner.setVisibility(View.GONE);

    }
    */


}
