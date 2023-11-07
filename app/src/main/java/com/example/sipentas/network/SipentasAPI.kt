package com.example.sipentas.network

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.AddPmResponse
import com.example.sipentas.models.AssesmenItem
import com.example.sipentas.models.AssesmenResponse
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.models.AssesmentResponse
import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.AtensiResponse
import com.example.sipentas.models.DetailAssesmenResponse
import com.example.sipentas.models.DetailAtensiResponse
import com.example.sipentas.models.DetailPmResponse
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.KategoriPendidikanResponse
import com.example.sipentas.models.KategoriStatusOrtuResponse
import com.example.sipentas.models.KecamatanModel
import com.example.sipentas.models.KelurahanModel
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.LoginResponse
import com.example.sipentas.models.PmModel
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.models.KategoriSumberResponse
import com.example.sipentas.models.KategoriTempatTinggal
import com.example.sipentas.models.PekerjaanResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.models.PmUpdateBody
import com.example.sipentas.models.UserDataResponse
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.models.verifikasi_assesment.VerifikasiAssesmentResponse
import com.example.sipentas.models.verifikasi_atensi.VerifikasiAtensiResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query


interface SipentasAPI {

    @DELETE("session")
    suspend fun logout()

    @Multipart
    @POST("uploadFile")
    suspend fun uploadPhoto(
        @Part file:MultipartBody.Part
    ):UploadResponse



    @POST("assessment/create")
    suspend fun addAssesmen(
        @Body body:AssesmentBody
    ):AssesmenResponse

    @PUT("assessment/update/{id}")
    suspend fun updateAssesment(@Path("id")id:Int, @Body body:AssesmentBody)

    @PUT("update-atensi/{id}")
    suspend fun updateAtensi(@Path("id")id:Int, @Body body:AtensiBody)

    @POST("login")
    suspend fun login(@Body body:LoginModel):LoginResponse

    @PUT("/pm/edit/{id}")
    suspend fun updatePm(@Path("id")id: Int,@Body body:PmUpdateBody)



    @POST("create-pm")
    suspend fun addPm(@Body body:PostPmModel):AddPmResponse

    @DELETE("delete-pm/{id}")
    suspend fun deletePm(@Path("id")id:Int)

    @GET("pm-by-sentra")
    suspend fun getAllPm():List<PmModel>

    @GET("pm-by-sentra")
    suspend fun searchPm(@Query("search")search:String):List<PmModel>

    @GET("assessment-by-sentra")
    suspend fun getAssesment():AssesmentResponse

    @GET("assessment-by-sentra")
    suspend fun searchAssesment(@Query("search")search:String):AssesmentResponse
    @GET("provinsi")
    suspend fun getProvinsi():List<ProvinsiModel>

    @GET("detail-pm/{id}")
    suspend fun getDetailPm(
        @Path("id")id:Int
    ):DetailPmResponse

    @GET("detail-asesmen/{id}")
    suspend fun getDetailAssesmen(
        @Path("id")id:Int
    ):DetailAssesmenResponse

    @GET("detail-atensi/{id}")
    suspend fun getDetailAtensi(
        @Path("id")id:Int
    ):DetailAtensiResponse

    @DELETE("delete-atensi/{id}")
    suspend fun deleteAtensi(@Path("id")id:Int)

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

    @GET("pendidikan")
    suspend fun getPendidikan():List<KategoriPendidikanResponse>
    @GET("sumber-kasus")
    suspend fun getSumber():List<KategoriSumberResponse>
    @GET("pekerjaan")
    suspend fun getPekerjaan():List<PekerjaanResponse>
    @GET("status-ortu")
    suspend fun getStatusOrtu():List<KategoriStatusOrtuResponse>
    @GET("tempat-tinggal")
    suspend fun getTempatTinggal():List<KategoriTempatTinggal>

    @GET("jenis-atensi")
    suspend fun getJenisAtensi():List<JenisAtensiResponse>
    @GET("pendekatan-atensi")
    suspend fun getPendekatanAtensi():List<PendekatanAtensiResponse>

    @GET("atensi-by-sentra")
    suspend fun getAtensi():AtensiResponse

    @GET("atensi-by-sentra")
    suspend fun searchAtensi(@Query("search")search:String):AtensiResponse

    @POST("create-atensi")
    suspend fun addAtensi(@Body body:AtensiBody)

    @GET("user")
    suspend fun getUSer():List<UserDataResponse>

    @GET("assessment-by-pm/{id}")
    suspend fun getAssesmentPm(
        @Path("id")id:Int
    ):VerifikasiAssesmentResponse
    @DELETE("delete-asesmen/{id}")
    suspend fun deleteAssesmen(@Path("id")id:Int)
    @GET("atensi-by-asesmen/{id}")
    suspend fun getAtensiAssesment(
        @Path("id")id:Int
    ):VerifikasiAtensiResponse
}