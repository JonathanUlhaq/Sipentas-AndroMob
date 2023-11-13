package com.example.sipentas.view.detail_assessment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.sipentas.component.OutlineButtonPrimary
import com.example.sipentas.models.AssesmentBody
import com.example.sipentas.models.verifikasi_atensi.VerifikasiAtensiList
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.ComposeDialog
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.MapsView
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.UploadStreamRequestBody
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.widgets.DatePicker
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import kotlinx.coroutines.runBlocking
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    curCatatan:String,
    id:String,
    idPm:String,
    longCur:String,
    latCur:String,
    elap:String
) {
    asVm.getPendidikan()
    asVm.getSumber()
    asVm.getPekerjaan()
    asVm.getStatusOrtu()
    asVm.getTempatTinggal()
    asVm.getAtensiAssesment(id.toInt())

    val atensi = asVm.atensiAssesment.collectAsState().value
    val currentIndex = remember {
        mutableIntStateOf(0)
    }

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
    val urlPdf = remember {
        mutableStateOf(elap)
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
        mutableStateOf(idPm)
    }
    val lat = remember {
        mutableStateOf("")
    }
    lat.value = latCur
    val long = remember {
        mutableStateOf("")
    }
    long.value = longCur
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
        Log.d("GET IDNYA",currentIndex.value.toString())
        asVm.deleteAtensi(currentIndex.value,isLoading, {
            Toast.makeText(context,"Tidak bisa dihapus, masih ada residensial  yang menggunakan id atensi ini",Toast.LENGTH_SHORT).show()
            confirmDelete.value = false
        }) {
            Toast.makeText(context,"Data Berhasil dihapus",Toast.LENGTH_SHORT).show()
            asVm.getAtensiAssesment(id.toInt())
            confirmDelete.value = false
        }
    }

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



    val getLocation = remember {
        mutableStateOf(false)
    }

    val currentLocationAction = remember {
        mutableStateOf(false)
    }

    val location = LocationProviders(context)
    if (getLocation.value) {
        location.LocationPermission(lat = lat, long = long).let {
            getLocation.value =false
            location.getLastKnownLocation(success = {
                getLocation.value =false
            }) {

            }

        }
    }
    Log.d("STATUSNYA ORTU,",statusOrtuString.value)
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
                   if (asVm.pref.getTipeSatker()!! == "3") {
                       Column(
                           horizontalAlignment = Alignment.CenterHorizontally
                       ) {
                           Switch(
                               checked = isEdit.value,
                               onCheckedChange = { isEdit.value = it },
                               colors = SwitchDefaults.colors(
                                   checkedBorderColor = Color.Transparent,
                                   checkedThumbColor = Color(0xFFFFFFFF),
                                   checkedTrackColor = Color(0xFF00A7C0),
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
                               color = Color(0xFF00A7C0),
                               modifier = Modifier
                                   .offset(y = -10.dp)
                           )
                       }
                   } else {
                       Text(
                           text = "Ubah",
                           style = MaterialTheme.typography.bodyMedium,
                           fontSize = 10.sp,
                           color = Color(0xFFFFFFFF),
                           modifier = Modifier
                               .offset(y = -10.dp)
                       )
                   }
               }
           },
           floatingActionButton = {

               val iconState by animateIntAsState(targetValue = if (!currentLocationAction.value) R.drawable.current_location else R.drawable.close_icon)

              if (isEdit.value) {
                  FloatingActionButton(onClick = { if (isEdit.value) {
                  when(currentLocationAction.value) {
                      false -> {
                          getLocation.value =true
                          currentLocationAction.value = true
                      }
                      else -> {
                          getLocation.value = false
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
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionRumah, capturedImagebyUriRumah, Modifier
                               .fillMaxWidth(0.5f), "Rumah",urlRumah.value,
                           isEdit = isEdit.value
                       )
                       Spacer(modifier = Modifier.width(4.dp))
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionFisik, capturedImagebyUriFisik, Modifier
                               .fillMaxWidth(), "Kondisi Fisik",urlFisik.value,
                           isEdit = isEdit.value
                       )
                   }
                   Spacer(modifier = Modifier.height(14.dp))
                   Row(
                       Modifier
                           .fillMaxWidth()
                   ) {
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionKk, capturedImagebyUriKk, Modifier
                               .fillMaxWidth(0.5f), "KK",urlKk.value,
                           isEdit = isEdit.value
                       )
                       Spacer(modifier = Modifier.width(4.dp))
                       com.example.sipentas.view.form_assessment.FotoBox(
                           showPermissionKtp, capturedImagebyUriKtp, Modifier
                               .fillMaxWidth(), "KTP",urlKtp.value,
                           isEdit = isEdit.value
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
                       isEdit.value,
                       urri = pdfUri,
                       onPdfPicked = { uri ->
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
                                   urlPdf.value = it.file_url!!
                               }
                               } catch (e:Exception) {
                                   Log.d("ERROR KENAPA NICH",e.toString())
                               }
                           }
//
                       },
                       url = urlPdf.value)
                   Spacer(modifier = Modifier.height(14.dp))
                   if (asVm.pref.getTipeSatker()!! != "3") {
                       Spacer(modifier = Modifier.height(10.dp))
                       Divider( color = Color(0xFF8f8f8f))
                       Spacer(modifier = Modifier.height(6.dp))
                       if (!atensi.rows.isNullOrEmpty())
                       atensi.rows.forEach {
                               item ->

                               ListAtensiAssesment(
                                   navController,
                                   item,
                                   item.id_atensi,
                                   item.id_pendekatan_atensi
                               )


                           Spacer(modifier = Modifier.height(14.dp))
                       }
                   }
                   AnimatedVisibility(visible = isEdit.value) {
                      Column {
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
                              if (!formWajib.value) {
                                asVm.updateAssesment(id.toInt(), body =
                                AssesmentBody(
                                    catatan = catatan.value,
                                    foto_kk = if (urlKk.value == "0"|| urlKk.value == "url" || urlKk.value == "url foto") null else urlKk.value,
                                    foto_kondisi_fisik = if (urlFisik.value == "0" || urlFisik.value == "url" || urlFisik.value == "url foto") null else urlFisik.value,
                                    foto_ktp = if (urlKtp.value == "0" || urlKtp.value == "url" || urlKtp.value == "url foto") null else urlKtp.value,
                                    foto_rumah = if (urlRumah.value == "0" || urlRumah.value == "url" || urlRumah.value == "url foto") null else urlRumah.value,
                                    id_kerja_ortu = if (pekerjaanOrtuInt.intValue.equals(0)) null else pekerjaanOrtuInt.intValue,
                                    id_lembaga = 1,
                                    id_pekerjaan = if (pekerjaanInt.intValue.equals(0)) null else pekerjaanInt.intValue,
                                    id_pendidikan = if (pendidikanInt.intValue.equals(0)) null else pendidikanInt.intValue,
                                    id_pm = idPm.toInt(),
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
                                    file_lap = urlPdf.value
                                ),
                                loading = isLoading) {
                                    Toast.makeText(context,"Assesment berhasil diubah",Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }

                              }
                          }
                          Spacer(modifier = Modifier.height(10.dp))
                          OutlineButtonPrimary(text = {
                              Text(
                                  text = "Tambah Atensi",
                                  style = MaterialTheme.typography.titleMedium,
                                  modifier = Modifier
                                      .padding(top = 6.dp, bottom = 6.dp),
                                  fontSize = 14.sp
                              )
                          }) {
                                navController.navigate(AppRoute.AddAtensi.route+"/$idPm/$id")
                          }

                          if (!atensi.rows.isNullOrEmpty()) {
                              Spacer(modifier = Modifier.height(10.dp))
                              Divider( color = Color(0xFF8f8f8f))
                              Spacer(modifier = Modifier.height(6.dp))
                              Text(
                                  text = "List Atensi",
                                  style = MaterialTheme.typography.titleMedium,
                                  modifier = Modifier
                                      .padding(top = 6.dp, bottom = 6.dp),
                                  fontSize = 12.sp,
                                  color = Color(0xFF8f8f8f)
                              )
                              atensi.rows.forEach {
                                  item ->
                                  val delete = SwipeAction(
                                      icon = painterResource(id = R.drawable.icon_delete),
                                      background = Color(0xFFEF3131),
                                      onSwipe = {
                                          currentIndex.intValue = item.id_atensi!!.toInt()
                                          confirmDelete.value = true
                                      }
                                  )
                                  SwipeableActionsBox(
                                      endActions = listOf(delete),
                                      modifier = Modifier
                                          .clip(RoundedCornerShape(6.dp))
                                  ) {
                                      ListAtensiAssesment(
                                          navController,
                                          item,
                                          item.id_atensi,
                                          item.id_pendekatan_atensi
                                      )
                                  }

                                  Spacer(modifier = Modifier.height(14.dp))
                              }
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
private fun ListAtensiAssesment(
    navController: NavController,
    item: VerifikasiAtensiList,
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