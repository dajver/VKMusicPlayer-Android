package com.project.vkmusicplayer.etc;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import static com.project.vkmusicplayer.etc.Constants.KEY_TOKEN;

/**
 * Created by gleb on 22.02.17.
 */

public class SharedPrefs {

    public static void setToken(Context context, String token) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_TOKEN, token)
                .commit();
    }

    public static String getToken(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(KEY_TOKEN, "");
    }

    public static void deleteAll(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .remove(KEY_TOKEN)
                .commit();
    }
}
