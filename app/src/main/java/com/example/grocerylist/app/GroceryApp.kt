package com.example.grocerylist.app

import android.app.Application
import com.bumptech.glide.Glide
import com.example.grocerylist.home.ui.Graph

class GroceryApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
        Glide.get(this)
    }
}