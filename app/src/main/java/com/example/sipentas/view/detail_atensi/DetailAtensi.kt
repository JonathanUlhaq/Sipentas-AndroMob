package com.example.sipentas.view.detail_atensi

import android.net.Uri
import android.widget.Space
import android.widget.Toast
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
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
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.form_assessment.PickPdfFile
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
fun DetailAtens(
    navController: NavController,
    vm:DetailAtensiViewModel,
    idPm:String,
    idAssesmen:String,
    penerimaNama:String,
    nik:String,
    petugas:String

) {

    vm.getJenisAtensi()
    vm.getPendekatanAtensi()

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
        mutableStateOf("")
    }
    val idJenis = remember {
        mutableIntStateOf(0)
    }
    val nilai = remember {
        mutableStateOf("")
    }
    val tanggalAtensi = remember {
        mutableStateOf("")
    }
    val penerima = remember {
        mutableStateOf("")
    }
    val addForm = remember {
        mutableStateOf(false)
    }

    val jenisAtens = remember {
        mutableStateOf(false)
    }
    val jenisAtensString = remember {
        mutableStateOf("")
    }
    val pendekatanAtens = remember {
        mutableStateOf(false)
    }
    val pendekatanAtensString = remember {
        mutableStateOf("")
    }
    val idPendekatan = remember {
        mutableIntStateOf(0)
    }
    val urlAtensi = remember {
        mutableStateOf("")
    }

    val context = LocalContext.current
    val dropDownAtensi = DropDownAtensi(vm)
    if (showPermission.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCamera
        )
    }
    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()



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
    } else {
        Scaffold {
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
                                    Text(
                                        text = "Form",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.Transparent
                                    )
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
                                        text = "$penerimaNama",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Text(
                                        text = "$nik",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 16.sp,
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
                                            text = "26 September 2023",
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
                                            text = "Petugas: $petugas",
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
                            itemsIndexed(atensi) {
                                    index, item ->
                                Column {
                                    Divider()
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Surface(
                                            shape = RoundedCornerShape(4.dp),
                                            modifier = Modifier
                                                .width(82.dp)
                                                .height(45.dp)
                                        ) {
                                            Image(painter = painterResource(id = R.drawable.gambar_person),
                                                contentDescription = null,
                                                contentScale = ContentScale.Crop)
                                        }
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Column {
                                            Text(text = item,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontSize = 12.sp,
                                                color = Color.Black)
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Text(text = member[index],
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontSize = 10.sp,
                                                color = Color(0xFFC3C3C3)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))
                                    Divider()
                                    Spacer(modifier = Modifier.height(12.dp))
                                }
                            }
                            item {
                                AnimatedVisibility(visible = !addForm.value) {
                                    ButtonPrimary(text = {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Tambah Atensi",
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .padding(top = 6.dp, bottom = 6.dp),
                                                fontSize = 14.sp
                                            )
                                        }
                                    }) {
                                        addForm.value = true
                                    }
                                }
                                AnimatedVisibility(visible = addForm.value) {
                                    Column {
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
                                                            text = "Foto Atensi",
                                                            style = MaterialTheme.typography.titleMedium,
                                                            color = Color.White
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(14.dp))
                                        DropdownField(kategoriPpks = jenisAtens ,
                                            label = "Jenis Atensi" ,
                                            stringText = jenisAtensString.value,
                                            modifier = Modifier
                                                .fillMaxWidth()) {
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
                                                .fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(14.dp))
                                        FilledTextField(
                                            textString = nilai,
                                            label = "Nilai",
                                            imeAction = ImeAction.Default,
                                            singleLine = true,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            keyboardType = KeyboardType.Number
                                        )
                                        Spacer(modifier = Modifier.height(14.dp))
                                        DatePicker(context = context, date = tanggalAtensi, label = "Tanggal Atensi")
                                        Spacer(modifier = Modifier.height(14.dp))
                                        DropdownField(kategoriPpks = pendekatanAtens ,
                                            label = "Pendekatan Atensi" ,
                                            stringText = pendekatanAtensString.value,
                                            modifier = Modifier
                                                .fillMaxWidth() ) {
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
                                                .fillMaxWidth()
                                        )
                                        Spacer(modifier = Modifier.height(32.dp))
                                        ButtonPrimary(text = {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = "Tambah Atensi",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    modifier = Modifier
                                                        .padding(top = 6.dp, bottom = 6.dp),
                                                    fontSize = 14.sp
                                                )
                                            }
                                        }) {
                                            vm.addAtensi(
                                                AtensiBody(
                                                    foto = urlAtensi.value,
                                                    id_assesment = idAssesmen.toInt(),
                                                    id_jenis =idJenis.intValue,
                                                    id_pendekatan = idPendekatan.intValue,
                                                    id_pm = idPm.toInt(),
                                                    jenis = jenis.value,
                                                    lat = "0",
                                                    long = "0",
                                                    nilai = nilai.value.toLong(),
                                                    penerima = penerima.value,
                                                    tanggal = tanggalAtensi.value
                                                )
                                            ) {
                                                Toast.makeText(context,"Atensi berhasil ditambah",Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(14.dp))

                                    }
                                }
                            }
                        })

                    }
                }

            }
        }
    }



}