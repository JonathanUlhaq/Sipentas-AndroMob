package com.example.sipentas.view.detail_atensi

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.AtensiResponse
import com.example.sipentas.models.DetailAtensiResponse
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.models.verifikasi_atensi.VerifikasiAtensiResponse
import com.example.sipentas.repositories.AtensiRepository
import com.example.sipentas.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class DetailAtensiViewModel @Inject constructor(private val repo: AtensiRepository,val pref:SharePrefs) : ViewModel() {
    private val _jenisAtens = MutableStateFlow<List<JenisAtensiResponse>>(emptyList())
    val jenisAtensi = _jenisAtens.asStateFlow()

    private val _pendekatanAtensi = MutableStateFlow<List<PendekatanAtensiResponse>>(emptyList())
    val pendekatanAtensi = _pendekatanAtensi.asStateFlow()

    private val _uiState = MutableStateFlow(AtensiResponse())
    val uiState = _uiState.asStateFlow()

    private val _detailAtensi = MutableStateFlow(DetailAtensiResponse())
    val detailAtensi = _detailAtensi.asStateFlow()

    private val _atensiAssesment = MutableStateFlow(VerifikasiAtensiResponse())
    val atensiAssesment = _atensiAssesment.asStateFlow()

    fun deleteAtensi(
        id: Int,
        loading: MutableState<Boolean>,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.deleteAtensi(id).let {
                    loading.value = false
                    onSuccess.invoke()
                }
            } catch (e:Exception) {
                Log.d("ERROR HAPUS",e.toString())
                loading.value = false
                onError.invoke(e.toString())
            }
        }

    fun getAtensiAssesment(id: Int) =
        viewModelScope.launch {
            try {
                repo.getAtensiAssesment(id).let { item ->
                    _atensiAssesment.value = item
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
            }
        }

    fun getDetailAtensi(id: Int) =
        viewModelScope.launch {
            try {
                repo.getDetailAtensi(id).let {
                    _detailAtensi.value = it
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
            }
        }

    fun postPhoto(
        photo: MultipartBody.Part,
        onError: () -> Unit,
        success: (UploadResponse) -> Unit
    ) =
        viewModelScope.launch {
            try {
                repo.insertPhoto(photo).let {
//                    Log.d("Berhasil Loh","Size: ${it.size}")
                    success.invoke(it)
                }

            } catch (e: java.lang.Exception) {
                Log.e("ERROR", "Gagal Upload $e")
                onError.invoke()
            }
        }

    fun addAtensi(
        body: AtensiBody,
        onLoadingAtensi: MutableState<Boolean>,
        onFailure: (String) -> Unit,
        onSuccess: () -> Unit
    ) =
        viewModelScope.launch {
            onLoadingAtensi.value = true
            try {
                repo.addAtensi(body).let {
                    onSuccess.invoke().let {
                        onLoadingAtensi.value = false
                    }
                }
            } catch (e: Exception) {
                onLoadingAtensi.value = false
                Log.e("ERROR POST", e.toString())
                onFailure.invoke(e.toString())
            }
        }


    fun updateAtensi(
        id: Int,
        body: AtensiBody,
        onLoadingAtensi: MutableState<Boolean>,
        onSuccess: () -> Unit
    ) =
        viewModelScope.launch {
            onLoadingAtensi.value = true
            try {
                repo.updateAtensi(id, body).let {
                    onSuccess.invoke().let {
                        onLoadingAtensi.value = false
                    }
                }
            } catch (e: Exception) {
                onLoadingAtensi.value = false
                Log.e("ERROR POST", e.toString())
            }
        }

    fun getAtensi(loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.getAtensi().let {
                    _uiState.value = it
                    loading.value = false
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
                loading.value = false
            }
        }

    fun getAtensiAll(loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.getAtensiAll().let {
                    _uiState.value = it
                    loading.value = false
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
                loading.value = false
            }
        }
    fun searchAtensiAll(search:String,loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.searchAtensiAll(search).let {
                    _uiState.value = it
                    loading.value = false
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
                loading.value = false
            }
        }
    fun searchAtensi(search:String,loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.searchAtensi(search).let {
                    _uiState.value = it
                    loading.value = false
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
                loading.value = false
            }
        }

    fun getJenisAtensi() =
        viewModelScope.launch {
            try {
                repo.getJenisAtensi().let {
                    _jenisAtens.value = it
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
            }
        }

    fun getPendekatanAtensi() =
        viewModelScope.launch {
            try {
                repo.getPendekatanAtensi().let {
                    _pendekatanAtensi.value = it
                }
            } catch (e: Exception) {
                Log.e("ERROR GET", e.toString())
            }
        }
}