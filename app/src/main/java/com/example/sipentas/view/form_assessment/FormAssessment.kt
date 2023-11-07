package com.example.sipentas.view.form_assessment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
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
import com.example.sipentas.component.NikFilledText
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.MapsView
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.widgets.DatePicker
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.DecimalFormat
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAssessment(
    navController: NavController,
    vm:FormPmViewModel,
    asVm:AssesmenViewModel,
    idUser:String
) {
    asVm.getPendidikan()
    asVm.getSumber()
    asVm.getPekerjaan()
    asVm.getStatusOrtu()
    asVm.getTempatTinggal()
    val scrollState = rememberScrollState()
    val pendidikan = remember {
        mutableStateOf(false)
    }
    val pendidikanString = remember {
        mutableStateOf("")
    }

    val sumber = remember {
        mutableStateOf(false)
    }
    val sumberString = remember {
        mutableStateOf("")
    }

    val pekerjaan = remember {
        mutableStateOf(false)
    }
    val pekerjaanString = remember {
        mutableStateOf("")
    }

    val lembaga = remember {
        mutableStateOf(false)
    }
    val lembagaString = remember {
        mutableStateOf("")
    }

    val statusOrtu = remember {
        mutableStateOf(false)
    }
    val statusOrtuString = remember {
        mutableStateOf("")
    }

    val pekerjaanOrtu = remember {
        mutableStateOf(false)
    }
    val pekerjaanOrtuString = remember {
        mutableStateOf("")
    }


    val tempatTinggal = remember {
        mutableStateOf(false)
    }
    val tempatTinggalString = remember {
        mutableStateOf("")
    }

//    State Camera Rumah
    val showPermissionRumah = remember {
        mutableStateOf(false)
    }
    val openCameraRumah = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUriRumah = remember {
        mutableStateOf(Uri.EMPTY)
    }
    val context = LocalContext.current
    if (showPermissionRumah.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraRumah
        )
    }

//    State Camera Kondisi Fisik
    val showPermissionFisik = remember {
        mutableStateOf(false)
    }
    val openCameraFisik = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUriFisik = remember {
        mutableStateOf(Uri.EMPTY)
    }
    if (showPermissionFisik.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraFisik
        )
    }

