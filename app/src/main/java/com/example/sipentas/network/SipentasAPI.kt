package com.example.sipentas.network

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.AssesmentResponse
import com.example.sipentas.models.KecamatanModel
import com.example.sipentas.models.KelurahanModel
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.LoginResponse
import com.example.sipentas.models.PmModel
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.models.upload_file.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path


interface SipentasAPI {

    @DELETE("session")
    suspend fun logout()

    @Multipart
    @POST("uploadFile")
    suspend fun uploadPhoto(
        @Part file:MultipartBody.Part
    ):UploadResponse


    @POST("login")
    suspend fun login(@Body body:LoginModel):LoginResponse

    @POST("create-pm")
    suspend fun addPm(@Body body:PostPmModel)
    @GET("pm")
    suspend fun getAllPm():List<PmModel>
    @GET("assessment-by-sentra")
    suspend fun getAssesment():AssesmentResponse
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

    @GET("kecamatan/{id}")
    suspend fun getKecamatan(
        @Path("id")id:Int
    ):List<KecamatanModel>

    @GET("kelurahan/{id}")
    suspend fun getKelurahan(
        @Path("id")id:Int
    ):List<KelurahanModel>

    @GET("kategori")
    suspend fun getKategori():List<KategoriModel?>
}