package com.shopifyit.di

import android.app.Application
import android.content.Context
import com.shopifyit.MyApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by gabeira@gmail.com on 26/11/18.
 */
@Module
open class AppModule(private val app: Application) {

    @Provides
    fun provideContext(application: MyApp): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApplication() = app

}