package adnyey.a8tuner;

import android.content.Context;
import android.preference.PreferenceManager;

import java.text.DecimalFormat;

/**
 * Created by Mahajan-PC on 2017-11-03.
 */

public class Unitzer {

    public static String convert(double input, Context mContext) {
        DecimalFormat format_sp = new DecimalFormat("####0.0");
        if (PreferenceManager.getDefaultSharedPreferences(mContext).getBoolean("UNITS", false)) {
            String output = ""+ format_sp.format(input * 0.621371)+" mph";
            return output;
        } else {
            return ""+format_sp.format(input)+" km/h";
        }
    }
}
