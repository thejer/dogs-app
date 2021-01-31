package io.budge.goobois.di

import com.google.gson.Gson
import dagger.Lazy
import dagger.Module
import dagger.Provides
import io.budge.goobois.BuildConfig
import io.budge.goobois.data.api.DogsApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class APIServiceModule {

    @Provides
    @Named("DogsApiService")
    @Singleton
    fun provideDogsServiceHttpClient(
        upstream: OkHttpClient,
    ): OkHttpClient {
        return upstream.newBuilder().build()
    }


    @Provides
    @Singleton
    fun provideDogsAPIService(
        @Named("DogsApiService") client: Lazy<OkHttpClient>,
        gson: Gson
    ): DogsApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.thedogapi.com/")
            .client(client.get())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(DogsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGenericOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)
}
