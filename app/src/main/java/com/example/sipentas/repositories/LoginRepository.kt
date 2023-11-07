package com.example.sipentas.repositories

import android.service.autofill.UserData
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.LoginResponse
import com.example.sipentas.models.UserDataResponse
import com.example.sipentas.network.SipentasAPI
import okhttp3.RequestBody
import javax.inject.Inject

class LoginRepository @Inject constructor(val api:SipentasAPI) {
    suspend fun login(body:LoginModel):LoginResponse = api.login(body)
    suspend fun getUser():List<UserDataResponse> = api.getUSer()
}