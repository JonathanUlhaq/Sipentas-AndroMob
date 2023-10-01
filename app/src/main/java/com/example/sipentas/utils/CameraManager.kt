package com.example.sipentas.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.view.OrientationEventListener
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Bottom
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.sipentas.R
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private suspend fun Context.getCameraProvider():ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            },ContextCompat.getMainExecutor(this))
        }
    }

@Composable
fun RequestCameraPermission(
    context: Context,
    openCamera:MutableState<Boolean>,
    changeContent:MutableState<Boolean> = mutableStateOf(false)
) {
    val coroutine = rememberCoroutineScope()
    val permission = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission() ,
        onResult ={
            boolean ->
            if (boolean) {
                Toast.makeText(context,"Akses kamera diizinkan",Toast.LENGTH_SHORT).show()
                coroutine.launch {
                    openCamera.value = true
                    changeContent.value = true
                }
            } else {
                Toast.makeText(context,"Akses kamera tidak diizinkan",Toast.LENGTH_SHORT).show()
            }
        } )

    val permissionCheckResult =
        ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)
    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
        Toast.makeText(context,"Akses kamera diizinkan",Toast.LENGTH_SHORT).show()
        SideEffect {
            openCamera.value = true
            changeContent.value = true
        }
    } else {
        Toast.makeText(context,"Akses tidak kamera diizinkan",Toast.LENGTH_SHORT).show()
        SideEffect {
            permission.launch(Manifest.permission.CAMERA)
        }
    }
}

fun getOutputDirectory(context: Context):File {
    val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
        file ->
        File(file,context.resources.getString(R.string.app_name)).apply { mkdirs() }
    }
    return if (mediaDir != null && mediaDir.exists()) mediaDir else context.filesDir
}

private fun takePhoto(
    fileName:String,
    imageCapture:ImageCapture,
    outputDirectory:File,
    executor:Executor,
    onImageCapture:(Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(
        outputDirectory,
        SimpleDateFormat(fileName,Locale.TAIWAN).format(System.currentTimeMillis())+".png"
    )
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        executor,
        object : ImageCapture.OnImageSavedCallback{
            override fun onError(exception: ImageCaptureException) {
                onError(exception)
            }

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                onImageCapture(savedUri)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraView(
    outputDirectory:File,
    executor:Executor,
    closeCamera:() -> Unit,
    onImageCapture:(Uri) -> Unit,
    onError:(ImageCaptureException) -> Unit
) {
    val cameraFace = CameraSelector.LENS_FACING_BACK
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val preview = Preview.Builder().build()

    val previewView = remember {
        PreviewView(context)
    }
    val imageCapture:ImageCapture = remember {
        ImageCapture.Builder().build()
    }

    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(cameraFace)
        .build()

    LaunchedEffect(cameraFace) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageCapture
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    val orientation by lazy {
        object : OrientationEventListener(context) {
            override fun onOrientationChanged(orientation: Int) {
                if (orientation == ORIENTATION_UNKNOWN) {
                    return
                }

                val rotation = when (orientation) {
                    in 45 until 135 -> android.view.Surface.ROTATION_270
                    in 135 until 225 -> android.view.Surface.ROTATION_180
                    in 225 until 315 -> android.view.Surface.ROTATION_90
                    else -> android.view.Surface.ROTATION_0
                }
                imageCapture.targetRotation = rotation
            }
        }
    }

    orientation.enable()

    Box(modifier = Modifier
        .fillMaxSize()) {
        AndroidView(factory = {previewView},
            modifier = Modifier
                .fillMaxSize())

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Start)
            .padding(16.dp)) {
            Surface(shape = CircleShape,
                color = MaterialTheme.colorScheme.background,
                onClick = {
                    closeCamera.invoke()
                    orientation.disable()
                },
                modifier = Modifier
                    .size(30.dp)) {
                    Icon(painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(16.dp)
                            .padding(8.dp),
                        tint = MaterialTheme.colorScheme.primary)
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(CenterHorizontally)
            .fillMaxHeight()
            .wrapContentHeight(Bottom)
            .padding(16.dp)) {
            Surface(shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.background,
                onClick = {
                    takePhoto(
                        "yyyy-MM-dd-HH-mm-ss-SSS",
                        imageCapture = imageCapture,
                        outputDirectory,
                        executor,
                        onImageCapture,
                        onError
                    )
                    orientation.disable()
                },
                modifier = Modifier) {
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .padding(12.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.camera_icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(16.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Foto Bukti",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }

}

