package com.example.sipentas.view.detail_atensi

import android.net.Uri
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.component.OutlineButtonPrimary
import com.example.sipentas.models.AtensiBody
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownAtensi
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.MapsView
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.form_assessment.PickPdfFile
import com.example.sipentas.widgets.DatePicker
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAtens(
    navController: NavController,
    vm:DetailAtensiViewModel,
    idPm:String,
    idAssesmen:String,
    penerimaNama:String,
    nik:String,
    petugas:String,
    tanggal:String,
    idAtensi:String,
    urlAtensi:String,
    jenAtensi:String,
    idJens:String,
    jenis:String,
    curNilai:String,
    tanggalAten:String,
    pendekatanAten:String,
    idPendekt:String,
    penerima:String,
    latCur:String,
    longCur:String

) {

    vm.getJenisAtensi()
    vm.getPendekatanAtensi()
//    vm.getDetailAtensi(idAtensi.toInt())
//    val detailState = vm.detailAtensi.collectAsState().value

    val atensi = listOf(
        "Perawatan Keluarga"
    )
    val member = listOf(
        "Keluarga"
    )


    val showPermission = remember {
        mutableStateOf(false)
    }
    val openCamera = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUri = remember {
        mutableStateOf(Uri.EMPTY)
    }

    val jenis = remember {
        mutableStateOf(if (jenis == "0") "" else jenis)
    }
    val idJenis = remember {
        mutableIntStateOf(idJens.toInt())
    }
    val nilai = remember {
        mutableStateOf(if (curNilai == "0") "" else curNilai)
    }
    val tanggalAtensi = remember {
        mutableStateOf(if (tanggalAten == "0") "" else tanggalAten)
    }
    val penerima = remember {
        mutableStateOf(if (penerima == "0") "" else penerima)
    }
    val addForm = remember {
        mutableStateOf(false)
    }

    val jenisAtens = remember {
        mutableStateOf(false)
    }
    val jenisAtensString = remember {
        mutableStateOf(if (jenAtensi == "0") "" else jenAtensi)
    }
    val pendekatanAtens = remember {
        mutableStateOf(false)
    }
    val pendekatanAtensString = remember {
        mutableStateOf(if (pendekatanAten == "0") "" else pendekatanAten)
    }
    val idPendekatan = remember {
        mutableIntStateOf(idPendekt.toInt())
    }
    val urlAtensi = remember {
        mutableStateOf(urlAtensi)
    }

    Log.d("URLNYA ADALAHH",urlAtensi.value)
    val context = LocalContext.current

    val location = LocationProviders(context)
    val dropDownAtensi = DropDownAtensi(vm)
    if (showPermission.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCamera
        )
    }
    val onLoadingAtensi = remember {
        mutableStateOf(false)
    }

    val lat = remember {
        mutableStateOf(latCur)
    }
    val long = remember {
        mutableStateOf(longCur)
    }
    val locationPermission = remember {
        mutableStateOf(false)
    }
    val isEdit = remember {
        mutableStateOf(false)
    }
    if (locationPermission.value) {
        location.LocationPermission(lat = lat, long = long).let {
            locationPermission.value =false
            location.getLastKnownLocation(success = {
                locationPermission.value =false
            }) {

            }

        }
    }
    LoadingDialog(boolean = onLoadingAtensi)
    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val currentLocationAction = remember {
        mutableStateOf(false)
    }

