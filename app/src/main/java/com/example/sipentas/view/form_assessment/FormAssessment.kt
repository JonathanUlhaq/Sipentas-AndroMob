package com.example.sipentas.view.form_assessment

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.sipentas.R
import com.example.sipentas.component.DropdownField
import com.example.sipentas.utils.CameraView
import com.example.sipentas.utils.DropDownDummy
import com.example.sipentas.utils.RequestCameraPermission
import com.example.sipentas.utils.getOutputDirectory
import com.rizzi.bouquet.ResourceType
import com.rizzi.bouquet.VerticalPDFReader
import com.rizzi.bouquet.rememberVerticalPdfReaderState
import java.io.File
import java.util.concurrent.Executors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormAssessment(
    navController: NavController
) {

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



    if (showPermissionKtp.value) {
        RequestCameraPermission(
            context = context,
            openCamera = openCameraKtp
        )
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
    } else {
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
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Text(
                        text = "Form Assessment",
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
                    if (pdfUri.value != null) {
                        val pdfState = rememberVerticalPdfReaderState(
                            resource = ResourceType.Local(pdfUri.value!!),
                            isZoomEnable = true
                        )
                        VerticalPDFReader(
                            state = pdfState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .background(color = Color.Gray)
                        )
                    }

                }
            }
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
    label: String
) {
    Surface(
        modifier
            .height(180.dp),
        color = MaterialTheme.colorScheme.secondary,
        shape = RoundedCornerShape(16.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    showPermissionRumah.value = true
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