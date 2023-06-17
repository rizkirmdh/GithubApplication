package com.example.githubapplication.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.prefDataStore by preferencesDataStore("setting")

class SettingPreferences constructor(context: Context) {
    private val settingDatastore = context.prefDataStore
    private val themeSwitcher = booleanPreferencesKey("theme_switch")

    fun getThemeSetting(): Flow<Boolean> = settingDatastore.data.map {
        it[themeSwitcher] ?: false
    }

    suspend fun saveThemeSetting(isActive: Boolean){
        settingDatastore.edit {
            it[themeSwitcher] = isActive
        }
    }
}