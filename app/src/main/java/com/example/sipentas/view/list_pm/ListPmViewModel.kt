package com.example.sipentas.view.list_pm

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.di.KabupatenModel
import com.example.sipentas.di.KategoriModel
import com.example.sipentas.di.ProvinsiModel
import com.example.sipentas.di.RagamModel
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.PmModel
import com.example.sipentas.repositories.PmRepository
import com.example.sipentas.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Exception

@HiltViewModel
class ListPmViewModel @Inject constructor(val repo: PmRepository,val prefs: SharePrefs) : ViewModel() {

    private val _uiState = MutableStateFlow<List<PmModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    fun deletePm(
        id: Int,
        loading: MutableState<Boolean>,
        onError: (String) -> Unit,
        onSucces: () -> Unit
    ) =
        viewModelScope.launch {
            loading.value = true
            try {
                repo.deletePm(id).let {
                    loading.value = false
                    onSucces.invoke()
                }
            } catch (e: Exception) {
                loading.value = false
                Log.e("GABISA HAPUS", e.toString())
                onError.invoke(e.toString())
            }
        }

    fun getPmData(search: String,loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                if (search.isEmpty()) {
                    repo.getAllPm().let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                } else {
                    repo.searchPm(search).let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }

    fun getPmDirektorat(search: String,loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                if (search.isEmpty()) {
                    repo.getPmByDirektorat().let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                } else {
                    repo.getSearchAllPmSearch(search).let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }

    fun getPm(search: String,loading: MutableState<Boolean>) =
        viewModelScope.launch {
            loading.value = true
            try {
                if (search.isEmpty()) {
                    repo.getPm().let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                } else {
                    repo.getSearchAllPm(search).let { item ->
                        _uiState.value = item
                        loading.value = false
                    }
                }
            } catch (e: Exception) {
                Log.e("EROR GET DATA", e.toString())
            }
        }


}