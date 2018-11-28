package com.shopifyit.di

import android.app.Application
import com.shopifyit.view.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by gabeira@gmail.com on 26/11/18.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(app: Application)

    fun inject(app: MainActivity)
}