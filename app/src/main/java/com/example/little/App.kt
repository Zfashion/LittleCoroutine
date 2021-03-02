package com.example.little

import android.app.Application
import android.content.Context

class App: Application() {

    companion object {
        private lateinit var appContext: Context

        fun getAppContext() = appContext
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        base?.also { appContext = it }
    }

}