package com.example.sipentas.view.detail_assessment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
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
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.util.concurrent.Executors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAssessmentView(
    navController: NavController,
    asVm:AssesmenViewModel,
    vm:FormPmViewModel,
    urlRumahs:String,
    urlFisiks:String,
    urlKks:String,
    urlKtps:String,
    curPendidikan:String,
    curPendidikanId:String,
    curSumber:String,
    curSumberId:String,
    curPekerjaan:String,
    curPekerjaanId:String,
    curTanggal:String,
    curPetugas:String,
    curDtks:String,
    curStatusOrtu:String,
    curStatusOrtuId:String,
    curPekerOrtu:String,
    curPekerOrtuId:String,
    curTempatTinggal:String,
    curTempatTinggalId:String,
    curNamaBapak:String,
    curNamaIbu:String,
    curNikIbu:String,
    curNamaWali:String,
    curPenghasilan:String,
    curCatatan:String
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
        mutableStateOf(if (curPendidikan == "0") "" else curPendidikan)
    }

    val sumber = remember {
        mutableStateOf(false)
    }
    val sumberString = remember {
        mutableStateOf(curSumber)
    }

    val pekerjaan = remember {
        mutableStateOf(false)
    }
    val pekerjaanString = remember {
        mutableStateOf(if (curPekerjaan == "0") "" else curPekerjaan)
    }

    val lembaga = remember {
        mutableStateOf(false)
    }
    val lembagaString = remember {
        mutableStateOf("Pemberdayaan")
    }

    val statusOrtu = remember {
        mutableStateOf(false)
    }
    val statusOrtuString = remember {
        mutableStateOf(if (curStatusOrtu == "0" || curStatusOrtu == "null") "" else curStatusOrtu)
    }

    val pekerjaanOrtu = remember {
        mutableStateOf(false)
    }
    val pekerjaanOrtuString = remember {
        mutableStateOf(if (curPekerOrtu == "0") "" else curPekerOrtu)
    }


    val tempatTinggal = remember {
        mutableStateOf(false)
    }
    val tempatTinggalString = remember {
        mutableStateOf(if (curTempatTinggal == "0") "" else curTempatTinggal)
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
    val isEdit = remember {
        mutableStateOf(false)
    }

    val urlFisik = remember {
        mutableStateOf(urlFisiks)
    }
    val urlKk = remember {
        mutableStateOf(urlKks)
    }
    val urlKtp = remember {
        mutableStateOf(urlKtps)
    }
    val urlRumah = remember {
        mutableStateOf(urlRumahs)
    }

    val idUser = remember {
        mutableStateOf("")
    }
    val lat = remember {
        mutableStateOf("")
    }
    val long = remember {
        mutableStateOf("")
    }
    val dropDownCompose = DropdownCompose(vm, asVm)
    val pendidikanInt = remember {
        mutableIntStateOf(curPendidikanId.toInt())
    }
    val sumberInt = remember {
        mutableIntStateOf(curSumberId.toInt())
    }
    val pekerjaanInt = remember {
        mutableIntStateOf(curPekerjaanId.toInt())
    }
    val tanggalLahir = remember {
        mutableStateOf(if (curTanggal == "0") "" else curTanggal)
    }
    val petugas = remember {
        mutableStateOf(if (curPetugas == "0") "" else curPetugas)
    }
    val dtks = remember {
        mutableStateOf(if (curDtks == "0") "" else curDtks)
    }
    val statusInt = remember {
        mutableIntStateOf(curStatusOrtuId.toInt())
    }
    val pekerjaanOrtuInt = remember {
        mutableIntStateOf(curPekerOrtuId.toInt())
    }
    val tempatTinggalInt = remember {
        mutableIntStateOf(curTempatTinggalId.toInt())
    }
    val namaBapak = remember {
        mutableStateOf(if (curNamaBapak == "0") "" else curNamaBapak)
    }
    val namaIbu = remember {
        mutableStateOf(if (curNamaIbu == "0") "" else curNamaIbu)
    }
    val nikIbu = remember {
        mutableStateOf(if (curNikIbu == "0") "" else curNikIbu)
    }
    val namaWali = remember {
        mutableStateOf(if (curNamaWali == "0") "" else curNamaWali)
    }
    val penghasilan = remember {
        mutableStateOf(if (curPenghasilan == "0") "" else curPenghasilan)
    }
    val catatan = remember {
        mutableStateOf(if (curCatatan == "0") "" else curCatatan)
    }

    val formWajib = remember {
        mutableStateOf(false)
    }

    formWajib.value = sumberString.value.isEmpty()


    if (showPermissionKtp.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraKtp
        )
    }
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
                           tint = Color.Black
                       )
                   }
                   Text(
                       text = "Detail Assesment",
                       style = MaterialTheme.typography.titleMedium,
                       color = Color.Black
                   )
                   Column(
                       horizontalAlignment = Alignment.CenterHorizontally
                   ) {
                       Switch(checked =isEdit.value ,
                           onCheckedChange = {isEdit.value = it},
                           colors = SwitchDefaults.colors(
                               checkedBorderColor = Color.Transparent,
                               checkedThumbColor = Color.White,
                               checkedTrackColor = Color(0xFF00A7C0),
                               uncheckedBorderColor = Color.Transparent,
                               uncheckedThumbColor = Color.White.copy(0.6f),
                               uncheckedTrackColor = Color(0xFF8f8f8f)
                           ),
                           modifier = Modifier
                               .scale(0.7f))
                       Text(text = "Ubah",
                           style = MaterialTheme.typography.bodyMedium,
                           fontSize = 10.sp,
                           color = Color(0xFF00A7C0),
                           modifier = Modifier
                               .offset(y= -10.dp))
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
               Column(
                   Modifier
                       .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                       .verticalScroll(scrollState)
               ) {
                   Row(
                       Modifier
                           .fillMaxWidth()
                   ) {
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionRumah, capturedImagebyUriRumah, Modifier
                               .fillMaxWidth(0.5f), "Rumah",urlRumah.value
                       )
                       Spacer(modifier = Modifier.width(4.dp))
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionFisik, capturedImagebyUriFisik, Modifier
                               .fillMaxWidth(), "Kondisi Fisik",urlFisik.value
                       )
                   }
                   Spacer(modifier = Modifier.height(14.dp))
                   Row(
                       Modifier
                           .fillMaxWidth()
                   ) {
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionKk, capturedImagebyUriKk, Modifier
                               .fillMaxWidth(0.5f), "KK",urlKk.value
                       )
                       Spacer(modifier = Modifier.width(4.dp))
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionKtp, capturedImagebyUriKtp, Modifier
                               .fillMaxWidth(), "KTP",urlKtp.value
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
                           pendidikanString.value,
                           isEnable = isEdit.value
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
                           sumberString.value,
                           isEnable = isEdit.value
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
                               text = "Sumber Kasus wajib diisi",
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
                           pekerjaanString.value,
                           isEnable = isEdit.value
                       ) {
                           dropDownCompose.DropDownPekerjaan(expand = pekerjaan) { item, id ->
                               pekerjaanString.value = item
                               pekerjaanInt.intValue = id
                           }
                       }
                   }
