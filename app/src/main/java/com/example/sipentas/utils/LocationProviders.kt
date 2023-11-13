package com.example.sipentas.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationProvider
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class LocationProviders(private val context: Context) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    lateinit var locationCallback: LocationCallback

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
    @SuppressLint("MissingPermission")
    @Composable
    fun LocationPermission(
        lat: MutableState<String>,
        long: MutableState<String>,
    ) {


        val coroutine = rememberCoroutineScope()
        val permission = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { boolean ->
                if (boolean) {
//                    Toast.makeText(context,"Akses lokasi diizinkan", Toast.LENGTH_SHORT).show()
                    coroutine.launch {

                    }
                } else {
//                    Toast.makeText(context,"Akses lokasi tidak diizinkan", Toast.LENGTH_SHORT).show()
                }
            })

        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            LaunchedEffect(key2 = fusedLocationClient, key1 = Unit) {
                getLastKnownLocation(
                    success = { location ->

                        locationCallback = object : LocationCallback() {
                            override fun onLocationResult(result: LocationResult) {
                                for (location in result.locations) {
                                    lat.value = location.latitude.toString()
                                    long.value = location.longitude.toString()
                                }
                            }

                        }
                        locationUpdate()
                        Toast.makeText(context,"Akses lokasi diizinkan ${lat.value +" "+long.value}", Toast.LENGTH_SHORT).show()
                        Log.d("LATITUDE DAN LONGITUDE", lat.value + " " + long.value)

                    },
                    failure = { exception ->
                        Toast.makeText(context,"Akses Ditolak ${exception}", Toast.LENGTH_SHORT).show()

                    }
                )

            }
        } else {
            SideEffect {
                permission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun locationUpdate() {
        locationCallback.let {
            //An encapsulation of various parameters for requesting
            // location through FusedLocationProviderClient.
            val locationRequest: LocationRequest =
                LocationRequest.create().apply {
                    interval = TimeUnit.SECONDS.toMillis(60)
                    fastestInterval = TimeUnit.SECONDS.toMillis(30)
                    maxWaitTime = TimeUnit.MINUTES.toMillis(2)
                    priority = LocationRequest.PRIORITY_HIGH_ACCURACY
                }
            //use FusedLocationProviderClient to request location update
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }

    }
}



