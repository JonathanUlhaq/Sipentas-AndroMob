package com.example.sipentas.network

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.LoginResponse
import com.example.sipentas.models.PmModel
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface SipentasAPI {
    @POST("login")
    suspend fun login(@Body body:LoginModel):LoginResponse
    @GET("pm")
    suspend fun getAllPm():List<PmModel>
    @GET("provinsi")
    suspend fun getProvinsi():List<ProvinsiModel>

    @GET("kabupaten/{id}")
    suspend fun getKabupaten(
        @Path("id")id:Int
    ):List<KabupatenModel>

    @GET("ragam/{id}")
    suspend fun getRagam(
        @Path("id")id:Int
    ):List<RagamModel>

    @GET("kategori")
    suspend fun getKategori():List<KategoriModel?>
}