package com.example.githubapplication

import android.util.Log
import androidx.lifecycle.*
import com.example.githubapplication.local.DatabaseModule
import com.example.githubapplication.local.FavoriteEntity
import com.example.githubapplication.remote.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(private val db: DatabaseModule) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailAccount = MutableLiveData<ItemsItem>()
    val detailAccount: LiveData<ItemsItem> = _detailAccount

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _successFavorite = MutableLiveData<Boolean>()
    val successFavorite: LiveData<Boolean> = _successFavorite

    private val _deleteFavorite = MutableLiveData<Boolean>()
    val deleteFavorite: LiveData<Boolean> = _deleteFavorite

    private var isFavorite = false

    companion object{
        private const val TAG = "DetailViewModel"
    }

    fun getDetailAccount(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailAccount(username)
        client.enqueue(object : Callback<ItemsItem> {
            override fun onResponse(
                call: Call<ItemsItem>,
                response: Response<ItemsItem>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailAccount.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ItemsItem>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    fun setFavorite(favoriteEntity: FavoriteEntity){
        viewModelScope.launch {
            favoriteEntity?.let {
                if (isFavorite){
                    db.favDao.deleteFavorite(favoriteEntity)
                    _deleteFavorite.value = true
                } else{
                    db.favDao.insert(favoriteEntity)
                    _successFavorite.value = true
                }
            }
            isFavorite = !isFavorite
        }
    }

    fun findFavoriteUser(username: String, listenFavorite: () -> Unit) {
        viewModelScope.launch {
            val favoriteUser = db.favDao.getFavoriteByUsername(username)
            if (favoriteUser != null){
                listenFavorite()
                isFavorite = true
            }
        }
    }

    class DetailFactory(private val db: DatabaseModule) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = DetailViewModel(db) as T
    }

}