package com.example.sipentas.view.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sipentas.repositories.ProfileRepository
import com.example.sipentas.utils.SharePrefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repo:ProfileRepository,val prefs:SharePrefs):ViewModel() {
    fun logout(onSuccess:() -> Unit, onFailure:() -> Unit) =
        viewModelScope.launch {
            try {
                repo.logout().let {
                    prefs.saveToken("")
                    prefs.saveSatker("")
                    prefs.saveName("")
                    Log.e("LOGOUT BEHASIL:","YE")
                    onSuccess.invoke()
                }
            } catch (e:Exception) {
                Log.e("GABISA LOGOUT",e.toString())
                onFailure.invoke()
            }
        }

    fun getNama():String? = prefs.getName()
    fun getSatker():String? = prefs.getSatker()
}