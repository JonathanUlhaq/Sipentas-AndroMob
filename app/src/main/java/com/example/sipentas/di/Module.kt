package com.example.sipentas.di

import com.example.sipentas.network.AuthInterceptor
import com.example.sipentas.network.SipentasAPI
import com.example.sipentas.utils.ConstValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class Module {

    @Provides
    @Singleton
    fun clientProvider(authInterceptor: AuthInterceptor):OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()

    @Provides
    @Singleton
    fun retrofitProvider(client: OkHttpClient):SipentasAPI =
        Retrofit.Builder()
            .baseUrl(ConstValue.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SipentasAPI::class.java)
}