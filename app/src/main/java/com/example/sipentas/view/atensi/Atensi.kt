package com.example.sipentas.view.atensi

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import com.example.sipentas.component.ShimerItem
import com.example.sipentas.models.AtensItem
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.navigation.BotNavRoute
import com.example.sipentas.utils.ComposeDialog
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.view.detail_atensi.DetailAtensiViewModel
import com.example.sipentas.view.login.LoginViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Atensi(
    navController: NavController,
    loginVm: LoginViewModel,
    detailVm: DetailAtensiViewModel
) {

    val currentIndex = remember {
        mutableIntStateOf(0)
    }
    val context = LocalContext.current
    val uiState = detailVm.uiState.collectAsState().value
    val search = remember {
        mutableStateOf("")
    }
    val checkNotNull = remember {
        mutableStateOf(false)
    }
    val loadingData = remember {
        mutableStateOf(false)
    }
    if (!loginVm.prefs.getTipeSatker().isNullOrEmpty()) {
        checkNotNull.value = true
        LaunchedEffect(key3 = search.value, key2 = loginVm.prefs.getTipeSatker(), key1 = Unit, block = {
            when (loginVm.prefs.getTipeSatker()) {
                "3" -> {
                    if (search.value.isEmpty()) {
                        detailVm.getAtensi(loadingData)
                    } else {
                        detailVm.searchAtensi(search.value,loadingData)
                    }
                }
                "1" -> {
                    if (search.value.isEmpty()) {
                        detailVm.getAtensiAll(loadingData)
                    } else {
                        detailVm.searchAtensiAll(search.value,loadingData)
                    }
                }
                else -> {
                    if (search.value.isEmpty()) {
                        detailVm.getAtensi(loadingData)
                    } else {
                        detailVm.searchAtensi(search.value,loadingData)
                    }
                }
            }
        })
    }
    val refresh = remember {
        mutableStateOf(false)
    }

    val confirmDelete = remember {
        mutableStateOf(false)
    }

    val isLoading = remember {
        mutableStateOf(false)
    }
    LoadingDialog(boolean = isLoading)
    ComposeDialog(
        title = "Hapus Data",
        desc = " Apakah anda yakin menghapus data ini ?",
        boolean = confirmDelete
    ) {
        Log.d("GET IDNYA",currentIndex.intValue.toString())
        detailVm.deleteAtensi(currentIndex.intValue,isLoading, {
            Toast.makeText(context,"Tidak bisa dihapus, masih ada residensial  yang menggunakan id atensi ini", Toast.LENGTH_SHORT).show()
            confirmDelete.value = false
        }) {
            Toast.makeText(context,"Data Berhasil dihapus", Toast.LENGTH_SHORT).show()
            navController.navigate(BotNavRoute.Atensi.route) {
                popUpTo(0)
            }
            confirmDelete.value = false
        }
    }

    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = refresh.value), onRefresh = {
        navController.navigate(BotNavRoute.Atensi.route) {
            popUpTo(0)
        }
    }) {
        Scaffold {
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = Color(0xFF00A7C0)
            ) {
                Column {
                    HeaderList(search, "Atensi", loginVm,uiState.rows)
                    Spacer(modifier = Modifier.height(20.dp))
                    ListBody {
                        if (!uiState.rows.isNullOrEmpty()) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(16.dp),
                                content = {
                                    itemsIndexed(uiState.rows) { index, item ->
                                        val delete = SwipeAction(
                                            icon = painterResource(id = R.drawable.trash_icon),
                                            background = Color(0xFFEF3131),
                                            onSwipe = {
                                                currentIndex.intValue = item.id_atensi!!
                                                confirmDelete.value = true
                                            }
                                        )
                                        if (loginVm.prefs.getTipeSatker() == "3") {
                                            SwipeableActionsBox(
                                                endActions = listOf(delete),
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(6.dp))
                                            ) {
                                                ListAtensi(
                                                    navController,
                                                    item,
                                                    item.id_atensi,
                                                    item.id_pendekatan_atensi
                                                )
                                            }
                                        } else {
                                            ListAtensi(
                                                navController,
                                                item,
                                                item.id_atensi,
                                                item.id_pendekatan_atensi
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(14.dp))
                                    }
                                })
                        } else if (uiState.rows.isNullOrEmpty() && loadingData.value) {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(16.dp),
                                content = {
                                    items(20) {
                                        ShimerItem()
                                        Spacer(modifier = Modifier.height(14.dp))

                                    }
                                })
                        } else {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.Center)) {
                                Text(text = "Data Kosong",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color(0xFF8D8D8D),
                                    fontSize = 16.sp
                                )
                            }

                        }
                    }
                }
            }
        }
    }


}

@Composable
private fun ListAtensi(
    navController: NavController,
    item: AtensItem,
    id_atensi: Int?,
    id_pendekatan_atensi: Int?
) {
    Surface(
        color = Color(0xFFF8F8F8),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                try {
                    navController.navigate(
                        AppRoute.DetailAtensi.route
                                + "/${if (!item.lat.isNullOrEmpty()) item.lat else "0"}"
                                + "/${if (!item.long.isNullOrEmpty()) item.long else "0"}"
                                + "/${if (!item.id_pm.isNullOrEmpty()) item.id_pm else "0"}"
                                + "/${if (!item.id_assessment.isNullOrEmpty()) item.id_assessment else "0"}"
                                + "/${if (!item.nama_pm.isNullOrEmpty()) item.nama_pm else "0"}"
                                + "/${if (!item.nik_pm.isNullOrEmpty()) item.nik_pm else "0"}"
                                + "/${if (!item.petugas_assesmen.isNullOrEmpty()) item.petugas_assesmen else "0"}"
                                + "/${if (!item.tanggal_assesmen.isNullOrEmpty()) item.tanggal_assesmen else "0"}"
                                + "/${if (item.id_atensi != null) id_atensi else "0"}"
                                + "/${if (!item.nama_jenis_atensi.isNullOrEmpty()) item.nama_jenis_atensi else "0"}"
                                + "/${if (!item.id_jenis_atensi.isNullOrEmpty()) item.id_jenis_atensi else "0"}"
                                + "/${if (!item.jenis.isNullOrEmpty()) item.jenis else "0"}"
                                + "/${if (!item.nilai.isNullOrEmpty()) item.nilai else "0"}"
                                + "/${if (!item.tanggal.isNullOrEmpty()) item.tanggal else "0"}"
                                + "/${if (!item.nama_pendekatan_atensi.isNullOrEmpty()) item.nama_pendekatan_atensi else "0"}"
                                + "/${if (item.id_pendekatan_atensi != null) id_pendekatan_atensi else "0"}"
                                + "/${if (!item.penerima.isNullOrEmpty()) item.penerima else "0"}"
                                + "?url_atensi=${if (!item.foto.isNullOrEmpty()) item.foto else "0"}"
                    )
                } catch (e: Exception) {
                    Log.e("ERORR NAVIGASI ATENSI", e.toString())
                }
            }
    ) {
        Row(
            Modifier
                .padding(16.dp)
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
                    text = item.nama_jenis_atensi!!.toString(),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${if (item.tanggal_assesmen != null) item.tanggal_assesmen else " "}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
            }
            Text(
                text = "${if (item.petugas_assesmen.isNullOrEmpty()) "" else item.petugas_assesmen}",
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 10.sp,
                color = Color(0xFFC3C3C3)
            )
        }
    }
}