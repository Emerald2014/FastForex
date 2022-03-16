package ru.kudesnik.fastforex

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this

        startKoin {
            modules(appModule)
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var appInstance: Context
    }
}