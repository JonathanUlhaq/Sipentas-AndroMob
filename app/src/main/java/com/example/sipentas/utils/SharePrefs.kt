package com.example.sipentas.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharePrefs @Inject constructor(@ApplicationContext context: Context) {
    private val prefs:SharedPreferences =
        context.getSharedPreferences("PREFS_TOKEN",Context.MODE_PRIVATE)

    fun saveToken(token:String) {
        val editor = prefs.edit()
        editor.putString("USER_TOKEN",token)
        editor.apply()
    }

    fun getToken():String? =
        prefs.getString("USER_TOKEN",null)

    fun saveName(name:String) {
        val editor = prefs.edit()
        editor.putString("USER_NAME",name)
        editor.apply()
    }

    fun getName():String? =
        prefs.getString("USER_NAME",null)


}