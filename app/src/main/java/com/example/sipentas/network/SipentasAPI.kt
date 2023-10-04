package com.example.sipentas.network

import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.PmModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


interface SipentasAPI {
    @POST("login")
    suspend fun login(@Body body:LoginModel)

    @GET("pm")
    suspend fun getAllPm():List<PmModel>
}