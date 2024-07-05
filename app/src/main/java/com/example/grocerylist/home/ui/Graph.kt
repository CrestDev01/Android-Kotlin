package com.example.grocerylist.home.ui

import android.content.Context
import androidx.room.Room
import com.example.grocerylist.home.data.WishDatabase
import com.example.grocerylist.home.repository.WishRepository

object Graph {
    private lateinit var database: WishDatabase

    val wishRepository by lazy{
        WishRepository(wishDao = database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wishlist.db").build()
    }
}