package com.example.sipentas.repositories

import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.PmModel
import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class PmRepository @Inject constructor(private val api: SipentasAPI) {
    suspend fun getAllPm():List<PmModel> = api.getAllPm()
    suspend fun getPm():List<PmModel> = api.getPm()
    suspend fun getSearchAllPm(search:String):List<PmModel> = api.getAllSearchPm(search)
    suspend fun searchPm(search:String):List<PmModel> = api.searchPm(search)
    suspend fun deletePm(id:Int) = api.deletePm(id)



}