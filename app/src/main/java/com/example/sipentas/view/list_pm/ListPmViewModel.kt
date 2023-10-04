package com.example.sipentas.view.list_pm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.models.PmModel
import com.example.sipentas.repositories.PmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ListPmViewModel @Inject constructor(val repo:PmRepository):ViewModel() {
    private val _uiState = MutableStateFlow<List<PmModel>>(emptyList())
    val uiState = _uiState.asStateFlow()

    fun getPmData() =
        viewModelScope.launch {
            try {
                repo.getAllPm().let {
                    item ->
                    _uiState.value = item
                }
            }  catch (e:Exception) {
                Log.e("EROR GET DATA",e.toString())
            }
        }

    init {
        getPmData()
    }
}