//                   AnimatedVisibility(visible = pekerjaanString.value.isEmpty()) {
//                       Column {
//                           Spacer(modifier = Modifier.height(6.dp))
//                           Text(
//                               text = "* Pekerjaan wajib diisi",
//                               fontSize = 10.sp,
//                               color = Color.Red
//                           )
//                       }
//                   }
                   Spacer(modifier = Modifier.height(14.dp))
                   DatePicker(context = context, date = tanggalLahir, label = "Tanggal Assesment",
                       boolean = isEdit.value)
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
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )

                   Spacer(modifier = Modifier.height(14.dp))
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = dtks,
                       label = "Status DTKS",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   DropdownField(
                       statusOrtu,
                       modifier = Modifier.fillMaxWidth(),
                       "Status Orang Tua",
                       statusOrtuString.value,
                       isEnable = isEdit.value
                   ) {
                       dropDownCompose.DropDownStatusOrtu(expand = statusOrtu) { item, id ->
                           statusOrtuString.value = item
                           statusInt.intValue = id
                       }
                   }
//                   AnimatedVisibility(visible = statusOrtuString.value.isEmpty()) {
//                       Column {
//                           Spacer(modifier = Modifier.height(6.dp))
//                           Text(
//                               text = "* Status Orang Tua wajib diisi",
//                               fontSize = 10.sp,
//                               color = Color.Red
//                           )
//                       }
//                   }
                   Spacer(modifier = Modifier.height(14.dp))
                   Row(
                       Modifier
                           .fillMaxWidth()
                   ) {
                       DropdownField(
                           pekerjaanOrtu,
                           modifier = Modifier.fillMaxWidth(0.5f),
                           "Pekerjaan Orang Tua",
                           pekerjaanOrtuString.value,
                           isEnable = isEdit.value
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
                           tempatTinggalString.value,
                           isEnable = isEdit.value
                       ) {
                           dropDownCompose.DropDownTempatTinggal(expand = tempatTinggal) { item, id ->
                               tempatTinggalString.value = item
                               tempatTinggalInt.intValue = id
                           }
                       }
                   }
