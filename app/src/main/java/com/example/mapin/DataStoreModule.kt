package com.example.mapin

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreModule(private val context : Context) {
    private val Context.dataStore by
    preferencesDataStore(name = "dataStore")

    private val tokenKey = stringPreferencesKey("TOKEN_KEY")


    val token : Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map {preferences ->
            preferences[tokenKey] ?: ""
        }

    suspend fun setToken(token : String){
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }


}