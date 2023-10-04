package com.example.sipentas.view.list_pm

import android.view.RoundedCorner
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.component.HeaderList
import com.example.sipentas.component.ListBody
import com.example.sipentas.models.PmModel
import com.example.sipentas.navigation.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPmView(
    vm: ListPmViewModel,
    navController: NavController
) {

    val uiState = vm.uiState.collectAsState().value

    val search = remember {
        mutableStateOf("")
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate(AppRoute.Form.route)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                shape = CircleShape
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
            color = MaterialTheme.colorScheme.primary
        ) {
            Column {
                HeaderList(search,"Penerima Manfaat")
                Spacer(modifier = Modifier.height(20.dp))
                ListBody {
                    if (uiState.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp),
                            content = {
                                items(uiState) { item ->
                                    Surface(
                                        color = Color(0xFFF8F8F8),
                                        shape = RoundedCornerShape(6.dp)
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
                                                Image(
                                                    painter = painterResource(id = R.drawable.gambar_person),
                                                    contentDescription = null,
                                                    modifier = Modifier
                                                )
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
                                    Spacer(modifier = Modifier.height(14.dp))
                                }
                            })
                    }
                }
            }
        }
    }
}
