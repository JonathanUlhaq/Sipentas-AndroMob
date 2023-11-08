package com.example.sipentas.view.detail_view

import android.net.Uri
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
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
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.component.NikFilledText
import com.example.sipentas.component.OutlineButtonPrimary
import com.example.sipentas.models.LoginModel
import com.example.sipentas.models.PmUpdateBody
import com.example.sipentas.models.verifikasi_assesment.VerifAssesmenList
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.navigation.BotNavRoute
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.ComposeDialog
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
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
    fotoDiri:String,
    asVm:AssesmenViewModel,
    id_pm:String,
    currentAgamaId:String,
    currentRagamId:String,
    currentKelurahanId:String?
) {

    vm.getProvinsi()
    vm.getKategori()
    vm.getAssesmentPm(id_pm.toInt())
    val assesmentState = vm.assesmentState.collectAsState().value
    val context = LocalContext.current
    val userType = vm.pref.getTipeSatker()

    val loading = remember {
        mutableStateOf(false)
    }
    LoadingDialog(boolean = loading)

    val confirmDelete = remember {
        mutableStateOf(false)
    }

    val currentIndex = remember {
        mutableIntStateOf(0)
    }

    ComposeDialog(
        title = "Hapus Data",
        desc = " Apakah anda yakin menghapus data ini ?",
        boolean = confirmDelete
    ) {
        Log.d("GET IDNYA",currentIndex.value.toString())
        asVm.deleteAssesment(currentIndex.value,loading, {
            Toast.makeText(context,"Tidak bisa dihapus, masih ada atensi yang menggunakan id assesmen ini",Toast.LENGTH_SHORT).show()
            confirmDelete.value = false
        }) {
            Toast.makeText(context,"Data Berhasil dihapus",Toast.LENGTH_SHORT).show()
            vm.getAssesmentPm(id_pm.toInt())
            confirmDelete.value = false
        }
    }

    val urlFoto = remember {
        mutableStateOf(fotoDiri)
    }
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
        mutableStateOf(currentRagam.replace('-','/'))
    }

    val agamaString = remember {
        mutableStateOf(if (currentAgama == "0" || currentAgama == "null") "" else currentAgama)
    }

    val provinsiString = remember {
        mutableStateOf(if (currentProvinsi == "0") "" else currentProvinsi)
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
        mutableStateOf(if (ketPpks == "0") "" else ketPpks)
    }
    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()
    val scrollState = rememberScrollState()

    val dropCompose = DropdownCompose(vm,asVm)

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
        mutableStateOf(if (currentNik == "0") "" else currentNik)
    }
    val tempatLahir = remember {
        mutableStateOf(if (currentTempatLahir == "0") "" else currentTempatLahir)
    }
    val tanggalLahir = remember {
        mutableStateOf(if (currentTanggalLahir == "0") "" else currentTanggalLahir)
    }
    val nomorHandphone = remember {
        mutableStateOf(if (currentNomorHandphone == "0") "" else currentNomorHandphone)
    }
    val kecamatanString = remember {
        mutableStateOf(if (currentKecamatan == "0")"" else currentKecamatan)
    }
    val kelurahanString = remember {
        mutableStateOf(if (currentKelurahan == "0")"" else currentKecamatan)
    }
    val namaJalan = remember {
        mutableStateOf(if (currentJalan == "0") "" else currentJalan)
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
        mutableLongStateOf(currentKelurahanId!!.toLong())
    }
    val ragamId = remember {
        mutableIntStateOf(currentRagamId.toInt())
    }
    val agamaId = remember {
        mutableIntStateOf(if (currentAgamaId == "0") 0 else currentAgamaId.toInt())
    }

    vm.getKelurahan(kecamatanId.intValue)
    formWajib.value = kategoriPpksString.value.isEmpty() || ragamString.value.isEmpty()
            || nama.value.isEmpty()
            || kelaminString.value.isEmpty()
            || agamaString.value.isEmpty()
            || provinsiString.value.isEmpty()
            || kabupatenString.value.isEmpty()

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
                        text = "Detail Penerima Manfaat",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    if (userType!! == "3") {
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
            }
        ) {
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = Color(0xFFFFFFFF)
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
                    Surface(
                        Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        color = Color(0xFF00A7C0),
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
                            } else if (fotoDiri.isNullOrEmpty() || fotoDiri == "0" || fotoDiri == "url") {
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
                            else {
                                AsyncImage(model = fotoDiri,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize())
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
                                ragamId.intValue = index
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
                            .fillMaxWidth(),
                        enabled = isEdit.value
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
                    NikFilledText(
                        textString = nik,
                        label = "NIK",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = isEdit.value
                    )
                    AnimatedVisibility(visible = nik.value.length != 16 && nik.value.isNotEmpty()) {
                        Column {
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "* NIK harus diisi 16 digit",
                                fontSize = 10.sp,
                                color = Color.Red
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    FilledTextField(
                        textString = tempatLahir,
                        label = "Tempat Lahir",
                        imeAction = ImeAction.Default,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(),
                        enabled = isEdit.value
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    DatePicker(context = context, date = tanggalLahir, boolean = isEdit.value)

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
                                        agamaId.intValue = index
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
                            .fillMaxWidth(),
                        enabled = isEdit.value
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
                        isEnable = provinsiString.value.isNotEmpty() && isEdit.value
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
                        isEnable = kabupatenString.value.isNotEmpty() && isEdit.value
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
                        isEnable = kecamatanString.value.isNotEmpty() && isEdit.value
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
                        singleLine = false,
                        enabled = isEdit.value
                    )
                    if (userType!! != "3") {
                        Spacer(modifier = Modifier.height(6.dp))
                        Divider( color = Color(0xFF8f8f8f))
                        Spacer(modifier = Modifier.height(6.dp))

                        if ( !assesmentState.data.isNullOrEmpty()) {
                            assesmentState.data.forEach { item ->
                                val changeColor by animateColorAsState(
                                    targetValue = if (item.flag == 0) Color(
                                        0xFFD0D34B
                                    )
                                    else if (item.flag == 1) Color(0xFF4BD379)
                                    else Color(0xFFD34B4B)
                                )
                                val changeIcon by animateIntAsState(
                                    targetValue = if (item.flag == 0)
                                        R.drawable.process_icon else if (item.flag == 1)
                                        R.drawable.check_icon
                                    else R.drawable.close_icon
                                )
                                    AssesList(
                                        navController,
                                        item,
                                        item.id_pm,
                                        item.id_pendidikan,
                                        item.id_pekerjaan,
                                        item.id_status_ortu,
                                        changeColor,
                                        changeIcon
                                    )


                                Spacer(modifier = Modifier.height(14.dp))
                            }
                        }

                }
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
                                vm.updatePm(id = id_pm.toInt(), body = PmUpdateBody(
                                    date_of_birth = if (tanggalLahir.value.isEmpty()) null else tanggalLahir.value,
                                    flag = null,
                                    foto_diri = if (urlFoto.value =="url" || urlFoto.value == "0") null else urlFoto.value,
                                    gender = kelaminString.value,
                                    kabupaten_id = kabupatenId.value,
                                    kecamatan_id = if (kecamatanId.value.equals(0)) null else kecamatanId.value,
                                    kelurahan_id = if (kelurahanId.value.equals(0L)) null else kelurahanId.value,
                                    ket_ppks = if (keteranganPPks.value.isEmpty()) null else keteranganPPks.value,
                                    kluster_id = kategoriPpksInt.value,
                                    kode_pos = null,
                                    nama_jalan = if (namaJalan.value.isEmpty()) null else namaJalan.value,
                                    name = nama.value,
                                    nik = if (nik.value.isEmpty()) "" else nik.value,
                                    phone_number = if (nomorHandphone.value.isEmpty()) "" else nomorHandphone.value,
                                    place_of_birth = if (tempatLahir.value.isEmpty()) "" else tempatLahir.value,
                                    provinsi_id = if (provinsiInt.intValue.equals(0)) null else provinsiInt.intValue,
                                    ragam_id = ragamId.intValue,
                                    religion = if (agamaId.intValue.equals(0)) null else agamaId.intValue,
                                    satker_id = 9
                                ), onError = {

                                } ) {
                                    Toast.makeText(context,"Data berhasil diubah",Toast.LENGTH_SHORT).show()
                                    navController.popBackStack()
                                }
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            if( assesmentState.data.isNullOrEmpty() || assesmentState.data.contains(
                                    VerifAssesmenList(flag = 2)
                                )) {
                                OutlineButtonPrimary(text = {
                                        Text(
                                            text = "Tambah Assesment",
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier
                                                .padding(top = 6.dp, bottom = 6.dp),
                                            fontSize = 14.sp
                                        )
                                }) {
                                   navController.navigate(AppRoute.FormAssessment.route + "/$id_pm")
                                }
                            } else {
                                Spacer(modifier = Modifier.height(6.dp))
                                Divider( color = Color(0xFF8f8f8f))
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = "List Assesment",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier
                                        .padding(top = 6.dp, bottom = 6.dp),
                                    fontSize = 12.sp,
                                    color = Color(0xFF8f8f8f)
                                )
                                assesmentState.data.forEach { item ->
                                        val changeColor by animateColorAsState(
                                            targetValue = if (item.flag == 0) Color(
                                                0xFFD0D34B
                                            )
                                            else if (item.flag == 1) Color(0xFF4BD379)
                                            else Color(0xFFD34B4B)
                                        )
                                        val changeIcon by animateIntAsState(
                                            targetValue = if (item.flag == 0)
                                                R.drawable.process_icon else if (item.flag == 1)
                                                R.drawable.check_icon
                                            else R.drawable.close_icon
                                        )

                                    val delete = SwipeAction(
                                        icon = painterResource(id = R.drawable.icon_delete),
                                        background = Color(0xFFEF3131),
                                        onSwipe = {
                                            currentIndex.intValue = item.id_asesmen!!.toInt()
                                            confirmDelete.value = true }
                                    )
                                    SwipeableActionsBox(
                                        endActions = listOf(delete),
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                    ) {
                                        AssesList(
                                            navController,
                                            item,
                                            item.id_pm,
                                            item.id_pendidikan,
                                            item.id_pekerjaan,
                                            item.id_status_ortu,
                                            changeColor,
                                            changeIcon
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
                            urlFoto.value = it.file_url!!
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

@Composable
private fun AssesList(
    navController: NavController,
    item: VerifAssesmenList,
    id_pm: Int?,
    id_pendidikan: Int?,
    id_pekerjaan: Int?,
    id_status_ortu: Int?,
    changeColor: Color,
    changeIcon: Int
) {
    Surface(
        color = Color(0xFFF8F8F8),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .clickable {
                try {
                    navController.navigate(
                        AppRoute.DetailAssessment.route
                                + "/${if (item.long != null) item.long else "0"}"
                                + "/${if (item.lat != null) item.lat else "0"}"
                                + "/${if (item.id_pm != null) id_pm else "0"}"
                                + "/${if (item.id_asesmen != null) item.id_asesmen else "0"}"
                                + "/${if (item.nama_pendidikan != null) item.nama_pendidikan else "0"}"
                                + "/${if (item.id_pendidikan != null) id_pendidikan else "0"}"
                                + "/${item.nama_sumber_kasus ?: "0"}"
                                + "/${item.id_sumber_kasus ?: "0"}"
                                + "/${if (!item.nama_pekerjaan.isNullOrEmpty()) item.nama_pekerjaan else "0"}"
                                + "/${if (item.id_pekerjaan != null) id_pekerjaan else "0"}"
                                + "/${if (!item.tanggal.isNullOrEmpty()) item.tanggal else "0"}"
                                + "/${if (!item.petugas.isNullOrEmpty()) item.petugas else "0"}"
                                + "/${if (!item.status_dtks.isNullOrEmpty()) item.status_dtks else "0"}"
                                + "/${if (!item.nama_status_ortu.isNullOrEmpty()) item.nama_status_ortu else "0"}"
                                + "/${if (item.id_status_ortu != null) id_status_ortu else "0"}"
                                + "/${if (!item.nama_pekerjaan.isNullOrEmpty()) item.nama_pekerjaan else "0"}"
                                + "/${item.id_kerja_ortu ?: "0"}"
                                + "/${if (!item.nama_tempat_tinggal.isNullOrEmpty()) item.nama_tempat_tinggal else "0"}"
                                + "/${item.id_tempat_tinggal ?: "0"}"
                                + "/${if (!item.nama_bpk.isNullOrEmpty()) item.nama_bpk else "0"}"
                                + "/${if (!item.nama_ibu.isNullOrEmpty()) item.nama_ibu else "0"}"
                                + "/${if (!item.nik_ibu.isNullOrEmpty()) item.nik_ibu else "0"}"
                                + "/${if (!item.nama_wali.isNullOrEmpty()) item.nama_wali else "0"}"
                                + "/${if (!item.penghasilan.isNullOrEmpty()) item.penghasilan else "0"}"
                                + "/${if (!item.catatan.isNullOrEmpty()) item.catatan else "0"}"
                                + "?urlRumah=${if (!item.foto_rumah.isNullOrEmpty()) item.foto_rumah else "0"}"
                                + "?urlFisik=${if (!item.foto_kondisi_fisik.isNullOrEmpty()) item.foto_kondisi_fisik else "0"}"
                                + "?urlKk=${if (!item.foto_kk.isNullOrEmpty()) item.foto_kk else "0"}"
                                + "?urlKtp=${if (!item.foto_ktp.isNullOrEmpty()) item.foto_ktp else "0"}"
                                + "?elap=${if (item.file_lap != null) item.file_lap else "0"}"

                    )
                } catch (e: Exception) {
                    Log.e("ERROR NAVIGATE TO", e.toString())
                }
            }
    ) {
        Row(
            Modifier
                .padding(start = 12.dp)
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
                    text = when (item.flag) {
                        0 -> "Belum Diproses"
                        1 -> "Sudah Diproses"
                        else -> "Closed"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = if (item.tanggal.isNullOrEmpty()) " " else item.tanggal,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 10.sp,
                    color = Color(0xFFC3C3C3)
                )
            }
            Surface(
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .size(width = 50.dp, height = 60.dp),
                color = changeColor
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                ) {
                    Icon(
                        painter = painterResource(id = changeIcon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(14.dp)
                    )
                }
            }
        }
    }
}