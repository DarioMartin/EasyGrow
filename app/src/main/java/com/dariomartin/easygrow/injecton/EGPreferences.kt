package com.dariomartin.easygrow.injecton

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EGPreferences @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val USER_NAME = "USER_NAME"
        private const val USER_SURNAME = "USER_SURNAME"
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun getUserName(): String {
        return prefs.getString(USER_NAME, "") ?: ""
    }

    fun saveUserName(name: String) {
        prefs.edit().putString(USER_NAME, name).apply()
    }

    fun getUserSurname(): String {
        return prefs.getString(USER_SURNAME, "") ?: ""
    }

    fun saveUserSurname(surname: String) {
        prefs.edit().putString(USER_SURNAME, surname).apply()
    }
}