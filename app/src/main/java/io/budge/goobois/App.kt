package io.budge.goobois

import android.app.Application
import io.budge.goobois.di.AppComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class App : Application() {

//    private var applicationScope = CoroutineScope(Dispatchers.Default)
//    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

//        component = DaggerAppComponent.builder()
//                .application(this)
//                .build()
//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }
    }
}
