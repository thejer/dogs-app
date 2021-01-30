package io.budge.goobois.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import io.budge.goobois.main.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [APIServiceModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(target: MainActivity)

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: Application): Builder
    }
}
