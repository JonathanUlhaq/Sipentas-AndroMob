package com.example.sipentas.utils

import android.widget.Toast
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.view.detail_atensi.DetailAtensiViewModel
import com.example.sipentas.view.form_pm.FormPmViewModel

@Composable
fun DropDownDummy(
    expand: MutableState<Boolean>,
    getString: (String) -> Unit
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DropdownMenuItem(
            text = { Text("Dummy") },
            onClick = {
                getString.invoke("Dummy")
                expand.value = false
            }
        )
        DropdownMenuItem(
            text = { Text("Daummy") },
            onClick = {
                getString.invoke("Daummy")
                expand.value = false
            }
        )
    }
}

class DropDownAtensi(val vm:DetailAtensiViewModel) {
    @Composable
    fun DropDownJenAtensi(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val uiState = vm.jenisAtensi.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.jenis}") },
                    onClick = {
                        getString.invoke("${string?.jenis}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownPendekatanAtensi(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val uiState = vm.pendekatanAtensi.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.pendekatan}") },
                    onClick = {
                        getString.invoke("${string?.pendekatan}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }
}
class DropdownCompose (val vm: FormPmViewModel,val asVm:AssesmenViewModel) {


    @Composable
    fun DropDownPpks(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = vm.kategori.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.kategori}") },
                    onClick = {
                        getString.invoke("${string?.kategori}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownRagam(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = vm.ragam.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string.ragam}") },
                    onClick = {
                        getString.invoke("${string.ragam}",string.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownJenisKelamin(
        expand: MutableState<Boolean>,
        getString: (String) -> Unit
    ) {
        val jenis_kelamin = listOf(
            "LAKI-LAKI",
            "PEREMPUAN"
        )
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            jenis_kelamin.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text(string) },
                    onClick = {
                        getString.invoke(string)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownAgama(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val agama = listOf(
            "Islam",
            "Kristen Katolik",
            "Kristen Protestan",
            "Budha",
            "Hindu",
            "Lainnya"
        )
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            agama.forEachIndexed {
                   index, string ->
                DropdownMenuItem(
                    text = { Text(string) },
                    onClick = {
                        getString.invoke(string,index+1)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownProvinsi(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val uiState = vm.provinsi.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string.nama}") },
                    onClick = {
                        getString.invoke("${string.nama}",string.id!!.toInt())
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownKabupaten(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val uiState = vm.kabupaten.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string.nama}") },
                    onClick = {
                        getString.invoke(string.nama!!,string.id!!.toInt())
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownKecamatan(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val uiState = vm.kecamatan.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string.nama}") },
                    onClick = {
                        getString.invoke(string.nama!!,string.id!!.toInt())
                        expand.value = false
                    }
                )
            }
        }
    }
    @Composable
    fun DropDownKelurahan(
        expand: MutableState<Boolean>,
        getString: (String,Long) -> Unit
    ) {
        val uiState = vm.kelurahan.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string.nama}") },
                    onClick = {
                        getString.invoke(string.nama!!,string.id!!.toLong())
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownPendidikan(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = asVm.pendidikan.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.pendidikan}") },
                    onClick = {
                        getString.invoke("${string?.pendidikan}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownSumberKasus(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = asVm.sumber.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.sumber}") },
                    onClick = {
                        getString.invoke("${string?.sumber}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownPekerjaan(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = asVm.pekerjaan.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.pekerjaan}") },
                    onClick = {
                        getString.invoke("${string?.pekerjaan}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownStatusOrtu(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = asVm.statusOrtu.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.status_ortu}") },
                    onClick = {
                        getString.invoke("${string?.status_ortu}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

    @Composable
    fun DropDownTempatTinggal(
        expand: MutableState<Boolean>,
        getString: (String,Int) -> Unit
    ) {
        val context = LocalContext.current
        val uiState = asVm.tempatTinggal.collectAsState().value
        DropdownMenu(
            expanded = expand.value,
            onDismissRequest = { expand.value = false }
        ) {
            uiState.forEach {
                    string ->
                DropdownMenuItem(
                    text = { Text("${string?.tempat_tinggal}") },
                    onClick = {
                        getString.invoke("${string?.tempat_tinggal}",string?.id!!)
                        expand.value = false
                    }
                )
            }
        }
    }

}