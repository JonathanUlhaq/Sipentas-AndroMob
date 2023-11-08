package com.example.sipentas.view.assessment

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.HeaderList
import com.example.sipentas.component.ListBody
import com.example.sipentas.models.AssesmentRow
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.navigation.BotNavRoute
import com.example.sipentas.utils.ComposeDialog
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.view.login.LoginViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssessmentView(
    navController: NavController,
    vm: AssesmenViewModel,
    loginVm:LoginViewModel
) {
    val search = remember {
        mutableStateOf("")
    }
    val checkNotNull = remember {
        mutableStateOf(false)
    }
    if (!loginVm.prefs.getTipeSatker().isNullOrEmpty()) {
        checkNotNull.value = true
        LaunchedEffect(key3 = search.value, key2 = checkNotNull.value, key1 = Unit, block = {
            when (loginVm.prefs.getTipeSatker()) {
                "3" -> {
                    if (search.value.isEmpty()) {
                        vm.getAssesmen()
                    } else {
                        vm.searchAssesment(search.value)
                    }
                }
                "1" -> {
                    if (search.value.isEmpty()) {
                        vm.getAssesmentAll()
                    } else {
                        vm.searchAllAssesment(search.value)
                    }
                }
                else -> {
                    if (search.value.isEmpty()) {
                        vm.getAssesmentAll()
                    } else {
                        vm.searchAllAssesment(search.value)
                    }
                }
            }
        })
    }
    LaunchedEffect(key1 = search.value, block = {

    } )
    val uiState = vm.uiState.collectAsState().value

    val context = LocalContext.current

    val loading = remember {
        mutableStateOf(false)
    }
    LoadingDialog(boolean = loading)

    val confirmDelete = remember {
        mutableStateOf(false)
    }

    val currentIndex = remember {
        mutableIntStateOf(0)
    }

    ComposeDialog(
        title = "Hapus Data",
        desc = " Apakah anda yakin menghapus data ini ?",
        boolean = confirmDelete
    ) {
        Log.d("GET IDNYA",currentIndex.value.toString())
        vm.deleteAssesment(currentIndex.value,loading, {
            Toast.makeText(context,"Tidak bisa dihapus, masih ada atensi yang menggunakan id assesmen ini",
                Toast.LENGTH_SHORT).show()
            confirmDelete.value = false
        }) {
            Toast.makeText(context,"Data Berhasil dihapus", Toast.LENGTH_SHORT).show()
            navController.navigate(BotNavRoute.Assessment.route) {
                popUpTo(0)
            }
            confirmDelete.value = false
        }
    }

    Scaffold {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color(0xFF00A7C0)
        ) {
            Column {
                HeaderList(search, "Assessment",loginVm,uiState.rows)
                Spacer(modifier = Modifier.height(20.dp))
                ListBody {
                    if (uiState.rows != null) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp),
                            content = {
                                itemsIndexed(uiState.rows) { index, item ->

                                    val changeColor by animateColorAsState(
                                        targetValue = if (item.flag == 0) Color(
                                            0xFFD0D34B
                                        )
                                        else if (item.flag == 1) Color(0xFF4BD379)
                                        else Color(0xFFD34B4B)
                                    )
                                    val changeIcon by animateIntAsState(
                                        targetValue = if (item.flag == 0)
                                            R.drawable.process_icon else if (item.flag == 1)
                                            R.drawable.check_icon
                                        else R.drawable.close_icon
                                    )
                                    val delete = SwipeAction(
                                        icon = painterResource(id = R.drawable.icon_delete),
                                        background = Color(0xFFEF3131),
                                        onSwipe = {
                                            currentIndex.intValue = item.id_asesmen!!.toInt()
                                            confirmDelete.value = true }
                                    )
                                    if (loginVm.prefs.getTipeSatker() == "3") {
                                        SwipeableActionsBox(
                                            endActions = listOf(delete),
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(6.dp))
                                        ) {
                                            ListAssesMain(
                                                item,
                                                navController,
                                                item.id_pm,
                                                item.id_pendidikan,
                                                item.id_pekerjaan,
                                                item.id_status_ortu,
                                                changeColor,
                                                changeIcon
                                            )
                                        }
                                    } else {
                                        ListAssesMain(
                                            item,
                                            navController,
                                            item.id_pm,
                                            item.id_pendidikan,
                                            item.id_pekerjaan,
                                            item.id_status_ortu,
                                            changeColor,
                                            changeIcon
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(14.dp))
                                }
                            })
                    }
                }
            }
        }
    }
}