Box {

    Scaffold (
        floatingActionButton = {
            val iconState by animateIntAsState(targetValue = if (!currentLocationAction.value) R.drawable.current_location else R.drawable.close_icon)

            if (isEdit.value) {
                FloatingActionButton(onClick = { if (isEdit.value) {
                    when(currentLocationAction.value) {
                        false -> {
                            locationPermission.value =true
                            currentLocationAction.value = true
                        }
                        else -> {
                            locationPermission.value = false
                            currentLocationAction.value =false
                            lat.value = latCur
                            long.value = longCur
                        }
                    }
                }
                },
                    shape = CircleShape,
                    containerColor = Color(0xFF00A7C0)) {
                    Icon(
                        painter = painterResource(id = iconState),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                }
            }
        }
            ) {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
            color = Color.White
        ) {
            Column {
                Box {
                    Surface(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)
                    ) {
                            Column {
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
                                            tint = Color.White
                                        )
                                    }
                                    Text(
                                        text = "Detail Atensi",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
                                    )
                                    if (vm.pref.getTipeSatker()!! == "3") {

                                        Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Switch(
                                            checked = isEdit.value,
                                            onCheckedChange = { isEdit.value = it },
                                            colors = SwitchDefaults.colors(
                                                checkedBorderColor = Color.Transparent,
                                                checkedThumbColor = Color(0xFF00A7C0),
                                                checkedTrackColor = Color(0xFFFFFFFF),
                                                uncheckedBorderColor = Color.Transparent,
                                                uncheckedThumbColor = Color(0xFFFFFFFF).copy(0.6f),
                                                uncheckedTrackColor = Color(0xFF8f8f8f)
                                            ),
                                            modifier = Modifier
                                                .scale(0.7f)
                                        )
                                        Text(
                                            text = "Ubah",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontSize = 10.sp,
                                            color = Color(0xFFFFFFFF),
                                            modifier = Modifier
                                                .offset(y = -10.dp)
                                        )
                                    }
                                } else {
                                        Text(
                                            text = "Ubah",
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontSize = 10.sp,
                                            color = Color(0xFF00A7C0),
                                            modifier = Modifier
                                                .offset(y = -10.dp)
                                        )
                                    }
                            }
                        }

                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                            .offset(y = 76.dp)
                    ) {
                        Surface(
                            Modifier
                                .width(140.dp)
                                .height(70.dp),
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp),
                            shadowElevation = 12.dp,

                            ) {
                            Image(
                                painter = painterResource(id = R.drawable.sipentas_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
                            )
                        }
                    }
                }
                Column(
                    Modifier
                        .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        Column {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(CenterHorizontally)
                            ) {
                                Text(
                                    text = "$penerimaNama | ",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                                Text(
                                    text = "${if(nik == "0") "" else nik}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 12.sp,
                                    color = Color.Black
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth(CenterHorizontally)
                            ) {
                                Column {
                                    Text(
                                        text = "${if(tanggal == "0") "" else tanggal}",
                                        color = Color.Black,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth(CenterHorizontally)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "Petugas: ${if (petugas == "0") "" else petugas}",
                                        color = Color(0xFF8D8D8D),
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 10.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentWidth(CenterHorizontally)
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                    LazyColumn(content = {
//                        if (!detailState.data.isNullOrEmpty()) {
//                            itemsIndexed(detailState.data) {
//                                    index, item ->
//                                Column {
//                                    Divider()
//                                    Spacer(modifier = Modifier.height(12.dp))
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Surface(
//                                            shape = RoundedCornerShape(4.dp),
//                                            modifier = Modifier
//                                                .width(82.dp)
//                                                .height(45.dp)
//                                        ) {
//                                            if (item.foto.isNullOrEmpty() || item.foto == "0" || item.foto == "url") {
//                                                Image(painter = painterResource(id = R.drawable.default_photo),
//                                                    contentDescription = null,
//                                                    contentScale = ContentScale.Crop)
//                                            } else {
//                                                AsyncImage(model = item.foto,
//                                                    contentDescription = null,
//                                                    contentScale = ContentScale.Crop,
//                                                    modifier = Modifier
//                                                        .fillMaxSize())
//                                            }
//
//                                        }
//                                        Spacer(modifier = Modifier.width(12.dp))
//                                        Column {
//                                            Text(text = "${item.nama_pm}",
//                                                style = MaterialTheme.typography.titleMedium,
//                                                fontSize = 12.sp,
//                                                color = Color.Black)
//                                            Spacer(modifier = Modifier.height(2.dp))
//                                            Text(text = "${item.nama_jenis_atensi}",
//                                                style = MaterialTheme.typography.bodyMedium,
//                                                fontSize = 10.sp,
//                                                color = Color(0xFFC3C3C3)
//                                            )
//                                        }
//                                    }
//                                    Spacer(modifier = Modifier.height(12.dp))
//                                    Divider()
//                                    Spacer(modifier = Modifier.height(12.dp))
//                                }
//                            }
//                        }
                        item {
                            Column {
                                AnimatedVisibility(visible = !lat.value.isNullOrEmpty() && !long.value.isNullOrEmpty() && long.value != "null" && lat.value != "null") {
                                    MapsView(lat.value.toDouble(), long.value.toDouble())
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Surface(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    color = Color(0xFFEB9B4B),
                                    shape = RoundedCornerShape(16.dp),
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                if (isEdit.value) {
                                                    showPermission.value = true
                                                }
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
                                        } else if (urlAtensi.value.isNullOrEmpty() || urlAtensi.value == "0" || urlAtensi.value == "url") {
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
                                                    text = "Foto Atensi",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = Color.White
                                                )
                                            }
                                        } else {
                                            AsyncImage(model = urlAtensi.value,
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop,
                                                modifier = Modifier
                                                    .fillMaxSize())
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                DropdownField(kategoriPpks = jenisAtens ,
                                    label = "Jenis Atensi" ,
                                    stringText = jenisAtensString.value,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                isEnable = isEdit.value) {
                                    dropDownAtensi.DropDownJenAtensi(expand = jenisAtens,
                                        getString = { item, index ->
                                            jenisAtensString.value = item
                                            idJenis.intValue = index
                                        } )
                                }
                                AnimatedVisibility(visible = jenisAtensString.value.isEmpty() ) {
                                    Column {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "* Jenis Atensi wajib diisi",
                                            fontSize = 10.sp,
                                            color = Color.Red
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                FilledTextField(
                                    textString = jenis,
                                    label = "Jenis",
                                    imeAction = ImeAction.Default,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = isEdit.value
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                FilledTextField(
                                    textString = nilai,
                                    label = "Nilai",
                                    imeAction = ImeAction.Default,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    keyboardType = KeyboardType.Number,
                                    enabled = isEdit.value
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                DatePicker(context = context, date = tanggalAtensi, label = "Tanggal Atensi", boolean = isEdit.value)
                                AnimatedVisibility(visible = tanggalAtensi.value.isEmpty()) {
                                    Column {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "* Tanggal Atensi wajib diisi",
                                            fontSize = 10.sp,
                                            color = Color.Red
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                DropdownField(kategoriPpks = pendekatanAtens ,
                                    label = "Pendekatan Atensi" ,
                                    stringText = pendekatanAtensString.value,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                isEnable = isEdit.value) {
                                    dropDownAtensi.DropDownPendekatanAtensi(expand = pendekatanAtens,
                                        getString = { item, index ->
                                            pendekatanAtensString.value = item
                                            idPendekatan.intValue = index
                                        } )
                                }
                                AnimatedVisibility(visible = pendekatanAtensString.value.isEmpty() ) {
                                    Column {
                                        Spacer(modifier = Modifier.height(6.dp))
                                        Text(
                                            text = "* Pendekatan Atensi wajib diisi",
                                            fontSize = 10.sp,
                                            color = Color.Red
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))
                                FilledTextField(
                                    textString = penerima,
                                    label = "Penerima",
                                    imeAction = ImeAction.Default,
                                    singleLine = true,
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    enabled = isEdit.value
                                )
                                AnimatedVisibility(visible = isEdit.value) {
                                    Column {
                                        Spacer(modifier = Modifier.height(32.dp))
                                        ButtonPrimary(text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "Ubah Atensi",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    modifier = Modifier
                                                        .padding(top = 6.dp, bottom = 6.dp),
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }) {
                                       vm.updateAtensi(idAtensi.toInt(), body =  AtensiBody(
                                           foto = if (urlAtensi.value == "0" || urlAtensi.value == "url") null else urlAtensi.value,
                                           id_assesment = idAssesmen.toInt(),
                                           id_jenis =idJenis.intValue,
                                           id_pendekatan = idPendekatan.intValue,
                                           id_pm = idPm.toInt(),
                                           jenis = jenis.value,
                                           lat = lat.value,
                                           long = long.value,
                                           nilai = if (nilai.value.isEmpty()) "0".toLong() else nilai.value.toLong(),
                                           penerima = penerima.value,
                                           tanggal = tanggalAtensi.value,
                                       ), onLoadingAtensi = onLoadingAtensi) {
                                           Toast.makeText(context,"Atensi berhasil diubah",Toast.LENGTH_SHORT).show()
                                           navController.popBackStack()
                                       }
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(14.dp))

                            }
//                            AnimatedVisibility(visible = !addForm.value) {
//                                ButtonPrimary(text = {
//                                    Row(
//                                        verticalAlignment = Alignment.CenterVertically
//                                    ) {
//                                        Text(
//                                            text = "Tambah Atensi",
//                                            style = MaterialTheme.typography.titleMedium,
//                                            modifier = Modifier
//                                                .padding(top = 6.dp, bottom = 6.dp),
//                                            fontSize = 14.sp
//                                        )
//                                    }
//                                }) {
//                                    addForm.value = true
//                                }
//                            }
                        }
                    })

                }
            }

        }
    }

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
                runBlocking {
                    val file = File(capturedImagebyUri.value?.path!!)
                    val compressor = Compressor.compress(context, file) {
                        default()
                        destination(file)
                    }
                    val requestBody = compressor.asRequestBody("image/*".toMediaType())
                    val gambar = MultipartBody.Part.createFormData(
                        "file",
                        compressor.name,
                        requestBody
                    )
                    vm.postPhoto(gambar, onError = {}) {
                        urlAtensi.value = it.file_url!!
                    }
                }
                cameraExecutor.shutdown()
            },
            onError = {

            }
        )
    }
}
}