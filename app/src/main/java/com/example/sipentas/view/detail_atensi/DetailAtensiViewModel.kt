package com.example.sipentas.view.detail_atensi

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.AtensiResponse
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.repositories.AtensiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class DetailAtensiViewModel @Inject constructor(private val repo:AtensiRepository):ViewModel() {
    private val _jenisAtens = MutableStateFlow<List<JenisAtensiResponse>>(emptyList())
    val jenisAtensi = _jenisAtens.asStateFlow()

    private val _pendekatanAtensi = MutableStateFlow<List<PendekatanAtensiResponse>>(emptyList())
    val pendekatanAtensi = _pendekatanAtensi.asStateFlow()

    private val _uiState = MutableStateFlow(AtensiResponse())
    val uiState = _uiState.asStateFlow()

    fun postPhoto(
        photo: MultipartBody.Part,
        onError:() -> Unit,
        success:(UploadResponse) -> Unit
    ) =
        viewModelScope.launch {
            try {
                repo.insertPhoto(photo).let {
//                    Log.d("Berhasil Loh","Size: ${it.size}")
                    success.invoke(it)
                }

            } catch (e: java.lang.Exception) {
                Log.e("ERROR","Gagal Upload $e" )
                onError.invoke()
            }
        }
    fun addAtensi(body:AtensiBody,
                  onLoadingAtensi:MutableState<Boolean>,
                  onSuccess:() -> Unit) =
        viewModelScope.launch {
            onLoadingAtensi.value = true
            try {
                repo.addAtensi(body).let {
                    onSuccess.invoke().let {
                        onLoadingAtensi.value = false
                    }
                }
            } catch (e:Exception) {
                onLoadingAtensi.value = false
                Log.e("ERROR POST",e.toString())
            }
        }
    fun getAtensi() =
        viewModelScope.launch {
            try {
                repo.getAtensi().let {
                    _uiState.value = it
                }
            } catch (e:Exception) {
                Log.e("ERROR GET",e.toString())
            }
        }

    fun getJenisAtensi() =
        viewModelScope.launch {
            try {
                repo.getJenisAtensi().let {
                    _jenisAtens.value = it
                }
            } catch (e:Exception) {
                Log.e("ERROR GET",e.toString())
            }
        }

    fun getPendekatanAtensi() =
        viewModelScope.launch {
            try {
                repo.getPendekatanAtensi().let {
                    _pendekatanAtensi.value = it
                }
            } catch (e:Exception) {
                Log.e("ERROR GET",e.toString())
            }
        }
}