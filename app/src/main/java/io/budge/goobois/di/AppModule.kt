package io.budge.goobois.di

import dagger.Module
import dagger.Provides
import io.budge.goobois.data.api.DogsApiService
import io.budge.goobois.data.api.DogsRepository
import io.budge.goobois.data.api.IDogsRepository
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNoteRepository(dogsApiService: DogsApiService): IDogsRepository {
        return DogsRepository(dogsApiService)
    }
}