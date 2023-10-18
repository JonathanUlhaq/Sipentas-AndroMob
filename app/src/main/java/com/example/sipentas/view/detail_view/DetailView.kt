package com.example.sipentas.view.detail_view

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.widgets.DatePicker
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailView(
    navController: NavController,
    vm: FormPmViewModel,
    currentRagam: String,
    currentName: String,
    currentKelamin: String,
    currentAgama: String,
    currentProvinsi: String,
    currentKabupaten: String,
    currentKluster: String,
    currentIdKluster: String,
    currentIdProvinsi: String,
    ketPpks: String,
    currentNik: String,
    currentTempatLahir: String,
    currentTanggalLahir: String,
    currentNomorHandphone: String,
    currentKecamatan: String,
    currentKelurahan: String,
    currentJalan: String,
    currentKabupId:String,
    currentKecId:String,
    fotoDiri:String
) {

    vm.getProvinsi()
    vm.getKategori()
    val nama = remember {
        mutableStateOf(currentName)
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

    val ragamString = remember {
        mutableStateOf(currentRagam)
    }

    val agamaString = remember {
        mutableStateOf(currentAgama)
    }

    val provinsiString = remember {
        mutableStateOf(currentProvinsi)
    }
    val kabupatenString = remember {
        mutableStateOf(currentKabupaten)
    }
    val kategoriPpksString = remember {
        mutableStateOf(currentKluster)
    }
    val kelaminString = remember {
        mutableStateOf(currentKelamin)
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
    val isEdit = remember {
        mutableStateOf(false)
    }
    val location = LocationProviders(context)
    if (locationPermission.value) {
        location.LocationPermission(lat = lat, long = long)
    }
    val keteranganPPks = remember {
        mutableStateOf(ketPpks)
    }
    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val scrollState = rememberScrollState()

    val dropCompose = DropdownCompose(vm)

    val kategoriPpksInt = remember {
        mutableIntStateOf(currentIdKluster.toInt())
    }
    val provinsiInt = remember {
        mutableIntStateOf(currentIdProvinsi.toInt())
    }
    vm.getRagam(kategoriPpksInt.intValue)
    vm.getKabupaten(provinsiInt.intValue)

    val formWajib = remember {
        mutableStateOf(false)
    }


    val nik = remember {
        mutableStateOf(currentNik)
    }
    val tempatLahir = remember {
        mutableStateOf(currentTempatLahir)
    }
    val tanggalLahir = remember {
        mutableStateOf(currentTanggalLahir)
    }
    val nomorHandphone = remember {
        mutableStateOf(currentNomorHandphone)
    }
    val kecamatanString = remember {
        mutableStateOf(currentKecamatan)
    }
    val kelurahanString = remember {
        mutableStateOf(currentKelurahan)
    }
    val namaJalan = remember {
        mutableStateOf(currentJalan)
    }
    val kabupatenId = remember {
        mutableIntStateOf(currentKabupId.toInt())
    }
    vm.getKecamatan(kabupatenId.value)
    val kecamatan = remember {
        mutableStateOf(false)
    }
    val kecamatanId = remember {
        mutableIntStateOf(currentKecId.toInt())
    }
    vm.getKelurahan(kecamatanId.intValue)
    val kelurahan = remember {
        mutableStateOf(false)
    }
    val kelurahanId = remember {
        mutableLongStateOf(0)
    }

    vm.getKelurahan(kecamatanId.intValue)
    formWajib.value = kategoriPpksString.value.isEmpty() || ragamString.value.isEmpty()
            || nama.value.isEmpty()
            || kelaminString.value.isEmpty()
            || agamaString.value.isEmpty()
            || provinsiString.value.isEmpty()
            || kabupatenString.value.isEmpty()


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
        Scaffold(
            topBar = {
                Row(
                    Modifier
                        .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
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
                        text = "Detail Penerima Manfaat",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Switch(
                            checked = isEdit.value,
                            onCheckedChange = { isEdit.value = it },
                            colors = SwitchDefaults.colors(
                                checkedBorderColor = Color.Transparent,
                                checkedThumbColor = MaterialTheme.colorScheme.background,
                                checkedTrackColor = MaterialTheme.colorScheme.primary,
                                uncheckedBorderColor = Color.Transparent,
                                uncheckedThumbColor = MaterialTheme.colorScheme.background.copy(0.6f),
                                uncheckedTrackColor = MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier
                                .scale(0.7f)
                        )
                        Text(
                            text = "Ubah",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .offset(y = -10.dp)
                        )
                    }
                }
            }
        ) {
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
                            } else if (!fotoDiri.isEmpty() || fotoDiri != "0") {
                                AsyncImage(model = fotoDiri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize())
                            }
                            else {
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

                    DropdownField(
                        kategoriPpks,
                        modifier = Modifier.fillMaxWidth(),
                        "Kategori PPKS *",
                        kategoriPpksString.value,
                        isEnable = isEdit.value
                    ) {
                        AnimatedVisibility(visible = isEdit.value) {
                            dropCompose.DropDownPpks(expand = kategoriPpks) { string, id ->
                                kategoriPpksString.value = string
                                ragamString.value = ""
                                kategoriPpksInt.intValue = id
                            }
                        }
                    }
                    AnimatedVisibility(visible = kategoriPpksString.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Kategori PPKS wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    DropdownField(
                        ragam, modifier = Modifier.fillMaxWidth(),
                        "Pilih Ragam *", ragamString.value,
                        isEnable = isEdit.value && kategoriPpksString.value.isNotEmpty()
                    ) {
                        AnimatedVisibility(visible = isEdit.value) {
                            dropCompose.DropDownRagam(expand = ragam) { string, index ->
                                ragamString.value = string
                            }
                        }
                    }
                    AnimatedVisibility(visible = ragamString.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Ragam wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = keteranganPPks,
                        label = "Keterangan PPKS",
                        imeAction = ImeAction.Default,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                    )


                    Spacer(modifier = Modifier.height(18.dp))

                    FilledTextField(
                        textString = nama,
                        label = "Nama PM *",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        enabled = isEdit.value,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    AnimatedVisibility(visible = nama.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Nama PM wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = nik,
                        label = "NIK",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = tempatLahir,
                        label = "Tempat Lahir",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    DatePicker(context = context, date = tanggalLahir)

                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {

                        Column {

                            DropdownField(
                                kelamin, modifier = Modifier.fillMaxWidth(0.5f),
                                "Jenis Kelamin", kelaminString.value,
                                isEnable = isEdit.value
                            ) {
                                AnimatedVisibility(visible = isEdit.value) {
                                    dropCompose.DropDownJenisKelamin(expand = kelamin) {
                                        kelaminString.value = it
                                    }
                                }
                            }
                            AnimatedVisibility(visible = kelaminString.value.isEmpty()) {
                                Column {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "* Jenis Kelamin wajib diisi",
                                        fontSize = 10.sp,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        Column {
                            DropdownField(
                                agama, modifier = Modifier.fillMaxWidth(), "Agama",
                                agamaString.value,
                                isEnable = isEdit.value
                            ) {
                                AnimatedVisibility(visible = isEdit.value) {
                                    dropCompose.DropDownAgama(expand = agama) { string, index ->
                                        agamaString.value = string
                                    }
                                }
                            }
                            AnimatedVisibility(visible = agamaString.value.isEmpty()) {
                                Column {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = "* Agama wajib diisi",
                                        fontSize = 10.sp,
                                        color = Color.Red
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = nomorHandphone,
                        label = "Nomor Handphone",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    DropdownField(
                        provinsi, modifier = Modifier.fillMaxWidth(), "Provinsi",
                        provinsiString.value,
                        isEnable = isEdit.value
                    ) {
                        AnimatedVisibility(visible = isEdit.value) {
                            dropCompose.DropDownProvinsi(expand = provinsi) { string, int ->
                                provinsiString.value = string
                                kabupatenString.value = ""
                                provinsiInt.intValue = int
                            }
                        }
                    }
                    AnimatedVisibility(visible = provinsiString.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Provinsi wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(
                        kabupaten,
                        modifier = Modifier.fillMaxWidth(),
                        "Kabupaten *",
                        kabupatenString.value,
                        isEnable = provinsiString.value.isNotEmpty()
                    ) {
                        dropCompose.DropDownKabupaten(expand = kabupaten) { string, id ->
                            kabupatenString.value = string
                            kabupatenId.intValue = id
                        }
                    }
                    AnimatedVisibility(visible = kabupatenString.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Kabupaten wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(
                        kecamatan,
                        modifier = Modifier.fillMaxWidth(),
                        "Kecamatan",
                        kecamatanString.value,
                        isEnable = kabupatenString.value.isNotEmpty()
                    ) {
                        dropCompose.DropDownKecamatan(expand = kecamatan) { string, id ->
                            kecamatanString.value = string
                            kecamatanId.intValue = id
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(
                        kelurahan,
                        modifier = Modifier.fillMaxWidth(),
                        "Kelurahan",
                        kelurahanString.value,
                        isEnable = kecamatanString.value.isNotEmpty()
                    ) {
                        dropCompose.DropDownKelurahan(expand = kelurahan) { string, id ->
                            kelurahanString.value = string
                            kelurahanId.longValue = id
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = namaJalan,
                        label = "Nama Jalan / Alamat Lengkap",
                        imeAction = ImeAction.Default,
                        singleLine = false
                    )
                    AnimatedVisibility(visible = isEdit.value) {
                        Column {
                            Spacer(modifier = Modifier.height(20.dp))
                            ButtonPrimary(text = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Ubah Data",
                                        style = MaterialTheme.typography.titleMedium,
                                        modifier = Modifier
                                            .padding(top = 6.dp, bottom = 6.dp),
                                        fontSize = 14.sp
                                    )

                                }
                            }) {

                            }
                        }
                    }

                }
            }
        }
    }


}