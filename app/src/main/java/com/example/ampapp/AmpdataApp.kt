package com.example.ampapp

import android.app.Application
import com.example.ampapp.Data.AppContainer
import com.example.ampapp.Data.DefaultAppContainer

class AmpdataApp:Application() {
    lateinit var Container: AppContainer
    override fun onCreate() {
        super.onCreate()
        Container = DefaultAppContainer()
    }
}