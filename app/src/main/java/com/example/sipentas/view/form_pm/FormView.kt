package com.example.sipentas.view.form_pm

import android.net.Uri
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormView(
    navController: NavController
) {

    val nama = remember {
        mutableStateOf("")
    }
    val kategoriPpks = remember {
        mutableStateOf(false)
    }
    val ragam = remember {
        mutableStateOf(false)
    }
    val agama = remember {
        mutableStateOf(false)
    }
    val kelamin = remember {
        mutableStateOf(false)
    }
    val provinsi = remember {
        mutableStateOf(false)
    }
    val kabupaten = remember {
        mutableStateOf(false)
    }
    val showPermission = remember {
        mutableStateOf(false)
    }
    val openCamera = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUri = remember {
        mutableStateOf(Uri.EMPTY)
    }
    val kategoriPpksString = remember {
        mutableStateOf("")
    }
    val kelaminString = remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    if (showPermission.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCamera
        )
    }
    val locationPermission = remember {
        mutableStateOf(false)
    }
    val lat = remember {
        mutableStateOf("")
    }
    val long = remember {
        mutableStateOf("")
    }
    val ragamString = remember {
        mutableStateOf("")
    }
    val agamaString = remember {
        mutableStateOf("")
    }
    val provinsiString = remember {
        mutableStateOf("")
    }
    val kabupatenString = remember {
        mutableStateOf("")
    }

    val location = LocationProviders(context)
    if (locationPermission.value) {
        location.LocationPermission(lat = lat, long = long)
    }


    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val scrollState = rememberScrollState()


    if (openCamera.value) {
        CameraView(
            outputDirectory = output,
            executor = cameraExecutor,
            closeCamera = {
                showPermission.value = false
                openCamera.value = false
                cameraExecutor.shutdown()
            },
            onImageCapture = { uri ->
                capturedImagebyUri.value = uri
                showPermission.value = false
                openCamera.value = false
                cameraExecutor.shutdown()
            },
            onError = {

            }
        )
    } else {
        Scaffold (
            topBar = {
                Row(
                    Modifier
                        .padding(top = 18.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Form Penerima Manfaat",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Form",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                }
            }
                ){
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    Modifier
                        .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(scrollState)
                ) {
                    Surface(
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    showPermission.value = true
                                }
                                .wrapContentSize(Alignment.Center)

                        ) {
                            if (capturedImagebyUri.value.path?.isNotEmpty() == true) {
                                Image(
                                    painter = rememberImagePainter(capturedImagebyUri.value),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                            } else {
                                Column(
                                    Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.camera_icon),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(28.dp)
                                    )
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Text(
                                        text = "Bukti Foto",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
                                    )
                                }
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DropdownField(kategoriPpks,
                        modifier = Modifier.fillMaxWidth(),
                        "Kategori PPKS",
                        kategoriPpksString.value) {
                        DropDownDummy(expand = kategoriPpks ) {
                            kategoriPpksString.value = it
                        }

                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    DropdownField(ragam, modifier = Modifier.fillMaxWidth(),"Pilih Ragam",ragamString.value) {
                        DropDownDummy(expand = ragam ) {
                            ragamString.value = it
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = nama,
                        label = "nama penerima manfaat",
                        minHeight = 100,
                        imeAction = ImeAction.Default,
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(Modifier
                     .fillMaxWidth()) {
                     DropdownField(kelamin, modifier = Modifier.fillMaxWidth(0.5f),"Jenis Kelamin",kelaminString.value) {
                         DropDownDummy(expand = kelamin ) {
                             kelaminString.value = it
                         }
                     }
                     Spacer(modifier = Modifier.width(4.dp))
                     DropdownField(agama, modifier = Modifier.fillMaxWidth(),"Agama",agamaString.value){
                         DropDownDummy(expand = agama ) {
                             agamaString.value = it
                         }
                     }
                 }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(provinsi, modifier = Modifier.fillMaxWidth(),"Provinsi",provinsiString.value) {
                        DropDownDummy(expand = provinsi ) {
                            provinsiString.value = it
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(kabupaten, modifier = Modifier.fillMaxWidth(),"Kabupaten",kabupatenString.value) {
                        DropDownDummy(expand = kabupaten ) {
                            kabupatenString.value = it
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    ButtonPrimary(text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Kirim Form",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp),
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.send_icon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(14.dp)
                            )
                        }
                    }) {
                        locationPermission.value = true
                    }
                }
            }
        }
    }


}

