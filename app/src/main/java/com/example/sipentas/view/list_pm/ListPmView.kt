package com.example.sipentas.view.list_pm

import android.util.Log
import android.view.RoundedCorner
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sipentas.R
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.component.HeaderList
import com.example.sipentas.component.ListBody
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.PmModel
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.navigation.BotNavRoute
import com.example.sipentas.utils.ComposeDialog
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.view.login.LoginViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPmView(
    vm: ListPmViewModel,
    navController: NavController,
    loginVm:LoginViewModel
) {

    val uiState = vm.uiState.collectAsState().value

    val search = remember {
        mutableStateOf("")
    }
   val getData = LaunchedEffect(key1 = search.value, block = {
        vm.getPmData(search.value)
    })
    val confirmDelete = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val loading = remember {
        mutableStateOf(false)
    }

    LoadingDialog(boolean = loading)
    val currentIndex = remember {
        mutableIntStateOf(0)
    }

    ComposeDialog(
        title = "Hapus Data",
        desc = " Apakah anda yakin menghapus data ini ?",
        boolean = confirmDelete
    ) {
        Log.d("GET IDNYA",currentIndex.value.toString())
        vm.deletePm(currentIndex.value,loading, {
            Toast.makeText(context,"Tidak bisa dihapus, masih ada asesmen yang menggunakan id pm ini",Toast.LENGTH_SHORT).show()
            confirmDelete.value = false
        }) {
            Toast.makeText(context,"Data Berhasil dihapus",Toast.LENGTH_SHORT).show()
            navController.navigate(BotNavRoute.PenerimaManfaat.route) {
                popUpTo(0)
            }
            confirmDelete.value = false
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(AppRoute.Form.route)
                },
                containerColor = Color(0xFF00A7C0),
                shape = CircleShape,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(14.dp)
                )
            }
        }
    ) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color(0xFF00A7C0)
        ) {
            Column {
                HeaderList(search, "Penerima Manfaat",loginVm,uiState)
                Spacer(modifier = Modifier.height(20.dp))
                ListBody {
                    if (uiState.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp),
                            content = {
                                items(uiState) { item ->
                                    val delete = SwipeAction(
                                        icon = painterResource(id = R.drawable.icon_delete),
                                        background = Color(0xFFEF3131),
                                        onSwipe = {
                                            currentIndex.intValue = item.id!!.toInt()
                                            confirmDelete.value = true }
                                    )
                                    SwipeableActionsBox(
                                        endActions = listOf(delete),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                    ) {
                                        ListPmItem(navController, item) {

                                        }
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
private fun ListPmItem(
    navController: NavController,
    item: PmModel,
    onId:@Composable (Int) -> Unit
) {
    onId.invoke(item.id!!.toInt())
    Surface(
        color = Color(0xFFF8F8F8),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable {
                try {
                    navController.navigate(
                        AppRoute.DetailPm.route
                                + "/${item.nama_ragam?.replace('/', '-')}"
                                + "/${if (!item.id_kelurahan.isNullOrEmpty()) item.id_kelurahan else "0"}"
                                + "/${item.id}"
                                + "/${if (!item.id_agama.isNullOrEmpty()) item.id_agama else "0"}"
                                + "/${item.id_ragam}"
                                + "/${item.name}"
                                + "/${item.gender}"
                                + "/${item.agama}"
                                + "/${item.nama_provinsi}"
                                + "/${item.nama_kabupaten}"
                                + "/${item.nama_kluster}"
                                + "/${item.id_kluster}"
                                + "/${item.id_provinsi}"
                                + "/${if (!item.ket_ppks.isNullOrEmpty()) item.ket_ppks else "0"}"
                                + "/${if (!item.place_of_birth.isNullOrEmpty()) item.place_of_birth else "0"}"
                                + "/${if (!item.date_of_birth.isNullOrEmpty()) item.date_of_birth else "0"}"
                                + "/${if (!item.phone_number.isNullOrEmpty()) item.phone_number else "0"}"
                                + "/${if (!item.nik.isNullOrEmpty()) item.nik else "0"}"
                                + "/${if (!item.nama_kelurahan.isNullOrEmpty()) item.nama_kelurahan else "0"}"
                                + "/${if (!item.id_kecamatan.isNullOrEmpty()) item.id_kecamatan else "0"}"
                                + "/${if (!item.nama_kecamatan.isNullOrEmpty()) item.nama_kecamatan else "0"}"
                                + "/${if (!item.id_kabupaten.isNullOrEmpty()) item.id_kabupaten else "0"}"
                                + "/${if (!item.nama_jalan.isNullOrEmpty()) item.nama_jalan else "0"}"
                                + "?foto_diri=${if (!item.foto_diri.isNullOrEmpty()) item.foto_diri else "0"}"
                    )
                } catch (e: Exception) {
                    Log.d("GABISA PINDAH", e.toString())
                }
            }
    ) {
        Row(
            Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .size(width = 80.dp, height = 44.dp)
            ) {
               if (item.foto_diri.isNullOrEmpty() || item.foto_diri == "0" || item.foto_diri == "url") {
                   Image(
                       painter = painterResource(id = R.drawable.default_photo),
                       contentDescription = null,
                       modifier = Modifier,
                       contentScale = ContentScale.Crop
                   )
               } else {
                   AsyncImage(model = item.foto_diri,
                       contentDescription = null,
                    contentScale = ContentScale.Crop)
               }

            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = item.name!!,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF515151)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.nama_ragam!!,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.nama_provinsi!! + " / " + item.nama_provinsi,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
            }
        }
    }
}

