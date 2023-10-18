package com.example.sipentas.repositories

import com.example.sipentas.models.AssesmentResponse
import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class AssesmentRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun getAssesment():AssesmentResponse = api.getAssesment()
}