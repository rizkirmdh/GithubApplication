package com.example.githubapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubapplication.local.DatabaseModule
import com.example.githubapplication.local.FavoriteEntity

class FavoriteViewModel(private val databaseModule: DatabaseModule) : ViewModel() {

    private val _favoriteUser = MutableLiveData<List<FavoriteEntity>>()
    val favoriteUser: LiveData<List<FavoriteEntity>> = _favoriteUser

    fun getAllFavoriteUser() {
       _favoriteUser.value = databaseModule.favDao.getFavoriteEntity()
    }

    class FavoriteFactory(private val databaseModule: DatabaseModule) : ViewModelProvider.NewInstanceFactory(){
        override fun <T : ViewModel> create(modelClass: Class<T>): T = FavoriteViewModel(databaseModule) as T
    }
}