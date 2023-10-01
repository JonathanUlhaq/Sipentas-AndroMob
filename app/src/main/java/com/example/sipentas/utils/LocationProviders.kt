package com.example.sipentas.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationProvider
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class LocationProviders(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun getLastKnownLocation(success: (Location?) -> Unit, failure: (Exception) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    success(location)
                }
                .addOnFailureListener { exception ->
                    failure(exception)
                }
        } else {
            failure(SecurityException("Deteksi lokasi tidak diizinkan"))
        }
    }
    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun LocationPermission(
        lat:MutableState<String>,
        long:MutableState<String>,
    ) {
        val coroutine = rememberCoroutineScope()
        val permission = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission() ,
            onResult ={
                    boolean ->
                if (boolean) {
                    Toast.makeText(context,"Akses lokasi diizinkan", Toast.LENGTH_SHORT).show()
                    coroutine.launch {

                    }
                } else {
                    Toast.makeText(context,"Akses lokasi tidak diizinkan", Toast.LENGTH_SHORT).show()
                }
            } )

        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context,Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context,"Akses lokasi diizinkan", Toast.LENGTH_SHORT).show()
            SideEffect {
                    getLastKnownLocation(
                        success = { location ->
                            lat.value = "Latitude: ${location?.latitude}"
                            long.value = "Latitude: ${location?.longitude}"
                            Toast.makeText(context,"Akses lokasi diizinkan ${lat.value +" "+long.value}", Toast.LENGTH_SHORT).show()
                            Log.d("LATITUDE DAN LONGITUDE",lat.value +" "+ long.value)

                        },
                        failure = { exception ->
                        }
                    )

            }
        } else {
            Toast.makeText(context,"Akses tidak lokasi diizinkan", Toast.LENGTH_SHORT).show()
            SideEffect {
                permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }
}

