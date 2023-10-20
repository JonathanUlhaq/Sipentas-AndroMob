package com.example.sipentas.view.form_pm

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.LocationManager
import android.net.Uri
import android.provider.Settings
import android.util.Log
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
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.widgets.DatePicker
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import id.zelory.compressor.constraint.destination
import id.zelory.compressor.constraint.size
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors

fun compressImage(file: File, context: Context): File {
    val compressedFile = File(context.cacheDir, "compressed_" + file.name)
    val bitmap = BitmapFactory.decodeFile(file.path)

    var outputStream: FileOutputStream? = null
    try {
        outputStream = FileOutputStream(compressedFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream) // 80 adalah kualitas gambar setelah dikompres
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        outputStream?.flush()
        outputStream?.close()
    }

    return compressedFile
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormView(
    navController: NavController,
    vm:FormPmViewModel,
    asVm:AssesmenViewModel
) {

    vm.getProvinsi()
    vm.getKategori()
    val nama = remember {
        mutableStateOf("")
    }
    val kategoriPpks = remember {
        mutableStateOf(false)
    }
    val ragam = remember {
        mutableStateOf(false)
    }
    val ragamId = remember {
        mutableIntStateOf(0)
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
    val kategoriPpksInt = remember {
        mutableIntStateOf(0)
    }
    vm.getRagam(kategoriPpksInt.intValue)
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
    val agamaId = remember {
        mutableIntStateOf(0)
    }
    val provinsiString = remember {
        mutableStateOf("")
    }
    val provinsiInt = remember {
        mutableIntStateOf(0)
    }
    vm.getKabupaten(provinsiInt.intValue)
    val kabupatenString = remember {
        mutableStateOf("")
    }

    val location = LocationProviders(context)
    if (locationPermission.value) {
        location.LocationPermission(lat = lat, long = long)
    }
    val dropCompose = DropdownCompose(vm,asVm)

    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val scrollState = rememberScrollState()

    val keteranganPPks = remember {
        mutableStateOf("")
    }
    val tempatLahir = remember {
        mutableStateOf("")
    }
    val tanggalLahir = remember {
        mutableStateOf("")
    }
    val nomorHandphone = remember {
        mutableStateOf("")
    }
    val kabupatenId = remember {
        mutableIntStateOf(0)
    }
    vm.getKecamatan(kabupatenId.value)
    val kecamatan = remember {
        mutableStateOf(false)
    }
    val kecamatanString = remember {
        mutableStateOf("")
    }
    val kecamatanId = remember {
        mutableIntStateOf(0)
    }
    vm.getKelurahan(kecamatanId.intValue)
    val kelurahan = remember {
        mutableStateOf(false)
    }
    val kelurahanString = remember {
        mutableStateOf("")
    }
    val kelurahanId = remember {
        mutableIntStateOf(0)
    }

    val namaJalan = remember {
        mutableStateOf("")
    }
    val nik = remember {
        mutableStateOf("")
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
                        "Kategori PPKS *",
                        kategoriPpksString.value) {
                        dropCompose.DropDownPpks(expand = kategoriPpks ) { string,id ->
                            kategoriPpksString.value = string
                            ragamString.value = ""
                            kategoriPpksInt.intValue = id
                        }

                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    DropdownField(ragam,
                        modifier = Modifier.fillMaxWidth(),
                        "Pilih Ragam *",
                        ragamString.value,
                        isEnable = kategoriPpksInt.intValue != 0) {
                        dropCompose.DropDownRagam(expand = ragam ) { string, id ->
                            ragamString.value = string
                            ragamId.intValue = id
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
                        modifier = Modifier
                            .fillMaxWidth()
                    )
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
                    Row(Modifier
                     .fillMaxWidth()) {
                     DropdownField(kelamin, modifier = Modifier.fillMaxWidth(0.5f),"Jenis Kelamin",kelaminString.value) {
                         dropCompose.DropDownJenisKelamin(expand = kelamin ) {
                             kelaminString.value = it
                         }
                     }
                     Spacer(modifier = Modifier.width(4.dp))
                     DropdownField(agama, modifier = Modifier.fillMaxWidth(),"Agama",agamaString.value){
                         dropCompose.DropDownAgama(expand = agama ) { string,index ->
                             agamaString.value = string
                             agamaId.intValue = index
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

                    DropdownField(provinsi, modifier = Modifier.fillMaxWidth(),"Provinsi",provinsiString.value) {
                        dropCompose.DropDownProvinsi(expand = provinsi ) { string, int ->
                            provinsiString.value = string
                            kabupatenString.value = ""
                            provinsiInt.intValue = int
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(kabupaten,
                        modifier = Modifier.fillMaxWidth(),
                        "Kabupaten",
                        kabupatenString.value,
                        isEnable = provinsiString.value.isNotEmpty()) {
                        dropCompose.DropDownKabupaten(expand = kabupaten ) { string,id ->
                            kabupatenString.value = string
                            kabupatenId.intValue = id
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(kecamatan,
                        modifier = Modifier.fillMaxWidth(),
                        "Kecamatan",
                        kecamatanString.value,
                        isEnable = kabupatenString.value.isNotEmpty()) {
                        dropCompose.DropDownKecamatan(expand = kecamatan ) { string, id ->
                            kecamatanString.value = string
                            kecamatanId.intValue = id
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    DropdownField(kelurahan,
                        modifier = Modifier.fillMaxWidth(),
                        "Kelurahan",
                        kelurahanString.value,
                        isEnable = kecamatanString.value.isNotEmpty()) {
                        dropCompose.DropDownKelurahan(expand = kelurahan ) { string, id ->
                            kelurahanString.value = string
//                            kelurahanId.intValue = id
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = namaJalan,
                        label = "Nama Jalan / Alamat Lengkap",
                        imeAction = ImeAction.Default,
                        singleLine = false)
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
                        if (locationPermission.value) {
                            location.getLastKnownLocation(success = {

                            }) {

                            }

                        } else {
                            locationPermission.value = true
                        }
                        if (capturedImagebyUri.value.path != null) {
                            runBlocking {
                                val file = File(capturedImagebyUri.value.path!!)
                                val compressor = Compressor.compress(context, file) {
                                    default()
                                    destination(file)
                                }
                                Log.d("EXIST", (compressor.length() / 1024).toString())
                                Log.d("UKURAN", compressor.name)
                                val requestBody = compressor.asRequestBody("image/*".toMediaType())
                                val gambar = MultipartBody.Part.createFormData(
                                    "file",
                                    compressor.name,
                                    requestBody
                                )

//                                vm.postPhoto(gambar) {
//                                        vm.addPm(
//                                            PostPmModel(
//                                                name = nama.value,
//                                                date_of_birth = tanggalLahir.value,
//                                                flag = 1,
//                                                foto_diri = it.file_url!!,
//                                                gender = kelaminString.value,
//                                                kabupaten_id = kabupatenId.intValue,
//                                                kecamatan_id = kecamatanId.intValue,
//                                                kelurahan_id = kelurahanId.intValue,
//                                                ket_ppks = keteranganPPks.value,
//                                                kluster_id = kategoriPpksInt.intValue,
//                                                kode_pos = "57716",
//                                                nama_jalan = namaJalan.value,
//                                                nik = nik.value,
//                                                phone_number = nomorHandphone.value,
//                                                place_of_birth = tempatLahir.value,
//                                                provinsi_id = provinsiInt.intValue,
//                                                ragam_id = ragamId.intValue,
//                                                religion = agamaId.intValue,
//                                                satker_id = 9
//                                            )
//                                        ) {
//                                            navController.popBackStack()
//                                        }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }


}

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

fun showGPSDisabledAlert(context: Context) {
    AlertDialog.Builder(context).apply {
        setTitle("Aktifkan GPS")
        setMessage("GPS tidak aktif. Anda harus mengaktifkan GPS untuk mengirim form")
        setPositiveButton("Settings") { _, _ ->
            context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }
        setNegativeButton("Cancel", null)
    }.show()
}

