package com.example.sipentas.view.detail_atensi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.JenisAtensiResponse
import com.example.sipentas.models.PendekatanAtensiResponse
import com.example.sipentas.repositories.AtensiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailAtensiViewModel @Inject constructor(private val repo:AtensiRepository):ViewModel() {
    private val _jenisAtens = MutableStateFlow<List<JenisAtensiResponse>>(emptyList())
    val jenisAtensi = _jenisAtens.asStateFlow()

    private val _pendekatanAtensi = MutableStateFlow<List<PendekatanAtensiResponse>>(emptyList())
    val pendekatanAtensi = _pendekatanAtensi.asStateFlow()

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