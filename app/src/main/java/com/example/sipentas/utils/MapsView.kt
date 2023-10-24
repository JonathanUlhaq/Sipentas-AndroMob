package com.example.sipentas.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapsView(lat:Double,long:Double) {
    val currentPosition = LatLng(lat, long)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPosition, 10f)
    }
  Surface(
      shape = RoundedCornerShape(12.dp)
  ) {
      GoogleMap(
          modifier = Modifier
              .fillMaxWidth()
              .height(100.dp),
          cameraPositionState = cameraPositionState
      ) {
          Marker(
              state = rememberMarkerState(position = currentPosition),
              title = "Posisi Sekarang",
              icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)
          )
      }
  }
}