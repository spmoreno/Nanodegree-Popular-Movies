package cl.sebapincheira.android.popularmovies;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Seba on 12/12/2015.
 */
public class MovieItemGrid {

    private String movieId;
    private String movieTitle;
    private String posterURI;
    private String backdropURI;
    private Date releaseDate;
    private String overview;
    private Float voteAverage;

    public MovieItemGrid() {
    }

    public void setReleaseDate(String releaseDate) {
        /*
        Got it from
        http://stackoverflow.com/questions/8573250/android-how-can-i-convert-string-to-date
         */

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            setReleaseDate(format.parse(releaseDate));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void setVoteAverage(String voteAverage) {
        setVoteAverage(Float.parseFloat(voteAverage));
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String iMovieId) {
        movieId = iMovieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterURI() {
        return posterURI;
    }

    public void setPosterURI(String iPosterURI) {
        posterURI = iPosterURI;
    }

    public String getBackdropURI() {
        return backdropURI;
    }

    public void setBackdropURI(String iBackdropURI) {
        backdropURI = iBackdropURI;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Float voteAverage) {
        this.voteAverage = voteAverage;
    }
}
