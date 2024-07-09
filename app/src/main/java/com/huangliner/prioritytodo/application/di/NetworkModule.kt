package com.huangliner.prioritytodo.application.di

import com.huangliner.prioritytodo.application.util.Constants.Companion.BACKEND_URL
import com.huangliner.prioritytodo.application.util.Constants.Companion.HTTP_CONNECT_TIMEOUT
import com.huangliner.prioritytodo.application.util.Constants.Companion.HTTP_READ_TIMEOUT
import com.huangliner.prioritytodo.application.util.OkhttpInterceptor
import com.huangliner.prioritytodo.data.network.Backend
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun providerApiService(retrofit: Retrofit):Backend{
        return retrofit.create(Backend::class.java)
    }

    @Singleton
    @Provides
    fun providerRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun providerConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providerHttpClient(
        interceptor: OkhttpInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun providerInterceptor(): OkhttpInterceptor {
        return OkhttpInterceptor()
    }
}