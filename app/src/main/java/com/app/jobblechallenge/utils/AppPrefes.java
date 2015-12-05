/**
 * 
 */
package com.app.jobblechallenge.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author luttu
 * 
 */
public class AppPrefes {

	private SharedPreferences appSharedPrefs;
	private Editor prefsEditor;

	public AppPrefes(Context context)
	{
		this.appSharedPrefs = context.getSharedPreferences("jobble",
				Activity.MODE_PRIVATE);
		this.prefsEditor = appSharedPrefs.edit();
	}

	/****
	 * 
	 * getdata() get the value from the preference
	 * 
	 * */
	public String getData(String key) {
		return appSharedPrefs.getString(key, "");
	}

	public Integer getIntData(String key) {
		return appSharedPrefs.getInt(key, 0);
	}

	/****
	 * 
	 * SaveData() save the value to the preference
	 * 
	 * */
	public void saveData(String Tag, String text) {
		prefsEditor.putString(Tag, text);
		prefsEditor.commit();
	}

	public void saveIntData(String key, int value) {
		prefsEditor.putInt(key, value);
		prefsEditor.commit();
	}

	/**
	 * delete all AppPreference
	 */
	public void deleteAll() {
		this.prefsEditor = appSharedPrefs.edit();
		this.prefsEditor.clear();
		this.prefsEditor.commit();
	}

}
