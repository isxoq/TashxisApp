package com.example.tashxis

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import io.paperdb.Paper

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
    }
}