package rtp.raidtechpro.co_tasker.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {


    static String MyPREFERENCES = "usersharedpreferences";

    public static void saveName(Context context,String key, String data) {

        SharedPreferences sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(key,data);
        editor.apply();
    }


    public static String getName(Context context,String key) {
        SharedPreferences sharedPref = context.getSharedPreferences( MyPREFERENCES,Context.MODE_PRIVATE);
        String defaultValue = "Not Set";
        String  value = sharedPref.getString(key, defaultValue);
        return value;
    }

}

