package com.z1.meutreino

import android.app.Application
import com.z1.meutreino.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        bindKoin()
    }

    private fun bindKoin() {
        startKoin {
            androidContext(this@Application)
            modules(
                listOf(
                    appModule
                ).flatten()
            )
        }
    }
}