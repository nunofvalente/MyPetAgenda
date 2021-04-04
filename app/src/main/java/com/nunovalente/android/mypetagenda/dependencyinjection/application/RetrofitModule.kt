package com.nunovalente.android.mypetagenda.dependencyinjection.application

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nunovalente.android.mypetagenda.R
import com.nunovalente.android.mypetagenda.application.MyApplication
import com.nunovalente.android.mypetagenda.networking.MapsApi
import com.nunovalente.android.mypetagenda.networking.UrlProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

@Module
class RetrofitModule(private val application: MyApplication) {

    @Provides
    fun moshi(): Moshi = Moshi.Builder()
        .build()

    @Provides
    fun urlProvides() = UrlProvider()

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original
                    .url
                    .newBuilder()
                    .addQueryParameter("key", application.getString(R.string.google_api_key))
                    .build()
                val request = original
                    .newBuilder().url(url)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @AppScope
    fun retrofit(urlProvider: UrlProvider, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(urlProvider.getBaseUrl())
            .client(getClient())
            .build()
    }

    @Provides
    @AppScope
    fun mapsApi(retrofit: Retrofit) = retrofit.create(MapsApi::class.java)
}