package com.example.sipentas.repositories

import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.AtensiResponse
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.network.SipentasAPI
import okhttp3.MultipartBody
import javax.inject.Inject

class AtensiRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun getJenisAtensi():List<JenisAtensiResponse> = api.getJenisAtensi()
    suspend fun getPendekatanAtensi():List<PendekatanAtensiResponse> = api.getPendekatanAtensi()
    suspend fun getAtensi(): AtensiResponse = api.getAtensi()
    suspend fun addAtensi(body:AtensiBody) = api.addAtensi(body)
    suspend fun insertPhoto(file:MultipartBody.Part) = api.uploadPhoto(file)
}
