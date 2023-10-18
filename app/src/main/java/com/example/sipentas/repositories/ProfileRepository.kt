package com.example.sipentas.repositories

import com.example.sipentas.network.SipentasAPI
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val api:SipentasAPI) {
    suspend fun logout() = api.logout()
}