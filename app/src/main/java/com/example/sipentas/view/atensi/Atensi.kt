package com.example.sipentas.view.atensi

import android.util.Log
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.HeaderList
import com.example.sipentas.component.ListBody
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.view.detail_atensi.DetailAtensiViewModel
import com.example.sipentas.view.login.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Atensi(
    navController: NavController,
    loginVm: LoginViewModel,
    detailVm: DetailAtensiViewModel
) {

    detailVm.getAtensi()
    val uiState = detailVm.uiState.collectAsState().value
    val search = remember {
        mutableStateOf("")
    }

    val nama = listOf(
        "Arya",
        "Anya",
        "Abraham"
    )

    val id = listOf(
        "7628362819273623",
        "7628362819273623",
        "7628362819273623"
    )

    val tanggal = listOf(
        "26 September 2023",
        "26 September 2023",
        "26 September 2023"
    )

    val petugas = listOf(
        "Indrajaya",
        "Indrajaya",
        "Indrajaya"
    )

    Scaffold {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color(0xFF00A7C0)
        ) {
            Column {
                HeaderList(search, "Atensi", loginVm)
                Spacer(modifier = Modifier.height(20.dp))
                ListBody {
                    if (uiState.rows != null) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp),
                            content = {
                                itemsIndexed(uiState.rows) { index, item ->
                                    Surface(
                                        color = Color(0xFFF8F8F8),
                                        shape = RoundedCornerShape(6.dp),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .clickable {
                                               try {
                                                   navController.navigate(
                                                       AppRoute.DetailAtensi.route
                                                               + "/${if (!item.id_pm.isNullOrEmpty()) item.id_pm else "0"}"
                                                               + "/${if (!item.id_assessment.isNullOrEmpty()) item.id_assessment else "0"}"
                                                               + "/${if (!item.nama_pm.isNullOrEmpty()) item.nama_pm else "null"}"
                                                               + "/${if (!item.nik_pm.isNullOrEmpty()) item.nik_pm else "0"}"
                                                               + "/${if (!item.petugas_assesmen.isNullOrEmpty()) item.petugas_assesmen else "null"}"
                                                               + "/${if (!item.tanggal_assesmen.isNullOrEmpty()) item.tanggal_assesmen else "null"}"

                                                   )
                                               } catch (e:Exception) {
                                                   Log.e("ERORR NAVIGASI ATENSI",e.toString())
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
                                                    text = item.id_atensi!!.toString(),
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
                                                text = "${item.petugas_assesmen}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontSize = 10.sp,
                                                color = Color(0xFFC3C3C3)
                                            )
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