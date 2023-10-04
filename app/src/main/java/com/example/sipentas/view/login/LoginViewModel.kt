package com.example.sipentas.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.LoginModel
import com.example.sipentas.repositories.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repo: LoginRepository) : ViewModel() {

    fun login(nik: String, password: String) =
        viewModelScope.launch {
            try {
//                val mediaType = "application/json; charset=utf-8".toMediaType()
//                val rawData = """{
//                    "nik": "$nik",
//                    "password": "$password"
//                    }""".trimIndent()
//                val requestBody = rawData.toRequestBody(mediaType)
                repo.login(LoginModel(nik, password))
            } catch (e: Exception) {
                Log.e("ERROR LOGIN GABISA", e.toString())
            }
        }
}