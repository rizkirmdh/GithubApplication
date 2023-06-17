package com.example.githubapplication.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubapplication.local.FavoriteEntity

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_user")
    fun getFavoriteEntity(): List<FavoriteEntity>

    @Query("SELECT * FROM favorite_user WHERE login = :username LIMIT 1")
    fun getFavoriteByUsername(username: String): FavoriteEntity

    @Delete
    fun deleteFavorite(favoriteEntity: FavoriteEntity)
}