package com.example.sipentas.view.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.LoginModel
import com.example.sipentas.repositories.LoginRepository
import com.example.sipentas.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repo: LoginRepository,val prefs:SharePrefs) : ViewModel() {

    fun login(nik: String, password: String,onError:(Exception) -> Unit,onSuccess:() -> Unit) =
        viewModelScope.launch {
            try {
                repo.login(LoginModel(nik, password)).let { item ->
                    item.token?.let { prefs.saveToken(it) }
                    Log.d("Sudah Tersimpan",getToken()!!)
                    onSuccess.invoke()
                }
            } catch (e: Exception) {
                Log.e("ERROR LOGIN GABISA", e.toString())
                onError.invoke(e)
            }
        }

    fun getToken():String? = prefs.getToken()
}