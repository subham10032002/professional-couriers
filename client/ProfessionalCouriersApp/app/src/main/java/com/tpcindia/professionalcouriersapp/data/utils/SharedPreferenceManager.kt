package com.tpcindia.professionalcouriersapp.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.tpcindia.professionalcouriersapp.data.model.User

object SharedPreferencesManager {

    private const val PREF_NAME = "app_prefs"
    private const val KEY_FIRST_NAME = "firstName"
    private const val KEY_LAST_NAME = "lastName"
    private const val KEY_BRANCH_CODE = "branchCode"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, user: User) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_BRANCH_CODE, user.branchCode)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val sharedPreferences = getSharedPreferences(context)
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, null)
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, null)
        val branchCode = sharedPreferences.getString(KEY_BRANCH_CODE, null)
        return if (firstName != null && lastName != null && branchCode != null) {
            User(firstName = "firstName", lastName = lastName, branchCode = branchCode)
        } else {
            null
        }
    }

    fun clearUser(context: Context) {
        val editor = getSharedPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}
