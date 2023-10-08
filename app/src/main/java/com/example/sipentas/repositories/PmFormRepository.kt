package com.example.sipentas.repositories

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class PmFormRepository @Inject constructor(val api:SipentasAPI)  {
    suspend fun getProvinsi():List<ProvinsiModel> = api.getProvinsi()
    suspend fun getKabupaten(id:Int):List<KabupatenModel> = api.getKabupaten(id)
    suspend fun getRagam(id:Int):List<RagamModel> = api.getRagam(id)
    suspend fun getKategori():List<KategoriModel?> = api.getKategori()
}