package com.example.uts_pam.network

import android.content.Context
import android.content.SharedPreferences

class LoginPreferences(context: Context) {
    val sharedPreferences: SharedPreferences
    var editor : SharedPreferences.Editor? = null
    val keyPreferences = "LOGINDB"
    val login = "login"

    init {
        sharedPreferences = context.getSharedPreferences(keyPreferences, Context.MODE_PRIVATE)
        editor = sharedPreferences?.edit()
    }

    fun setStatus(status: Boolean){
        sharedPreferences.edit().putBoolean(login, status).apply()
    }

    fun getStatus(): Boolean{
        return sharedPreferences.getBoolean(login, false)
    }
}