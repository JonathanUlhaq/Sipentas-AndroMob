package com.example.sipentas.view.all_in_form

import android.net.Uri
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.DropdownField
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.models.TimelineModel
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.form_assessment.FotoBox
import com.example.sipentas.view.form_assessment.PickPdfFile
import com.example.sipentas.view.form_pm.FormPmViewModel
import com.example.sipentas.widgets.DatePicker
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AllInFormView(
    navController: NavController,
    vm: FormPmViewModel
) {

    val listSesi = listOf(
        TimelineModel(1, "Penerima Manfaat"),
        TimelineModel(2, "Assesment"),
        TimelineModel(3, "Atensi")
    )

    val pagerState = rememberPagerState(pageCount = {
        listSesi.size
    })

    val checkListPm = remember {
        mutableStateOf(false)
    }
    val checkListAssesment = remember {
        mutableStateOf(false)
    }
    val checkListAtensi = remember {
        mutableStateOf(false)
    }
    val currentSelected = remember {
        mutableStateOf(false)
    }
    val currentIndex = remember {
        mutableIntStateOf(0)
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


    val context = LocalContext.current
    if (showPermission.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCamera
        )
    }
    val output: File = getOutputDirectory(context)
    val cameraExecutor = Executors.newSingleThreadExecutor()

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
    }  else if (openCameraRumah.value) {
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
                cameraExecutorKtp.shutdown()
            },
            onError = {

            }
        )
    }
    else {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.primary,
            topBar = {
                Row(
                    Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .padding(top = 18.dp, start = 10.dp, end = 16.dp),
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
                            tint = MaterialTheme.colorScheme.background
                        )
                    }
                    Text(
                        text = "Form Penerima Manfaat",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.background
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
                color = MaterialTheme.colorScheme.primary
            ) {
                Column {
                    Column(
                        Modifier
                            .padding(top = 18.dp, start = 16.dp, bottom = 16.dp)
                    ) {
                        LazyRow(content = {
                            itemsIndexed(listSesi) { index, item ->
                                currentSelected.value =   currentIndex.intValue == index
                                val checkPm by animateColorAsState(
                                    targetValue = if (checkListPm.value)
                                        Color(0xffB3FFB6)
                                    else if (currentSelected.value) MaterialTheme.colorScheme.background
                                    else MaterialTheme.colorScheme.background.copy(0.5f)
                                )
                                val checkAssesment by animateColorAsState(
                                    targetValue = if (checkListAssesment.value)
                                        Color(0xffB3FFB6)
                                    else if (currentSelected.value) MaterialTheme.colorScheme.background
                                    else MaterialTheme.colorScheme.background.copy(0.5f)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable {

                                            runBlocking {
                                                when (index) {
                                                    0 -> {
                                                        if (checkListPm.value) {
                                                            pagerState.scrollToPage(0)
                                                            currentIndex.intValue = index
                                                        }
                                                    }

                                                    1 -> {
                                                        if (checkListAssesment.value || checkListPm.value) {
                                                            pagerState.scrollToPage(1)
                                                            currentIndex.intValue = index
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                ) {
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = when (index) {
                                            0 -> checkPm
                                            else -> checkAssesment
                                        },
                                        modifier = Modifier
                                            .size(20.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .wrapContentSize(Alignment.Center)
                                        ) {
                                            Text(
                                                text = item.nomor_sesi.toString(),
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontSize = 12.sp,
                                                color = Color(0xFf117F90)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = item.sesi,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 12.sp,
                                        color = when (index) {
                                            0 -> checkPm
                                            else -> checkAssesment
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(
                                                when (index) {
                                                    0 -> checkPm
                                                    else -> checkAssesment
                                                }
                                            )
                                            .height(1.dp)
                                            .width(32.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(18.dp))
                            }
                        })
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Surface(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                        shadowElevation = 12.dp,
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HorizontalPager(state = pagerState, userScrollEnabled = false) {
                            when (pagerState.currentPage) {
                                0 -> {
                                    FormAddPm(
                                        showPermission = showPermission,
                                        capturedImagebyUri = capturedImagebyUri,
                                        vm = vm
                                    ) {
                                        Log.d("PIndah ke", pagerState.currentPage.toString())
                                        runBlocking {
                                            checkListPm.value = true
                                            currentIndex.intValue= 1
                                            pagerState.scrollToPage(1)
                                        }
                                    }
                                }

                                1 -> {
                                    FormAssesment(
                                        showPermissionRumah = showPermissionRumah,
                                        showPermissionFisik = showPermissionFisik,
                                        showPermissionKtp = showPermissionKtp,
                                        showPermissionKk = showPermissionKk,
                                        capturedImagebyUriFisik = capturedImagebyUriFisik,
                                        capturedImagebyUriKk = capturedImagebyUriKk,
                                        capturedImagebyUriRumah = capturedImagebyUriRumah,
                                        capturedImagebyUriKtp = capturedImagebyUriKtp
                                    )
                                }
                            }
                        }
                    }

                }

            }
        }
    }


}

@Composable
fun FormAssesment(
    showPermissionRumah:MutableState<Boolean>,
    showPermissionFisik:MutableState<Boolean>,
    showPermissionKtp:MutableState<Boolean>,
    showPermissionKk:MutableState<Boolean>,
    capturedImagebyUriFisik:MutableState<Uri>,
    capturedImagebyUriKk:MutableState<Uri>,
    capturedImagebyUriRumah:MutableState<Uri>,
    capturedImagebyUriKtp:MutableState<Uri>
) {
    val context = LocalContext.current
    val pdfUri = remember {
        mutableStateOf<Uri?>(null)
    }

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
    val pekerjaanOrtuString = remember {
        mutableStateOf("")
    }


    val tempatTinggal = remember {
        mutableStateOf(false)
    }
    val tempatTinggalString = remember {
        mutableStateOf("")
    }

    val tanggalLahir = remember {
        mutableStateOf("")
    }

    Column(
        Modifier
            .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
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
                DropDownDummy(expand = pendidikan) {
                    pendidikanString.value = it
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            DropdownField(
                sumber,
                modifier = Modifier.fillMaxWidth(),
                "Sumber Kasus",
                sumberString.value
            ) {
                DropDownDummy(expand = sumber) {
                    sumberString.value = it
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            Modifier
                .fillMaxWidth()
        ) {
            DropdownField(
                pekerjaan,
                modifier = Modifier.fillMaxWidth(0.5f),
                "Pekerjaan",
                pekerjaanString.value
            ) {
                DropDownDummy(expand = pekerjaan) {
                    pekerjaanString.value = it
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            DropdownField(
                lembaga,
                modifier = Modifier.fillMaxWidth(),
                "Lembaga",
                lembagaString.value
            ) {
                DropDownDummy(expand = lembaga) {
                    lembagaString.value = it
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))
        DatePicker(context = context, date = tanggalLahir, label = "Tanggal Assesment")
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
            DropDownDummy(expand = statusOrtu) {
                statusOrtuString.value = it
            }
        }
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
                DropDownDummy(expand = pekerjaanOrtu) {
                    pekerjaanOrtuString.value = it
                }
            }
            Spacer(modifier = Modifier.width(4.dp))
            DropdownField(
                tempatTinggal,
                modifier = Modifier.fillMaxWidth(),
                "Tempat Tinggal",
                tempatTinggalString.value
            ) {
                DropDownDummy(expand = tempatTinggal) {
                    tempatTinggalString.value = it
                }
            }
        }
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
        FilledTextField(
            textString = nikIbu,
            label = "NIK Ibu",
            imeAction = ImeAction.Default,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
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
        PickPdfFile(urri = pdfUri, onPdfPicked = { uri ->
            pdfUri.value = uri
        })
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

        }
    }
}