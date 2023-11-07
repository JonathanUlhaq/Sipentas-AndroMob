package com.example.sipentas.view.assessment

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.models.AssesmentResponse
import com.example.sipentas.models.DetailAssesmenResponse
import com.example.sipentas.models.KategoriPendidikanResponse
import com.example.sipentas.models.KategoriStatusOrtuResponse
import com.example.sipentas.models.KategoriSumberResponse
import com.example.sipentas.models.KategoriTempatTinggal
import com.example.sipentas.models.PekerjaanResponse
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.models.verifikasi_atensi.VerifikasiAtensiResponse
import com.example.sipentas.repositories.AssesmentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class AssesmenViewModel @Inject constructor(private val repo: AssesmentRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(AssesmentResponse())
    val uiState = _uiState.asStateFlow()

    private val _pendidikan = MutableStateFlow<List<KategoriPendidikanResponse>>(emptyList())
    val pendidikan = _pendidikan.asStateFlow()

    private val _detailAssesmen = MutableStateFlow(DetailAssesmenResponse())
    val detailAssesmen = _detailAssesmen.asStateFlow()

    private val _atensiAssesment = MutableStateFlow(VerifikasiAtensiResponse())
    val atensiAssesment = _atensiAssesment.asStateFlow()

    fun deleteAssesment(
        id: Int,
        loading: MutableState<Boolean>,
        onError: (String) -> Unit,
        onSuccess: () -> Unit
    ) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.deleteAssesment(id).let {
                    loading.value = false
                    onSuccess.invoke()
                }
            } catch (e:Exception) {
                Log.d("ERROR HAPUS",e.toString())
                loading.value = false
                onError.invoke(e.toString())
            }
        }

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

    fun updateAssesment(
        id: Int,
        body: AssesmentBody,
        loading: MutableState<Boolean>,
        onSuccess: () -> Unit
    ) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.updateAssesmen(id, body).let {
                    loading.value = false
                    onSuccess.invoke()
                }
            } catch (e: Exception) {
                loading.value = false
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

    fun getDetailAssesment(id: Int) =
        viewModelScope.launch {
            try {
                repo.getDetailAssesmen(id).let { item ->
                    _detailAssesmen.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    fun getPendidikan() =
        viewModelScope.launch {
            try {
                repo.getPendidikan().let { item ->
                    _pendidikan.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    private val _sumber = MutableStateFlow<List<KategoriSumberResponse>>(emptyList())
    val sumber = _sumber.asStateFlow()

    fun getSumber() =
        viewModelScope.launch {
            try {
                repo.getKasus().let { item ->
                    _sumber.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    private val _pekerjaan = MutableStateFlow<List<PekerjaanResponse>>(emptyList())
    val pekerjaan = _pekerjaan.asStateFlow()

    fun getPekerjaan() =
        viewModelScope.launch {
            try {
                repo.getPekerjaan().let { item ->
                    _pekerjaan.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    private val _statusOrtu = MutableStateFlow<List<KategoriStatusOrtuResponse>>(emptyList())
    val statusOrtu = _statusOrtu.asStateFlow()

    fun getStatusOrtu() =
        viewModelScope.launch {
            try {
                repo.getStatusOrtu().let { item ->
                    _statusOrtu.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    private val _tempatTinggal = MutableStateFlow<List<KategoriTempatTinggal>>(emptyList())
    val tempatTinggal = _tempatTinggal.asStateFlow()

    fun getTempatTinggal() =
        viewModelScope.launch {
            try {
                repo.getTempatTinggal().let { item ->
                    _tempatTinggal.value = item
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    fun getAssesmen() =
        viewModelScope.launch {
            try {
                repo.getAssesment().let { item ->
                    _uiState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }

    fun searchAssesment(search:String) =
        viewModelScope.launch {
            try {
                repo.searchAssesment(search).let { item ->
                    _uiState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }

    fun addAssesmen(
        body: AssesmentBody,
        onLoadingAssesmen: MutableState<Boolean>,
        onFailuer: () -> Unit = {},
        onSuccess: (Int) -> Unit
    ) =
        viewModelScope.launch {
            try {
                repo.addAssesmen(body).let {
                    Log.d("Berhasil ye", "SUKSES")
                    onSuccess.invoke(it.rows?.get(0)!!.id!!)
                }
            } catch (e: Exception) {
                onFailuer.invoke()
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

    fun addAssesmenFile(file: MultipartBody.Part, success: (UploadResponse) -> Unit) =
        viewModelScope.launch {
            try {
                repo.addFile(file).let {
                    Log.d("Berhasil ye", "SUKSES")
                    success.invoke(it)
                }
            } catch (e: Exception) {
                Log.d("ERROR GET DATA ", e.toString())
            }
        }

}