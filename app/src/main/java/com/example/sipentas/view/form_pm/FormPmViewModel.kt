package com.example.sipentas.view.form_pm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.AddPmResponse
import com.example.sipentas.models.KecamatanModel
import com.example.sipentas.models.KelurahanModel
import com.example.sipentas.models.PmUpdateBody
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.models.upload_file.UploadResponse
import com.example.sipentas.repositories.PmFormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class FormPmViewModel @Inject constructor(val repo: PmFormRepository) : ViewModel() {

    private val _provinsiState = MutableStateFlow<List<ProvinsiModel>>(emptyList())
    val provinsi = _provinsiState.asStateFlow()

    private val _kabupatenState = MutableStateFlow<List<KabupatenModel>>(emptyList())
    val kabupaten = _kabupatenState.asStateFlow()

    private val _kategoriState = MutableStateFlow<List<KategoriModel?>>(emptyList())
    val kategori = _kategoriState.asStateFlow()

    private val _ragamState = MutableStateFlow<List<RagamModel>>(emptyList())
    val ragam = _ragamState.asStateFlow()

    private val _kecamatan = MutableStateFlow<List<KecamatanModel>>(emptyList())
    val kecamatan = _kecamatan.asStateFlow()

    private val _kelurahan = MutableStateFlow<List<KelurahanModel>>(emptyList())
    val kelurahan = _kelurahan.asStateFlow()

    fun postPhoto(
        photo:MultipartBody.Part,
        onError:() -> Unit,
        success:(UploadResponse) -> Unit
    ) =
        viewModelScope.launch {
            try {
                repo.insertPhoto(photo).let {
//                    Log.d("Berhasil Loh","Size: ${it.size}")
                    success.invoke(it)
                }

            } catch (e:Exception) {
                Log.e("ERROR","Gagal Upload $e" )
                onError.invoke()
            }
        }
    fun addPm(
        body:PostPmModel,
        loading:MutableState<Boolean>,
        onError:() -> Unit,
        success:(AddPmResponse) -> Unit

    ) =
        viewModelScope.launch {
            loading.value =true
            try {
                repo.addPm(body).let {
                    loading.value = false
                    success.invoke(it)
                }

            } catch (e:Exception) {
                Log.e("ERROR","Gagal Post $e")
                loading.value = false
                onError.invoke()
            }
        }

    fun updatePm(
        body:PmUpdateBody,
        id:Int,
        onError:() -> Unit,
        success:() -> Unit

    ) =
        viewModelScope.launch {
            try {
                repo.updatePm(id,body).let {
                    success.invoke()
                }

            } catch (e:Exception) {
                Log.e("ERROR","Gagal Post $e")
                onError.invoke()
            }
        }

    fun getKategori() =
        viewModelScope.launch {
            try {
                repo.getKategori().let { item ->
                    _kategoriState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }
    fun getProvinsi() =
        viewModelScope.launch {
            try {
                repo.getProvinsi().let { item ->
                    _provinsiState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA PROVINSI", e.toString())
            }
        }

    fun getRagam(id: Int) =
        viewModelScope.launch {
            try {
                repo.getRagam(id).let { item ->
                    _ragamState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA PROVINSI", e.toString())
            }
        }

    fun getKelurahan(id: Int) =
        viewModelScope.launch {
            try {
                repo.getKelurahan(id).let { item ->
                    _kelurahan.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA PROVINSI", e.toString())
            }
        }

    fun getKecamatan(id: Int) =
        viewModelScope.launch {
            try {
                repo.getKecamatan(id).let { item ->
                    _kecamatan.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA PROVINSI", e.toString())
            }
        }

    fun getKabupaten(id: Int) =
        viewModelScope.launch {
            try {
                repo.getKabupaten(id).let { item ->
                    _kabupatenState.value = item
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA PROVINSI", e.toString())
            }
        }

    init {

    }

}