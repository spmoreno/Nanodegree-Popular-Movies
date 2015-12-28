package cl.sebapincheira.android.popularmovies;

import android.os.Build;

/**
 * Created by Seba on 21/12/2015.
 * Utilities class
 */
public class _Util {

    public _Util() {

    }

    public boolean isMaterial() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }

        return false;

    }

    public boolean isMarshmallow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        }

        return false;
    }

}
