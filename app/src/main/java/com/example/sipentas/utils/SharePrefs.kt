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

    fun saveN(nik:String) {
        val editor = prefs.edit()
        editor.putString("USER_N",nik)
        editor.apply()
    }

    fun savePass(nik:String) {
        val editor = prefs.edit()
        editor.putString("USER_N",nik)
        editor.apply()
    }

    fun getN():String? = prefs.getString("USER_N",null)

    fun getToken():String? =
        prefs.getString("USER_TOKEN",null)

    fun saveName(name:String) {
        val editor = prefs.edit()
        editor.putString("USER_NAME",name)
        editor.apply()
    }

    fun getName():String? =
        prefs.getString("USER_NAME",null)

    fun saveSatker(name:String) {
        val editor = prefs.edit()
        editor.putString("USER_SATKER",name)
        editor.apply()
    }

    fun saveTipeSatker(satker:String) {
        val editor = prefs.edit()
        editor.putString("USER_TIPESATKER",satker)
        editor.apply()
    }
    fun getTipeSatker():String? =  prefs.getString("USER_TIPESATKER",null)

    fun getSatker():String? =
        prefs.getString("USER_SATKER",null)


}