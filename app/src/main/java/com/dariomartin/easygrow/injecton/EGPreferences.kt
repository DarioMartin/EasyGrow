package com.dariomartin.easygrow.injecton

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.dariomartin.easygrow.data.model.User
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EGPreferences @Inject constructor(@ApplicationContext context: Context) {

    companion object {
        private const val USER_NAME = "USER_NAME"
        private const val USER_SURNAME = "USER_SURNAME"
        private const val USER_TYPE = "USER_TYPE"
        private const val HOUR = "HOUR"
        private const val MINUTES = "MINUTES"

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

    fun getUserType(): User.Type? {
        return prefs.getString(USER_TYPE, null)?.let { User.Type.valueOf(it) }
    }

    fun saveUserType(type: User.Type?) {
        prefs.edit().putString(USER_TYPE, type?.name).apply()
    }

    fun saveHour(hour: Int) {
        prefs.edit().putInt(HOUR, hour).apply()
    }

    fun getHour(): Int {
        return prefs.getInt(HOUR, 0)
    }


    fun saveMinutes(minutes: Int) {
        prefs.edit().putInt(MINUTES, minutes).apply()
    }

    fun getMinutes(): Int {
        return prefs.getInt(MINUTES, 0)
    }

}