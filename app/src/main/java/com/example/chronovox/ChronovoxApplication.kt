package com.example.chronovox

import android.app.Application
import com.google.firebase.FirebaseApp

class ChronovoxApplication:Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}