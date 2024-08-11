package com.tpcindia.professionalcouriersapp.data.utils

import android.content.Context
import android.content.SharedPreferences
import com.tpcindia.professionalcouriersapp.data.model.response.User

object SharedPreferencesManager {

    private const val PREF_NAME = "app_prefs"
    private const val KEY_FIRST_NAME = "firstName"
    private const val KEY_LAST_NAME = "lastName"
    private const val KEY_BRANCH_CODE = "branchCode"
    private const val KEY_USER_CODE = "userCode"
    private const val KEY_BRANCH = "branch"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, user: User) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_BRANCH_CODE, user.branchCode)
        editor.putString(KEY_BRANCH, user.branch)
        editor.putString(KEY_USER_CODE, user.userCode)
        editor.apply()
    }

    fun getUser(context: Context): User? {
        val sharedPreferences = getSharedPreferences(context)
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, null)
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, null)
        val branchCode = sharedPreferences.getString(KEY_BRANCH_CODE, null)
        val branch = sharedPreferences.getString(KEY_BRANCH, null)
        val userCode = sharedPreferences.getString(KEY_USER_CODE, null)
        return if (firstName != null && lastName != null && branchCode != null && branch != null && userCode != null) {
            User(firstName = firstName, lastName = lastName, branchCode = branchCode, branch = branch, userCode = userCode)
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
