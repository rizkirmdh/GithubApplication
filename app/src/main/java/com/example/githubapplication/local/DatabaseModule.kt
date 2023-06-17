package com.example.githubapplication.local

import android.content.Context
import androidx.room.Room

class DatabaseModule(private val context: Context) {
    private val db = Room.databaseBuilder(context, FavoriteDatabase::class.java, "favorite.db")
        .allowMainThreadQueries()
        .build()

    val favDao = db.favDao()
}