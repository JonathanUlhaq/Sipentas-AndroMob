package com.example.sipentas.repositories

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.PmModel
import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class PmRepository @Inject constructor(private val api: SipentasAPI) {
    suspend fun getAllPm():List<PmModel> = api.getAllPm()



}