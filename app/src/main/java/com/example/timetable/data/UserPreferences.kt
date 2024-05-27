package com.example.timetable.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences(private val context: Context) {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(context)
                INSTANCE = instance
                instance
            }
        }
    }

    private val Context.dataStore by preferencesDataStore(name = "my_data_store")

    private object Keys {
        val KEY_AUTH = stringPreferencesKey("key_auth")
        val KEY_ROLE = stringPreferencesKey("key_role")
    }

    val authToken: Flow<String?>
        get() = context.dataStore.data.map { prefs ->
            prefs[Keys.KEY_AUTH]
        }

    val role: Flow<String?>
        get() = context.dataStore.data.map {prefs ->
            prefs[Keys.KEY_ROLE]
        }

    suspend fun saveAuthToken(authToken: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.KEY_AUTH] = authToken
        }
    }

    suspend fun saveRole(role: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.KEY_ROLE] = role
        }
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