//                   AnimatedVisibility(visible = pekerjaanOrtuString.value.isEmpty() || tempatTinggalString.value.isEmpty()) {
//                       Column {
//                           Spacer(modifier = Modifier.height(6.dp))
//                           Text(
//                               text = "* Pekerjaan Orang Tua dan Tempat Tinggal wajib diisi",
//                               fontSize = 10.sp,
//                               color = Color.Red
//                           )
//                       }
//                   }
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = namaBapak,
                       label = "Nama Bapak",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = namaIbu,
                       label = "Nama Ibu",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = nikIbu,
                       label = "NIK Ibu",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value,
                       keyboardType = KeyboardType.Number
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = namaWali,
                       label = "Nama Wali",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = penghasilan,
                       label = "Penghasilan",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       keyboardType = KeyboardType.Number,
                       enabled = isEdit.value
                   )
                   Spacer(modifier = Modifier.height(14.dp))
                   FilledTextField(
                       textString = catatan,
                       label = "Catatan",
                       imeAction = ImeAction.Default,
                       singleLine = true,
                       modifier = Modifier
                           .fillMaxWidth(),
                       enabled = isEdit.value
                   )
//                    Foto
                   Spacer(modifier = Modifier.height(14.dp))
                   com.example.sipentas.view.form_assessment.PickPdfFile(
                       urri = pdfUri,
                       onPdfPicked = { uri ->
                           pdfUri.value = uri
                       })
                   Spacer(modifier = Modifier.height(14.dp))
                   AnimatedVisibility(visible = isEdit.value) {
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
                               && urlKk.value.isNotEmpty()
                               && urlRumah.value.isNotEmpty()
                               && urlFisik.value.isNotEmpty()
                               && urlKtp.value.isNotEmpty()
                           ) {
//                            asVm.addAssesmen(
//                                AssesmentBody(
//                                    catatan = catatan.value,
//                                    foto_kk = urlKk.value,
//                                    foto_kondisi_fisik = urlFisik.value,
//                                    foto_ktp = urlKtp.value,
//                                    foto_rumah = urlRumah.value,
//                                    id_kerja_ortu = pekerjaanOrtuInt.intValue,
//                                    id_lembaga = 1,
//                                    id_pekerjaan = pekerjaanInt.intValue,
//                                    id_pendidikan = pendidikanInt.intValue,
//                                    id_pm = idUser.value.toInt(),
//                                    id_status_ortu = statusInt.intValue,
//                                    id_sumber_kasus = sumberInt.intValue,
//                                    id_tempat_tgl = tempatTinggalInt.intValue,
//                                    lat = lat.value,
//                                    long = long.value,
//                                    nama_bpk = namaBapak.value,
//                                    nama_ibu = namaIbu.value,
//                                    nama_wali = namaWali.value,
//                                    nik_ibu = nikIbu.value,
//                                    petugas = petugas.value,
//                                    status_dtks = "status_dtks",
//                                    tanggal = tanggalLahir.value,
//                                    flag = 0
//                                )
//                            )

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
fun PickPdfFile(urri: MutableState<Uri?>, onPdfPicked: (Uri) -> Unit) {
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
                launcher.launch("application/pdf")
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
                    ) else "Unggah Dokumen PDF",
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
private fun FotoBox(
    showPermissionRumah: MutableState<Boolean>,
    capturedImagebyUriRumah: MutableState<Uri>,
    modifier: Modifier = Modifier,
    label: String,
    isEnable:Boolean
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
                    if (isEnable) {
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
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
            }

        }
    }
}