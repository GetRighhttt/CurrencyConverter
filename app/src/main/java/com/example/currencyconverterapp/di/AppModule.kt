package com.example.currencyconverterapp.di

import com.example.currencyconverterapp.data.api.ApiService
import com.example.currencyconverterapp.domain.repository.Repository
import com.example.currencyconverterapp.data.repo.RepositoryImpl
import com.example.currencyconverterapp.domain.util.Constants
import com.example.currencyconverterapp.domain.util.DispatcherProvider
import com.example.currencyconverterapp.presentation.viewmodel.CurrencyViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/*
Dependency Injection module
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideHttpInterceptor(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        /**
         * Now we create an OKHTTPClient Instance.
         *
         * And we will show how to manually do connection timeouts just in case
         * somebody has slow internet.
         */
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
        }.build()

        return client
    }

    /*
    Api service, Repo, and Dispatchers provides methods.
     */
    @Singleton
    @Provides
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(provideHttpInterceptor())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): Repository = RepositoryImpl(apiService)

    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider = object : DispatcherProvider {
        override val mainCD: CoroutineDispatcher
            get() = Dispatchers.Main
        override val ioCD: CoroutineDispatcher
            get() = Dispatchers.IO
        override val defaultCD: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfinedCD: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    fun provideCurrencyViewModelFactory(
        repository: Repository,
        dispatchers: DispatcherProvider
    ): CurrencyViewModelFactory {
        return CurrencyViewModelFactory(repository, dispatchers)
    }
}