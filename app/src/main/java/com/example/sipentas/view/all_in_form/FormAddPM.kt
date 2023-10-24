package com.example.sipentas.view.all_in_form

import android.net.Uri
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.component.NikFilledText
import com.example.sipentas.models.AddPmResponse
import com.example.sipentas.models.PostPmModel
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LocationProviders
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

@Composable
fun FormAddPm(
    showPermission: MutableState<Boolean>,
    capturedImagebyUri: MutableState<Uri?>,
    vm: FormPmViewModel,
    asVm:AssesmenViewModel,
    lat:MutableState<String>,
    long:MutableState<String>,
    url:String = "url",
    onLoading:MutableState<Boolean>,
    onAction: (AddPmResponse) -> Unit

) {


    val context = LocalContext.current

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

    val locationPermission = remember {
        mutableStateOf(false)
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

    val dropCompose = DropdownCompose(vm,asVm)

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
        mutableLongStateOf(0)
    }

    val namaJalan = remember {
        mutableStateOf("")
    }
    val nik = remember {
        mutableStateOf("")
    }

    val formWajib = remember {
        mutableStateOf(false)
    }

    formWajib.value = kategoriPpksString.value.isEmpty() || ragamString.value.isEmpty()
            || nama.value.isEmpty()
            || kelaminString.value.isEmpty()
            || agamaString.value.isEmpty()
            || provinsiString.value.isEmpty()
            || kabupatenString.value.isEmpty()

    if (locationPermission.value) {
        location.LocationPermission(lat = lat, long = long).let {
            location.getLastKnownLocation(success = {

            }) {

            }

        }
    }

    Column(
        Modifier
            .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
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
                        showPermission.value = true
                    }
                    .wrapContentSize(Alignment.Center)

            ) {
                if (capturedImagebyUri.value?.path?.isNotEmpty() == true) {
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

        DropdownField(
            kategoriPpks,
            modifier = Modifier.fillMaxWidth(),
            "Kategori PPKS *",
            kategoriPpksString.value
        ) {
            dropCompose.DropDownPpks(expand = kategoriPpks) { string, id ->
                kategoriPpksString.value = string
                ragamString.value = ""
                kategoriPpksInt.intValue = id
            }

        }
        AnimatedVisibility(visible = kategoriPpksString.value.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "* Kategori PPKS wajib diisi",
                    fontSize = 10.sp,
                    color = Color.Red)
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        DropdownField(
            ragam,
            modifier = Modifier.fillMaxWidth(),
            "Pilih Ragam *",
            ragamString.value,
            isEnable = kategoriPpksInt.intValue != 0
        ) {
            dropCompose.DropDownRagam(expand = ragam) { string, id ->
                ragamString.value = string
                ragamId.intValue = id
            }
        }
        AnimatedVisibility(visible = ragamString.value.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "* Ragam wajib diisi",
                    fontSize = 10.sp,
                    color = Color.Red)
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
        AnimatedVisibility(visible = nama.value.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "* Nama wajib diisi",
                    fontSize = 10.sp,
                    color = Color.Red)
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
                    kelamin,
                    modifier = Modifier.fillMaxWidth(0.5f),
                    "Jenis Kelamin *",
                    kelaminString.value
                ) {
                    dropCompose.DropDownJenisKelamin(expand = kelamin) {
                        kelaminString.value = it
                    }
                }
                AnimatedVisibility(visible = kelaminString.value.isEmpty()) {
                    Column {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "* Jenis Kelamin wajib diisi",
                            fontSize = 10.sp,
                            color = Color.Red)
                    }
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                DropdownField(agama, modifier = Modifier.fillMaxWidth(), "Agama *", agamaString.value) {
                    dropCompose.DropDownAgama(expand = agama) { string, index ->
                        agamaString.value = string
                        agamaId.intValue = index
                    }
                }
                AnimatedVisibility(visible = agamaString.value.isEmpty()) {
                    Column {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "* Agama wajib diisi",
                            fontSize = 10.sp,
                            color = Color.Red)
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
            provinsi,
            modifier = Modifier.fillMaxWidth(),
            "Provinsi *",
            provinsiString.value
        ) {
            dropCompose.DropDownProvinsi(expand = provinsi) { string, int ->
                provinsiString.value = string
                kabupatenString.value = ""
                provinsiInt.intValue = int
            }
        }
        AnimatedVisibility(visible = provinsiString.value.isEmpty()) {
            Column {
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "* Provinsi wajib diisi",
                    fontSize = 10.sp,
                    color = Color.Red)
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
                Text(text = "* Kabupaten wajib diisi",
                    fontSize = 10.sp,
                    color = Color.Red)
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
                kelurahanId.longValue = id.toLong()
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        FilledTextField(
            textString = namaJalan,
            label = "Nama Jalan / Alamat Lengkap",
            imeAction = ImeAction.Default,
            singleLine = false
        )
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
            if (!formWajib.value) {
                locationPermission.value = true
                    runBlocking {
                        vm.addPm(
                            PostPmModel(
                                name = nama.value,
                                date_of_birth = if (tanggalLahir.value.isEmpty()) null else tanggalLahir.value,
                                foto_diri = url,
                                gender = kelaminString.value,
                                kabupaten_id = kabupatenId.intValue,
                                kecamatan_id = if (kecamatanId.intValue.equals(0)) null else kecamatanId.intValue,
                                kelurahan_id = if (kelurahanId.longValue.equals(0L)) null else kelurahanId.longValue,
                                ket_ppks = if (keteranganPPks.value.isEmpty()) null else keteranganPPks.value,
                                kluster_id = kategoriPpksInt.intValue,
                                kode_pos = null,
                                nama_jalan = if (namaJalan.value.isEmpty()) null else namaJalan.value,
                                nik = if (nik.value.isEmpty()) null else nik.value,
                                phone_number = if (nomorHandphone.value.isEmpty()) null else nomorHandphone.value,
                                place_of_birth = if (tempatLahir.value.isEmpty()) null else tempatLahir.value ,
                                provinsi_id = provinsiInt.intValue,
                                ragam_id = ragamId.intValue,
                                religion = if (agamaId.intValue.equals(0))null else agamaId.intValue,
                                satker_id = 9
                            ),
                            loading = onLoading
                            , onError = {locationPermission.value = false}) {
                            onAction.invoke( it)
                            locationPermission.value = false
                        }


                    }

            }

        }
    }
}