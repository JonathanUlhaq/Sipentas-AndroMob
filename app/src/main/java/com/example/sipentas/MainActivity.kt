package com.example.sipentas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.sipentas.navigation.BottomNav
import com.example.sipentas.navigation.NavigationAdapter
import com.example.sipentas.ui.theme.SipentasTheme
import com.example.sipentas.view.form.FormView
import com.example.sipentas.view.login.LoginView
import com.google.android.gms.location.LocationCallback
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
