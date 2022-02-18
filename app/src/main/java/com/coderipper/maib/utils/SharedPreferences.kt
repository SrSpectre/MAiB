package com.coderipper.maib.utils

import android.app.Activity
import android.content.Context
import androidx.preference.PreferenceManager

fun addValue(activity: Activity, uname: String? = null, avatar: Int? = null) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        uname?.let { putString("UNAME", it) }
        avatar?.let { putInt("AVATAR", it) }
        commit()
    }
}

fun setStringValue(activity: Activity, key: String,  value: String) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putString(key, value)
        commit()
    }
}

fun setIntValue(activity: Activity, key: String,  value: Int) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putInt(key, value)
        commit()
    }
}

fun setLongValue(activity: Activity, key: String,  value: Long) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putLong(key, value)
        commit()
    }
}

fun setBooleanValue(activity: Activity, key: String,  value: Boolean) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        putBoolean(key, value)
        commit()
    }
}

fun getStringValue(activity: Activity, key: String): String? {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    return sharedPref.getString(key, null)
}

fun getIntValue(activity: Activity, key: String): Int {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    return sharedPref.getInt(key, -1)
}

fun getLongValue(activity: Activity, key: String): Long {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    return sharedPref.getLong(key, -1L)
}

fun getBooleanValue(activity: Activity, key: String): Boolean {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    return sharedPref.getBoolean(key, false)
}

fun getBooleanValue(context: Context, key: String): Boolean {
    val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)
    return preferenceManager.getBoolean(key, true)
}

fun logout(activity: Activity) {
    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE)
    with (sharedPref.edit()) {
        clear()
        commit()
    }
}