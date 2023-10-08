package com.example.sipentas.view.form_pm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.repositories.PmFormRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        getProvinsi()
        getKategori()
    }

}