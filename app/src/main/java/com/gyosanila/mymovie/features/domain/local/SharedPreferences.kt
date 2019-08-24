package com.gyosanila.mymovie.features.domain.local

import android.content.Context

/**
 * Created by ilgaputra15
 * on Friday, 26/07/2019 16:44
 * Division Mobile - PT.Homecareindo Global Medika
 **/
class SharedPreferences(context: Context) {

    companion object {
        const val SETTINGS = "settings"
        const val DAILY_REMAINDER = "dailyRemainder"
        const val RELEASE_REMAINDER = "releaseRemainder"
        const val FIRST_TIME = "firstTime"
    }

    private val settingsPref = context.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)

    var dailyRemainder: Boolean
        get() = settingsPref.getBoolean(DAILY_REMAINDER, false)
        set(value) {
            settingsPref.edit().putBoolean(DAILY_REMAINDER, value).apply()
        }

    var releaseRemainder: Boolean
        get() = settingsPref.getBoolean(RELEASE_REMAINDER, false)
        set(value) {
            settingsPref.edit().putBoolean(RELEASE_REMAINDER, value).apply()
        }

    var isFirstTime: Boolean
        get() = settingsPref.getBoolean(FIRST_TIME, true)
        set(value) {
            settingsPref.edit().putBoolean(FIRST_TIME, value).apply()
        }

}