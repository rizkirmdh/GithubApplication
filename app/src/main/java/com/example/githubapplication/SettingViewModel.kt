package com.example.githubapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubapplication.local.SettingPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val preference: SettingPreferences) : ViewModel(){
    fun getTheme() = preference.getThemeSetting().asLiveData()

    fun saveTheme(isActive: Boolean){
        viewModelScope.launch {
            preference.saveThemeSetting(isActive)
        }
    }

    class SettingFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = SettingViewModel(pref) as T
    }
}