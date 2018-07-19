package com.agtools.agtools.Constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SPHelper {

	Context ctx;
	public static final String MyPREFERENCES = "AGTOOLS";

	public static final String KEY_LOGIN_RESPONSE = "LOGINRESPONSE";
	public static final String KEY_COMMUDITYNAME = "commidityname";
	public static final String KEY_LIVEWEATHERCITY1 = "liveweathercity1";
	public static final String KEY_LIVEWEATHERCITY2 = "liveweathercity2";
	public static final String KEY_LIVEWEATHERCITY3 = "liveweathercity3";


	public static String getFromSP(Context ctx, String prefsName, String key) {
		SharedPreferences sharedpreferences = ctx.getSharedPreferences(
				prefsName, Context.MODE_PRIVATE);
		String returnValue = sharedpreferences.getString(key, "");
		return returnValue;
	}
	
	
	public static void setValueToPrefs(Context ctx, String prefsName,
                                       String key, String value) {
		SharedPreferences pref = ctx.getSharedPreferences(prefsName,
				Context.MODE_PRIVATE);
		Editor edit = pref.edit();

		edit.putString(key, value);

		edit.commit();
	}
	public static void removeValueFROMPrefs(Context ctx, String prefsName, String key)
	{
		SharedPreferences pref = ctx.getSharedPreferences(prefsName,
				Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.remove(key);
		edit.commit();
	}

	public static void removeAllValuesFromPrefs(Context ctx, String prefsName)
	{
		SharedPreferences pref = ctx.getSharedPreferences(prefsName,
				Context.MODE_PRIVATE);
		Editor edit = pref.edit();
		edit.clear();
		edit.commit();
	}

}
