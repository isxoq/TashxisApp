package com.example.tashxis

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
}