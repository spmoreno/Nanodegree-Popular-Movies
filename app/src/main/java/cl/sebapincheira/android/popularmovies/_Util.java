package cl.sebapincheira.android.popularmovies;

import android.os.Build;

/**
 * Created by Seba on 21/12/2015.
 */
public class _Util {

    public _Util() {

    }

    public boolean isMaterialAvailable() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }

        return false;

    }
}
