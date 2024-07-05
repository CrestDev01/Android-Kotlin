package com.example.grocerylist.home.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.grocerylist.home.data.Wish
import com.example.grocerylist.home.data.WishDao

@Database(
    entities = [Wish::class],
    version = 1,
    exportSchema = false
)
abstract class WishDatabase : RoomDatabase() {
    abstract fun wishDao(): WishDao
}