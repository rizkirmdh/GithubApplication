package com.example.githubapplication

import android.util.Log
import androidx.lifecycle.*
import com.example.githubapplication.local.SettingPreferences
import com.example.githubapplication.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val preferences: SettingPreferences): ViewModel() {
    private val _account = MutableLiveData<List<ItemsItem>>()
    val account: LiveData<List<ItemsItem>> = _account

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        private const val username = "arif"
    }

    init {
        findAccount(username)
    }

    fun findAccount(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchAccount(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _account.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getTheme() = preferences.getThemeSetting().asLiveData()

    class MainFactory(private val preferences: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T =
            MainViewModel(preferences) as T
    }
}