@Composable
private fun ListAssesMain(
    item: AssesmentRow,
    navController: NavController,
    id_pm: Int?,
    id_pendidikan: Int?,
    id_pekerjaan: Int?,
    id_status_ortu: Int?,
    changeColor: Color,
    changeIcon: Int
) {
    Surface(
        color = Color(0xFFF8F8F8),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .clickable {
                try {
                    if (item.flag == 0) {
                        navController.navigate(AppRoute.FormAssessment.route + "/${item.id_pm}")
                    } else if (item.flag == 1) {
                        navController.navigate(
                            AppRoute.DetailAssessment.route
                                    + "/${if (item.long != null) item.long else "0"}"
                                    + "/${if (item.lat != null) item.lat else "0"}"
                                    + "/${if (item.id_pm != null) id_pm else "0"}"
                                    + "/${if (item.id_asesmen != null) item.id_asesmen else "0"}"
                                    + "/${if (item.nama_pendidikan != null) item.nama_pendidikan else "0"}"
                                    + "/${if (item.id_pendidikan != null) id_pendidikan else "0"}"
                                    + "/${item.nama_sumber_kasus ?: "0"}"
                                    + "/${item.id_sumber_kasus ?: "0"}"
                                    + "/${if (!item.nama_pekerjaan.isNullOrEmpty()) item.nama_pekerjaan else "0"}"
                                    + "/${if (item.id_pekerjaan != null) id_pekerjaan else "0"}"
                                    + "/${if (!item.tanggal.isNullOrEmpty()) item.tanggal else "0"}"
                                    + "/${if (!item.petugas.isNullOrEmpty()) item.petugas else "0"}"
                                    + "/${if (!item.status_dtks.isNullOrEmpty()) item.status_dtks else "0"}"
                                    + "/${if (!item.nama_status_ortu.isNullOrEmpty()) item.nama_status_ortu else "0"}"
                                    + "/${if (item.id_status_ortu != null) id_status_ortu else "0"}"
                                    + "/${if (!item.nama_pekerjaan.isNullOrEmpty()) item.nama_pekerjaan else "0"}"
                                    + "/${item.id_kerja_ortu ?: "0"}"
                                    + "/${if (!item.nama_tempat_tinggal.isNullOrEmpty()) item.nama_tempat_tinggal else "0"}"
                                    + "/${item.id_tempat_tinggal ?: "0"}"
                                    + "/${if (!item.nama_bpk.isNullOrEmpty()) item.nama_bpk else "0"}"
                                    + "/${if (!item.nama_ibu.isNullOrEmpty()) item.nama_ibu else "0"}"
                                    + "/${if (!item.nik_ibu.isNullOrEmpty()) item.nik_ibu else "0"}"
                                    + "/${if (!item.nama_wali.isNullOrEmpty()) item.nama_wali else "0"}"
                                    + "/${if (!item.penghasilan.isNullOrEmpty()) item.penghasilan else "0"}"
                                    + "/${if (!item.catatan.isNullOrEmpty()) item.catatan else "0"}"
                                    + "?urlRumah=${if (!item.foto_rumah.isNullOrEmpty()) item.foto_rumah else "0"}"
                                    + "?urlFisik=${if (!item.foto_kondisi_fisik.isNullOrEmpty()) item.foto_kondisi_fisik else "0"}"
                                    + "?urlKk=${if (!item.foto_kk.isNullOrEmpty()) item.foto_kk else "0"}"
                                    + "?urlKtp=${if (!item.foto_ktp.isNullOrEmpty()) item.foto_ktp else "0"}"
                                    + "?elap=${if (item.file_lap != null) item.file_lap else "0"}"

                        )
                    }
                } catch (e: Exception) {
                }
            }
    ) {
        Row(
            Modifier
                .padding(start = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = item.nama_pm!!,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF515151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = when (item.flag) {
                        0 -> "Belum Diproses"
                        1 -> "Sudah Diproses"
                        else -> "Closed"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (item.tanggal.isNullOrEmpty()) " " else item.tanggal,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
            }
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .size(width = 50.dp, height = 60.dp),
                color = changeColor
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(id = changeIcon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                }
            }
        }
    }
}