//    State Camera KK
    val showPermissionKk = remember {
        mutableStateOf(false)
    }
    val openCameraKk = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUriKk = remember {
        mutableStateOf(Uri.EMPTY)
    }
    if (showPermissionKk.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraKk
        )
    }
    val outputRumah: File = getOutputDirectory(context)
    val cameraExecutorRumah = Executors.newSingleThreadExecutor()
    val outputFisik: File = getOutputDirectory(context)
    val cameraExecutorFisik = Executors.newSingleThreadExecutor()
    val outputKk: File = getOutputDirectory(context)
    val cameraExecutorKk = Executors.newSingleThreadExecutor()
    val outputKtp: File = getOutputDirectory(context)
    val cameraExecutorKtp = Executors.newSingleThreadExecutor()

    //    State Camera KTP
    val showPermissionKtp = remember {
        mutableStateOf(false)
    }
    val openCameraKtp = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUriKtp = remember {
        mutableStateOf(Uri.EMPTY)
    }
    val pdfUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val tanggalLahir = remember {
        mutableStateOf("")
    }
    val petugas = remember {
        mutableStateOf("")
    }
    val dtks = remember {
        mutableStateOf("")
    }
    val namaBapak = remember {
        mutableStateOf("")
    }
    val namaIbu = remember {
        mutableStateOf("")
    }
    val nikIbu = remember {
        mutableStateOf("")
    }
    val namaWali = remember {
        mutableStateOf("")
    }
    val penghasilan = remember {
        mutableStateOf("")
    }
    val catatan = remember {
        mutableStateOf("")
    }

    val formWajib = remember {
        mutableStateOf(false)
    }
    val pendidikanInt = remember {
        mutableIntStateOf(0)
    }
    val sumberInt = remember {
        mutableIntStateOf(0)
    }
    val pekerjaanInt = remember {
        mutableIntStateOf(0)
    }
    val statusInt = remember {
        mutableIntStateOf(0)
    }
    val pekerjaanOrtuInt = remember {
        mutableIntStateOf(0)
    }
    val tempatTinggalInt = remember {
        mutableIntStateOf(0)
    }
    val urlFisik = remember {
        mutableStateOf("")
    }
    val urlKk = remember {
        mutableStateOf("")
    }
    val urlKtp = remember {
        mutableStateOf("")
    }
    val urlRumah = remember {
        mutableStateOf("")
    }

    val idUser = remember {
        mutableStateOf(idUser)
    }
    val lat = remember {
        mutableStateOf("")
    }
    val long = remember {
        mutableStateOf("")
    }
    val onLoadingAssesmen = remember {
        mutableStateOf(false)
    }

    val locationPermission = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        locationPermission.value =true
    }
    val pdfUrl = remember {
        mutableStateOf("")
    }
    val location = LocationProviders(context)
    if (locationPermission.value) {
            location.getLastKnownLocation(success = {
                locationPermission.value =false
            }) {

            }
        location.LocationPermission(lat = lat, long = long)
        locationPermission.value =false


    }

    LoadingDialog(boolean = onLoadingAssesmen)
    formWajib.value = sumberString.value.isEmpty()
            || tanggalLahir.value.isEmpty()


    val dropDownCompose = DropdownCompose(vm, asVm)

    if (showPermissionKtp.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraKtp
        )
    }



    Box {

        Scaffold(
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
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = "Form Assessment",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Text(
                        text = "Form",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Transparent
                    )
                }
            }
        ) {
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = Color.White
            ) {
                Column(
                    Modifier
                        .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                        .verticalScroll(scrollState)
                ) {
                   AnimatedVisibility(visible = !lat.value.isNullOrEmpty() && !long.value.isNullOrEmpty() && long.value != "null" && lat.value != "null") {
                       MapsView(lat.value.toDouble(), long.value.toDouble())
                   }
                    Spacer(modifier = Modifier.height(12.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        FotoBox(
                            showPermissionRumah, capturedImagebyUriRumah, Modifier
                                .fillMaxWidth(0.5f), "Rumah"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        FotoBox(
                            showPermissionFisik, capturedImagebyUriFisik, Modifier
                                .fillMaxWidth(), "Kondisi Fisik"
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        FotoBox(
                            showPermissionKk, capturedImagebyUriKk, Modifier
                                .fillMaxWidth(0.5f), "KK"
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        FotoBox(
                            showPermissionKtp, capturedImagebyUriKtp, Modifier
                                .fillMaxWidth(), "KTP"
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
//                Pendidikan dan Sumber Kasus
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        DropdownField(
                            pendidikan,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            "Pendidikan",
                            pendidikanString.value
                        ) {
                            dropDownCompose.DropDownPendidikan(expand = pendidikan) { item, id ->
                                pendidikanString.value = item
                                pendidikanInt.intValue = id
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        DropdownField(
                            sumber,
                            modifier = Modifier.fillMaxWidth(),
                            "Sumber Kasus",
                            sumberString.value
                        ) {
                            dropDownCompose.DropDownSumberKasus(expand = sumber) { item, id ->
                                sumberString.value = item
                                sumberInt.intValue = id
                            }
                        }
                    }
                    AnimatedVisibility(visible = sumberString.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Sumber Kasus wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        DropdownField(
                            pekerjaan,
                            modifier = Modifier.fillMaxWidth(),
                            "Pekerjaan",
                            pekerjaanString.value
                        ) {
                            dropDownCompose.DropDownPekerjaan(expand = pekerjaan) { item, id ->
                                pekerjaanString.value = item
                                pekerjaanInt.intValue = id
                            }
                        }
                    }
//        AnimatedVisibility(visible = pekerjaanString.value.isEmpty()) {
//            Column {
//                Spacer(modifier = Modifier.height(6.dp))
//                Text(
//                    text = "* Pekerjaan wajib diisi",
//                    fontSize = 10.sp,
//                    color = Color.Red
//                )
//            }
//        }
                    Spacer(modifier = Modifier.height(14.dp))
                    DatePicker(context = context, date = tanggalLahir, label = "Tanggal Assesment")
                    AnimatedVisibility(visible = tanggalLahir.value.isEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* Tanggal Assesment wajib diisi",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = petugas,
                        label = "Petugas",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = dtks,
                        label = "Status DTKS",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(
                        statusOrtu,
                        modifier = Modifier.fillMaxWidth(),
                        "Status Orang Tua",
                        statusOrtuString.value
                    ) {
                        dropDownCompose.DropDownStatusOrtu(expand = statusOrtu) { item, id ->
                            statusOrtuString.value = item
                            statusInt.intValue = id
                        }
                    }
//        AnimatedVisibility(visible = statusOrtuString.value.isEmpty()) {
//            Column {
//                Spacer(modifier = Modifier.height(6.dp))
//                Text(
//                    text = "* Status Orang Tua wajib diisi",
//                    fontSize = 10.sp,
//                    color = Color.Red
//                )
//            }
//        }
                    Spacer(modifier = Modifier.height(14.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        DropdownField(
                            pekerjaanOrtu,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            "Pekerjaan Orang Tua",
                            pekerjaanOrtuString.value
                        ) {
                            dropDownCompose.DropDownPekerjaan(expand = pekerjaanOrtu) { item, id ->
                                pekerjaanOrtuString.value = item
                                pekerjaanOrtuInt.intValue = id
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        DropdownField(
                            tempatTinggal,
                            modifier = Modifier.fillMaxWidth(),
                            "Tempat Tinggal",
                            tempatTinggalString.value
                        ) {
                            dropDownCompose.DropDownTempatTinggal(expand = tempatTinggal) { item, id ->
                                tempatTinggalString.value = item
                                tempatTinggalInt.intValue = id
                            }
                        }
                    }
//        AnimatedVisibility(visible = pekerjaanOrtuString.value.isEmpty() || tempatTinggalString.value.isEmpty()) {
//            Column {
//                Spacer(modifier = Modifier.height(6.dp))
//                Text(
//                    text = "* Pekerjaan Orang Tua dan Tempat Tinggal wajib diisi",
//                    fontSize = 10.sp,
//                    color = Color.Red
//                )
//            }
//        }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = namaBapak,
                        label = "Nama Bapak",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = namaIbu,
                        label = "Nama Ibu",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    NikFilledText(
                        textString = nikIbu,
                        label = "NIK Ibu",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardType = KeyboardType.Number
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = namaWali,
                        label = "Nama Wali",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = penghasilan,
                        label = "Penghasilan",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardType = KeyboardType.Number
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = catatan,
                        label = "Catatan",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
//                    Foto
                    Spacer(modifier = Modifier.height(14.dp))
                    PickPdfFile(urri = pdfUri, onPdfPicked = { uri ->
                        pdfUri.value = uri
                        runBlocking {
                            try {
                                val stream = context.contentResolver.openInputStream(uri) ?: return@runBlocking
                                val request = RequestBody.create("application/pdf".toMediaTypeOrNull(), stream.readBytes())
                                val filePart = MultipartBody.Part.createFormData(
                                    "file",
                                    "test.pdf",
                                    request
                                )
                                asVm.addAssesmenFile(filePart) {
                                    pdfUrl.value = it.file_url!!
                                }
                            } catch (e:Exception) {
                                Log.d("ERROR KENAPA NICH",e.toString())
                            }
                        }

                    },
                    url = pdfUrl.value)
                    Spacer(modifier = Modifier.height(14.dp))
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

                        if (!formWajib.value
                        ) {
                            asVm.addAssesmen(
                                AssesmentBody(
                                    catatan = catatan.value,
                                    foto_kk = if (urlKk.value.isEmpty()) null else urlKk.value,
                                    foto_kondisi_fisik = if (urlFisik.value.isEmpty()) null else urlFisik.value,
                                    foto_ktp = if (urlKtp.value.isEmpty()) null else urlKtp.value,
                                    foto_rumah = if (urlRumah.value.isEmpty()) null else urlRumah.value,
                                    id_kerja_ortu = if (pekerjaanOrtuInt.intValue.equals(0)) null else pekerjaanOrtuInt.intValue,
                                    id_lembaga = 1,
                                    id_pekerjaan = if (pekerjaanInt.intValue.equals(0)) null else pekerjaanInt.intValue,
                                    id_pendidikan = if (pendidikanInt.intValue.equals(0)) null else pendidikanInt.intValue,
                                    id_pm = idUser.value.toInt(),
                                    id_status_ortu = if (statusInt.intValue.equals(0)) null else statusInt.intValue,
                                    id_sumber_kasus = sumberInt.intValue,
                                    id_tempat_tgl = if (tempatTinggalInt.intValue.equals(0)) null else tempatTinggalInt.intValue,
                                    lat = lat.value,
                                    long = long.value,
                                    nama_bpk = if (namaBapak.value.isEmpty()) null else namaBapak.value,
                                    nama_ibu = if (namaIbu.value.isEmpty()) null else namaIbu.value,
                                    nama_wali = if (namaWali.value.isEmpty()) null else namaWali.value,
                                    nik_ibu = if (nikIbu.value.isEmpty()) null else nikIbu.value,
                                    petugas = if (petugas.value.isEmpty()) null else petugas.value,
                                    status_dtks = if (dtks.value.isEmpty()) null else dtks.value,
                                    tanggal = tanggalLahir.value,
                                    penghasilan = if (penghasilan.value.isEmpty()) null else penghasilan.value.toLong(),
                                    file_lap = if (pdfUrl.value.isEmpty()) null else pdfUrl.value
                                ),
                                onLoadingAssesmen = onLoadingAssesmen
                            ) {
                                Toast.makeText(context,"Assesmen berhasil ditambahkan",Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }

                        }
                    }
                }
            }
        }


        if (openCameraRumah.value) {
            CameraView(
                outputDirectory = outputRumah,
                executor = cameraExecutorRumah,
                closeCamera = {
                    showPermissionRumah.value = false
                    openCameraRumah.value = false
                    cameraExecutorRumah.shutdown()
                },
                onImageCapture = { uri ->
                    capturedImagebyUriRumah.value = uri
                    showPermissionRumah.value = false
                    openCameraRumah.value = false
                    runBlocking {
                        val file = File(capturedImagebyUriRumah.value?.path!!)
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
                        asVm.addAssesmenFile(gambar) {
                            urlRumah.value = it.file_url!!
                        }
                    }
                    cameraExecutorRumah.shutdown()
                },
                onError = {

                }
            )
        } else if (openCameraFisik.value) {
            CameraView(
                outputDirectory = outputFisik,
                executor = cameraExecutorFisik,
                closeCamera = {
                    showPermissionFisik.value = false
                    openCameraFisik.value = false
                    cameraExecutorFisik.shutdown()
                },
                onImageCapture = { uri ->
                    capturedImagebyUriFisik.value = uri
                    showPermissionFisik.value = false
                    openCameraFisik.value = false
                    runBlocking {
                        val file = File(capturedImagebyUriFisik.value?.path!!)
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
                        asVm.addAssesmenFile(gambar) {
                            urlFisik.value = it.file_url!!
                        }
                    }
                    cameraExecutorFisik.shutdown()
                },
                onError = {

                }
            )
        } else if (openCameraKk.value) {
            CameraView(
                outputDirectory = outputKk,
                executor = cameraExecutorKk,
                closeCamera = {
                    showPermissionKk.value = false
                    openCameraKk.value = false
                    cameraExecutorKk.shutdown()
                },
                onImageCapture = { uri ->
                    capturedImagebyUriKk.value = uri
                    showPermissionKk.value = false
                    openCameraKk.value = false
                    runBlocking {
                        val file = File(capturedImagebyUriKk.value?.path!!)
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
                        asVm.addAssesmenFile(gambar) {
                            urlKk.value = it.file_url!!
                        }
                    }
                    cameraExecutorKk.shutdown()
                },
                onError = {

                }
            )
        } else if (openCameraKtp.value) {
            CameraView(
                outputDirectory = outputKtp,
                executor = cameraExecutorKtp,
                closeCamera = {
                    showPermissionKtp.value = false
                    openCameraKtp.value = false
                    cameraExecutorKtp.shutdown()
                },
                onImageCapture = { uri ->
                    capturedImagebyUriKtp.value = uri
                    showPermissionKtp.value = false
                    openCameraKtp.value = false
                    runBlocking {
                        val file = File(capturedImagebyUriKtp.value?.path!!)
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
                        asVm.addAssesmenFile(gambar) {
                            urlKtp.value = it.file_url!!
                        }
                    }
                    cameraExecutorKtp.shutdown()
                },
                onError = {

                }
            )
        }
    }
}

@Composable
fun PickPdfFile(isEdit: Boolean = true,urri: MutableState<Uri?>, url:String, onPdfPicked: (Uri) -> Unit) {
    val context = LocalContext.current
    val isNotNull = remember {
        mutableStateOf(false)
    }
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            isNotNull.value = uri != null
            uri?.let {
                onPdfPicked(it)
            }
        }

    Surface(
        color = Color(0xFFE8E8E8),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                if (isEdit) {
                    launcher.launch("application/pdf")
                }
            }
            .fillMaxWidth()
    ) {
        Row(
            Modifier
                .padding(
                    start = 10.dp,
                    end = 10.dp,
                    top = 14.dp,
                    bottom = 14.dp
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.document_icon),
                    contentDescription = null,
                    tint = Color(0xFF8F8F8F),
                    modifier = Modifier
                        .size(14.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (urri.value != null) getFileName(
                        context,
                        urri.value!!
                    ) else if (url.isNotEmpty() && url != "0") url.replace("http://api.sipentas-atensi.id/getFile/","") else "Unggah Dokumen PDF",
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF8F8F8F)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Icon(
                painter = painterResource(id = R.drawable.upload_icon),
                contentDescription = null,
                tint = Color(0xFF8F8F8F),
                modifier = Modifier
                    .size(14.dp)
            )
        }
    }
}

@SuppressLint("Range")
fun getFileName(context: Context, uri: Uri): String {

    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor.use {
            if (cursor!!.moveToFirst()) {
                return cursor!!.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }
        }
    }

    return uri.path!!.substring(uri.path!!.lastIndexOf('/') + 1)
}

@Composable
fun FotoBox(
    showPermissionRumah: MutableState<Boolean>,
    capturedImagebyUriRumah: MutableState<Uri>,
    modifier: Modifier = Modifier,
    label: String,
    url:String = "0",
    isEdit:Boolean = true
) {
    Surface(
        modifier
            .height(180.dp),
        color = Color(0xFFEB9B4B),
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    if (isEdit) {
                        showPermissionRumah.value = true
                    }
                 }
                .wrapContentSize(Alignment.Center)

        ) {
            if (capturedImagebyUriRumah.value.path?.isNotEmpty() == true) {
                Image(
                    painter = rememberImagePainter(capturedImagebyUriRumah.value),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else if (url.isNullOrEmpty() || url == "0" || url == "url" || url == "url foto") {
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
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            } else {
                AsyncImage(model = url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize())
            }

        }
    }
}