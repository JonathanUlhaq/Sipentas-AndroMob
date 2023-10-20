package com.example.sipentas.repositories

import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class AtensiRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun getJenisAtensi():List<JenisAtensiResponse> = api.getJenisAtensi()
    suspend fun getPendekatanAtensi():List<PendekatanAtensiResponse> = api.getPendekatanAtensi()
}
