package com.example.sipentas.repositories

import com.example.sipentas.models.AssesmenItem
import com.example.sipentas.models.AssesmenResponse
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.models.AssesmentResponse
import com.example.sipentas.models.KategoriPendidikanResponse
import com.example.sipentas.models.KategoriStatusOrtuResponse
import com.example.sipentas.models.KategoriSumberResponse
import com.example.sipentas.models.KategoriTempatTinggal
import com.example.sipentas.models.PekerjaanResponse
import com.example.sipentas.network.SipentasAPI
import okhttp3.MultipartBody
import javax.inject.Inject

class AssesmentRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun getAssesment():AssesmentResponse = api.getAssesment()
    suspend fun getPendidikan():List<KategoriPendidikanResponse> = api.getPendidikan()
    suspend fun getKasus():List<KategoriSumberResponse> = api.getSumber()
    suspend fun getPekerjaan():List<PekerjaanResponse> = api.getPekerjaan()
    suspend fun getStatusOrtu():List<KategoriStatusOrtuResponse> = api.getStatusOrtu()
    suspend fun getTempatTinggal():List<KategoriTempatTinggal> = api.getTempatTinggal()
    suspend fun addAssesmen(body:AssesmentBody): AssesmenResponse = api.addAssesmen(body)
    suspend fun addFile(file:MultipartBody.Part) = api.uploadPhoto(file)
}