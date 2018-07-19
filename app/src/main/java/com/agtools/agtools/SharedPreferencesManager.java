package com.agtools.agtools;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPreferencesManager {
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    Context ctx;

    private static SharedPreferencesManager instance;

    public static SharedPreferencesManager getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedPreferencesManager(ctx);
        }
        return instance;
    }

    public SharedPreferencesManager(Context ctx) {
        sharedpreferences = ctx.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        this.ctx = ctx;
    }

    public void setLangaugeValue(String value) {
        editor.putString("langauge", value);
        editor.commit();
    }

    public String getLangauageValue() {
        String retValue = sharedpreferences.getString("langauge", "");
        return retValue;
    }


}
