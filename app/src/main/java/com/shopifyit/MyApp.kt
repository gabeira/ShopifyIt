package com.shopifyit

import android.app.Application
import com.shopifyit.di.AppComponent
import com.shopifyit.di.AppModule

import com.shopifyit.di.DaggerAppComponent

class MyApp : Application() {

    val component: AppComponent? by lazy {
        DaggerAppComponent
            .builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
    }
}