package com.example.a963103033239757ba10504dc3857ddc7.ui

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.a963103033239757ba10504dc3857ddc7.R

/**
 * Created by Muhammad AKKAD on 10/7/21.
 */
class SharedPref(var activity: Activity) {

    var sharedPreferences = getInstance()

    private fun getInstance(): SharedPreferences {
        sharedPreferences = activity.getSharedPreferences(
            activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )!!
        return sharedPreferences
    }

    fun setBoolean() {
        sharedPreferences.edit()?.putBoolean("alreadyDownloaded", true)?.apply()
    }

    fun getBoolean(): Boolean {
        return sharedPreferences.getBoolean("alreadyDownloaded", false)
    }

    fun clearSharedPrefs() {
        sharedPreferences.edit().clear().apply()
    }
}