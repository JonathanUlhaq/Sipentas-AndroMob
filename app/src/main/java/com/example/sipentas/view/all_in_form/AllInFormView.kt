package com.example.sipentas.view.all_in_form

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import com.example.sipentas.models.AtensiBody
import com.example.sipentas.models.TimelineModel
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownAtensi
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.DropdownCompose
import com.example.sipentas.utils.LoadingDialog
import com.example.sipentas.utils.MapsView
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.example.sipentas.view.assessment.AssesmenViewModel
import com.example.sipentas.view.detail_atensi.DetailAtensiViewModel
import com.example.sipentas.view.form_assessment.FotoBox
import com.example.sipentas.view.form_assessment.PickPdfFile
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AllInFormView(
    navController: NavController,
    vm: FormPmViewModel,
    asVm: AssesmenViewModel,
    atensiVm:DetailAtensiViewModel
) {

    asVm.getPendidikan()
    asVm.getSumber()
    asVm.getPekerjaan()
    asVm.getStatusOrtu()
    asVm.getTempatTinggal()

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

    if (showPermissionKtp.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraKtp
        )
    }
    val urlFisik = remember {
        mutableStateOf("")
    }
    val urlKk = remember {
        mutableStateOf("url")
    }
    val urlAtensi = remember {
        mutableStateOf("url")
    }
    val urlKtp = remember {
        mutableStateOf("url")
    }
    val urlRumah = remember {
        mutableStateOf("url")
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

    val showPermissionAtensi = remember {
        mutableStateOf(false)
    }
    val openCameraAtensi = remember {
        mutableStateOf(false)
    }
    val capturedImagebyUriAtensi = remember {
        mutableStateOf(Uri.EMPTY)
    }

    val idAssesment = remember {
        mutableIntStateOf(0)
    }
    val onLoadingPm = remember {
        mutableStateOf(false)
    }
    val onLoadingAssesmen = remember {
        mutableStateOf(false)
    }
    val onLoadingAtensi = remember {
        mutableStateOf(false)
    }

    LoadingDialog(boolean = onLoadingAtensi)
    LoadingDialog(boolean = onLoadingPm)
    LoadingDialog(boolean = onLoadingAssesmen)

val pmAssesmen = remember {
    mutableStateOf("url")
}
    if (showPermissionAtensi.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraAtensi
        )
    }
    val outputAtensi: File = getOutputDirectory(context)
    val cameraExecutorAtensi = Executors.newSingleThreadExecutor()

    Box {

        Scaffold(
            containerColor = Color(0xFF00A7C0),
            topBar = {
                Row(
                    Modifier
                        .background(Color(0xFF00A7C0))
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
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Form Penerima Manfaat",
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
        ) {
            Surface(
                Modifier
                    .padding(it)
                    .fillMaxSize(),
                color = Color(0xFF00A7C0)
            ) {
                Column {
                    Column(
                        Modifier
                            .padding(top = 18.dp, start = 16.dp, bottom = 16.dp)
                    ) {
                        Spacer(modifier = Modifier.height(14.dp))
                        AnimatedVisibility(visible = lat.value.isNotEmpty() &&
                                long.value.isNotEmpty() && lat.value != "null" &&
                                long.value != "null") {
                            Box(modifier = Modifier.padding(end = 16.dp)) {
                                MapsView(lat.value.toDouble(),long.value.toDouble())
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        LazyRow(content = {
                            itemsIndexed(listSesi) { index, item ->
                                currentSelected.value = currentIndex.intValue == index
                                val checkPm by animateColorAsState(
                                    targetValue = if (checkListPm.value)
                                        Color(0xffB3FFB6)
                                    else if (currentSelected.value) Color.White
                                    else Color.White.copy(0.5f)
                                )
                                val checkAssesment by animateColorAsState(
                                    targetValue = if (checkListAssesment.value)
                                        Color(0xffB3FFB6)
                                    else if (currentSelected.value) Color.White
                                    else Color.White.copy(0.5f)
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .clickable {

//                                            runBlocking {
//                                                when (index) {
//                                                    0 -> {
//                                                        if (checkListPm.value) {
//                                                            pagerState.scrollToPage(0)
//                                                            currentIndex.intValue = index
//                                                        }
//                                                    }
//
//                                                    1 -> {
//                                                        if (checkListAssesment.value || checkListPm.value) {
//                                                            pagerState.scrollToPage(1)
//                                                            currentIndex.intValue = index
//                                                        }
//                                                    }
//                                                }
//
//                                            }
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
                        color = Color.White
                    ) {
                        HorizontalPager(state = pagerState, userScrollEnabled = false) {
                            when (pagerState.currentPage) {
                                0 -> {
                                    FormAddPm(
                                        showPermission = showPermission,
                                        capturedImagebyUri = capturedImagebyUri,
                                        vm = vm,
                                        asVm = asVm,
                                        lat = lat,
                                        long = long,
                                        url = pmAssesmen.value,
                                        onLoading = onLoadingPm
                                    ) {
                                        Toast.makeText(context,"Penambahan PM berhasil",Toast.LENGTH_SHORT).show()
                                        idUser.value = it.data?.id.toString()
                                        runBlocking {
                                            checkListPm.value = true
                                            currentIndex.intValue = 1
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
                                        capturedImagebyUriKtp = capturedImagebyUriKtp,
                                        vm = vm,
                                        asVm = asVm,
                                        urlRumah = urlRumah.value,
                                        urlFisik = urlFisik.value,
                                        urlKk = urlKk.value,
                                        urlKtp = urlKtp.value,
                                        idUser = idUser.value,
                                        lat = lat.value,
                                        long = long.value,
                                        onLoadingAssesmen = onLoadingAssesmen
                                    ) {
                                        Toast.makeText(context,"Penambahan assesmen berhasil",Toast.LENGTH_SHORT).show()
                                        idAssesment.intValue = it
                                        runBlocking {
                                            checkListAssesment.value = true
                                            currentIndex.intValue = 2
                                            pagerState.scrollToPage(2)
                                        }
                                    }
                                }
                                else -> {
                                    AtensiForm(
                                        vm =atensiVm ,
                                        showPermission = showPermissionAtensi ,
                                        capturedImagebyUri = capturedImagebyUriAtensi,
                                        context = context,
                                        idUser = idUser.value,
                                        idAssesmen = idAssesment.value,
                                        urlAtensi = urlAtensi.value,
                                        lat = lat.value,
                                        long = long.value,
                                        onLoadingAtensi = onLoadingAtensi
                                    ) {
                                        Toast.makeText(context,"Penambahan atensi berhasil",Toast.LENGTH_SHORT).show()
                                        navController.popBackStack()
                                    }
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
                        asVm.addAssesmenFile(gambar) {
                            pmAssesmen.value = it.file_url!!
                        }
                    }
                    cameraExecutor.shutdown()
                },
                onError = {

                }
            )
        } else if (openCameraRumah.value) {
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
        } else if (openCameraAtensi.value) {
            CameraView(
                outputDirectory = outputAtensi,
                executor = cameraExecutorAtensi,
                closeCamera = {
                    showPermissionAtensi.value = false
                    openCameraAtensi.value = false
                    runBlocking {
                        val file = File(capturedImagebyUriAtensi.value?.path!!)
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
                            urlAtensi.value = it.file_url!!
                        }
                    }
                    cameraExecutorAtensi.shutdown()
                },
                onImageCapture = { uri ->
                    capturedImagebyUriAtensi.value = uri
                    showPermissionAtensi.value = false
                    openCameraAtensi.value = false
                    cameraExecutorAtensi.shutdown()
                },
                onError = {

                }
            )
        }
    }
}

@Composable
fun AtensiForm(
    vm:DetailAtensiViewModel,
    showPermission:MutableState<Boolean>,
    capturedImagebyUri:MutableState<Uri>,
    context:Context,
    idUser: String,
    idAssesmen:Int,
    urlAtensi:String,
    lat:String,
    long:String,
    onLoadingAtensi: MutableState<Boolean>,
    onClick:() -> Unit
) {
    vm.getJenisAtensi()
    vm.getPendekatanAtensi()
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
    val dropDownAtensi = DropDownAtensi(vm)
    val scrollState = rememberScrollState()
    Column (
        Modifier
            .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
            ) {
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
                    foto = urlAtensi,
                    id_assesment = idAssesmen.toInt(),
                    id_jenis =idJenis.intValue,
                    id_pendekatan = idPendekatan.intValue,
                    id_pm = idUser.toInt(),
                    jenis = jenis.value,
                    lat = lat,
                    long = long,
                    nilai = if (nilai.value.isEmpty()) "0".toLong() else nilai.value.toLong(),
                    penerima = penerima.value,
                    tanggal = tanggalAtensi.value,
                ),
                onLoadingAtensi = onLoadingAtensi
            ) {
                onClick.invoke()

            }
        }
        Spacer(modifier = Modifier.height(14.dp))

    }
}
@Composable
fun FormAssesment(
    showPermissionRumah: MutableState<Boolean>,
    showPermissionFisik: MutableState<Boolean>,
    showPermissionKtp: MutableState<Boolean>,
    showPermissionKk: MutableState<Boolean>,
    capturedImagebyUriFisik: MutableState<Uri>,
    capturedImagebyUriKk: MutableState<Uri>,
    capturedImagebyUriRumah: MutableState<Uri>,
    capturedImagebyUriKtp: MutableState<Uri>,
    onLoadingAssesmen:MutableState<Boolean>,
    vm: FormPmViewModel,
    asVm: AssesmenViewModel,
    urlFisik:String,
    urlKk:String,
    urlRumah:String,
    urlKtp:String,
    idUser:String,
    lat:String,
    long:String,
    onSuccess:(Int) -> Unit
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
    val pendidikanInt = remember {
        mutableIntStateOf(0)
    }

    val sumber = remember {
        mutableStateOf(false)
    }

    val sumberString = remember {
        mutableStateOf("")
    }
    val sumberInt = remember {
        mutableIntStateOf(0)
    }

    val pekerjaan = remember {
        mutableStateOf(false)
    }
    val pekerjaanString = remember {
        mutableStateOf("")
    }
    val pekerjaanInt = remember {
        mutableIntStateOf(0)
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
    val statusInt = remember {
        mutableIntStateOf(0)
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
    val pekerjaanOrtuInt = remember {
        mutableIntStateOf(0)
    }

    val tempatTinggal = remember {
        mutableStateOf(false)
    }
    val tempatTinggalString = remember {
        mutableStateOf("")
    }
    val tempatTinggalInt = remember {
        mutableIntStateOf(0)
    }
    val tanggalLahir = remember {
        mutableStateOf("")
    }

    val formWajib = remember {
        mutableStateOf(false)
    }

    formWajib.value = sumberString.value.isEmpty()
    val dropDownCompose = DropdownCompose(vm, asVm)

    Column(
        Modifier
            .padding(top = 18.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
            .verticalScroll(scrollState)
    ) {
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
        FilledTextField(
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

            if (!formWajib.value
            ) {
                asVm.addAssesmen(
                    AssesmentBody(
                        catatan = catatan.value,
                        foto_kk = urlKk,
                        foto_kondisi_fisik = urlFisik,
                        foto_ktp = urlKtp,
                        foto_rumah = urlRumah,
                        id_kerja_ortu = if (pekerjaanOrtuInt.intValue.equals(0)) null else pekerjaanOrtuInt.intValue,
                        id_lembaga = 1,
                        id_pekerjaan = if (pekerjaanInt.intValue.equals(0)) null else pekerjaanInt.intValue,
                        id_pendidikan = if (pendidikanInt.intValue.equals(0)) null else pendidikanInt.intValue,
                        id_pm = idUser.toInt(),
                        id_status_ortu = if (statusInt.intValue.equals(0)) null else statusInt.intValue,
                        id_sumber_kasus = sumberInt.intValue,
                        id_tempat_tgl = if (tempatTinggalInt.intValue.equals(0)) null else tempatTinggalInt.intValue,
                        lat = lat,
                        long = long,
                        nama_bpk = if (namaBapak.value.isEmpty()) null else namaBapak.value,
                        nama_ibu = if (namaIbu.value.isEmpty()) null else namaIbu.value,
                        nama_wali = if (namaWali.value.isEmpty()) null else namaWali.value,
                        nik_ibu = if (nikIbu.value.isEmpty()) null else nikIbu.value,
                        petugas = if (petugas.value.isEmpty()) null else petugas.value,
                        status_dtks = if (dtks.value.isEmpty()) null else dtks.value ,
                        tanggal = tanggalLahir.value,
                        flag = 1
                    ),
                    onLoadingAssesmen = onLoadingAssesmen
                ) {
                    onSuccess.invoke(it)
                }

            }
        }
    }
}