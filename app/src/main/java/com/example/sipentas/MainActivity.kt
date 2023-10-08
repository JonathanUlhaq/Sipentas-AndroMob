package com.example.sipentas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.sipentas.navigation.BottomNav
import com.example.sipentas.navigation.NavigationAdapter
import com.example.sipentas.ui.theme.SipentasTheme
import com.example.sipentas.utils.LocationProviders
import com.example.sipentas.view.form_pm.isLocationEnabled
import com.example.sipentas.view.form_pm.showGPSDisabledAlert
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
        @OptIn(ExperimentalMaterial3Api::class)
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SipentasTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val showBottomBar = remember {
                        mutableStateOf(false)
                    }
                    val context = LocalContext.current
                    val location = LocationProviders(context)

                    LaunchedEffect(Unit) {
                        if (!isLocationEnabled(context)) {
                            showGPSDisabledAlert(context)
                        }
                    }

                    Scaffold (
                        bottomBar = {
                            if (showBottomBar.value) {
                                BottomNav(navController = navController)
                            }
                        }
                            ) {
                        Surface(
                            Modifier
                                .padding(it)
                                .fillMaxSize(),
                            contentColor = Color.Transparent) {
                            NavigationAdapter(navController,showBottomBar)
                        }
                    }
                }
            }
        }
    }

}
