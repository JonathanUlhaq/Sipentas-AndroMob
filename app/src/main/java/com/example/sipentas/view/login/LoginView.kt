package com.example.sipentas.view.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.navigation.BotNavRoute
import com.example.sipentas.utils.LoadingDialog


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginView(
    navController: NavController,
    loginViewModel: LoginViewModel
) {

    val username = remember {
        mutableStateOf("")
    }

    val password = remember {
        mutableStateOf("")
    }

    val showPassword = remember {
        mutableStateOf(false)
    }

    val scrollState = rememberScrollState()
    val errorVerification =  remember {
        mutableStateOf(false)
    }

    val loading = remember {
        mutableStateOf(false)
    }

    val animatedPassword
            by animateIntAsState(
                targetValue =
                if (showPassword.value) R.drawable.hide_icon
                else R.drawable.show_icon
            )
    LoadingDialog(boolean = loading)
    Scaffold {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize()
                .background(backgroundBrush()),
            color = Color.Transparent
        ) {
            Column {
                Column(
                    Modifier
                        .padding(start = 16.dp, end = 16.dp, top = 18.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.login_image),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                    )
                    Text(
                        text = "Login",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = "Masukkan NIK dan password untuk memasuki aplikasi",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier
                            .width(300.dp),
                        textAlign = TextAlign.Center
                    )
                }

//                Login Form
                Spacer(modifier = Modifier.height(30.dp))
                Surface(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxSize(),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    color = MaterialTheme.colorScheme.background,
                    shadowElevation = 12.dp
                ) {
                    Column(
                        Modifier
                            .padding(top = 20.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxHeight()
                            .verticalScroll(scrollState)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sipentas_logo),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        FilledTextField(
                            username,
                            "NIK",
                            keyboardType = KeyboardType.Number
                        )
                        Spacer(modifier = Modifier.height(15.dp))
                        FilledTextField(
                            textString = password,
                            label = "Password",
                            keyboardType = KeyboardType.Password,
                            trailingIcon = {
                                IconButton(onClick = {
                                    showPassword.value = !showPassword.value
                                }) {
                                    Icon(
                                        painter = painterResource(id = animatedPassword),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(16.dp)
                                    )
                                }
                            },
                            visualTransformation = if (showPassword.value) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        AnimatedVisibility(visible = errorVerification.value) {
                            Column {
                                Spacer(modifier = Modifier.height(15.dp))
                                Text(text = "NIK / Password tidak sesuai",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontSize = 10.sp,
                                    color = Color(0xFFD34B4B))
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        ButtonPrimary(text = {
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(top = 6.dp, bottom = 6.dp),
                                fontSize = 14.sp
                            )
                        }) {
//                            navController.navigate(BotNavRoute.PenerimaManfaat.route)

                            errorVerification.value = false
                            loading.value = true
                            loginViewModel.login(username.value,password.value, onError = {
                                errorVerification.value = true
                                loading.value = false
                            }) {
                                loading.value = false
                                navController.navigate(BotNavRoute.PenerimaManfaat.route)
                            }
                        }

                    }
                }
            }
        }
    }
}




fun backgroundBrush(): Brush =
    Brush.radialGradient(listOf(Color(0xFF30B7CC), Color(0xFF61E0F4)))

