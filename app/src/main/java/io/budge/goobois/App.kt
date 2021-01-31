package io.budge.goobois

import android.app.Application
import io.budge.goobois.di.AppComponent
import io.budge.goobois.di.DaggerAppComponent


class App : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .application(this)
                .build()
    }
}
