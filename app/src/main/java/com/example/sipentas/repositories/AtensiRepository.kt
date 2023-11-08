package com.example.sipentas.repositories

import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.AtensiResponse
import com.example.sipentas.models.DetailAtensiResponse
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.models.verifikasi_atensi.VerifikasiAtensiResponse
import com.example.sipentas.network.SipentasAPI
import okhttp3.MultipartBody
import javax.inject.Inject

class AtensiRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun getJenisAtensi():List<JenisAtensiResponse> = api.getJenisAtensi()
    suspend fun getPendekatanAtensi():List<PendekatanAtensiResponse> = api.getPendekatanAtensi()
    suspend fun getAtensi(): AtensiResponse = api.getAtensi()
    suspend fun getAtensiAll(): AtensiResponse = api.getAtensiAll()
    suspend fun searchAtensiAll(search:String):AtensiResponse = api.searchAtensiAll(search)
    suspend fun searchAtensi(search:String):AtensiResponse = api.searchAtensi(search)
    suspend fun addAtensi(body:AtensiBody) = api.addAtensi(body)
    suspend fun insertPhoto(file:MultipartBody.Part) = api.uploadPhoto(file)

    suspend fun getDetailAtensi(id:Int):DetailAtensiResponse = api.getDetailAtensi(id)

    suspend fun getAtensiAssesment(id:Int):VerifikasiAtensiResponse = api.getAtensiAssesment(id)
    suspend fun updateAtensi(id:Int,body:AtensiBody) = api.updateAtensi(id, body = body)
    suspend fun deleteAtensi(id:Int) = api.deleteAtensi(id)
}
