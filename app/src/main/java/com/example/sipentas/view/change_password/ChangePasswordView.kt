package com.example.sipentas.view.change_password

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.ButtonPrimary
import com.example.sipentas.component.FilledTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangePasswordView(
    navController:NavController
) {
    
    val currentPassword = remember {
        mutableStateOf("")
    }

   val showPasswordSekarang = remember {
        mutableStateOf(false)
    }
    val animatedPasswordSekarang
            by animateIntAsState(
                targetValue =
                if (showPasswordSekarang.value) R.drawable.hide_icon
                else R.drawable.show_icon
            )


    val newPassword = remember {
        mutableStateOf("")
    }

    val showNewPassword = remember {
        mutableStateOf(false)
    }
    val animatedPasswordNew
            by animateIntAsState(
                targetValue =
                if (showNewPassword.value) R.drawable.hide_icon
                else R.drawable.show_icon
            )
    val confirmPassword = remember {
        mutableStateOf("")
    }

    val showConfirmPassword = remember {
        mutableStateOf(false)
    }
    val animatedPasswordCurrent
            by animateIntAsState(
                targetValue =
                if (showConfirmPassword.value) R.drawable.hide_icon
                else R.drawable.show_icon
            )
    Scaffold (
        topBar = {
            Row(
                Modifier
                    .padding(top = 18.dp, start = 16.dp, end = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back_icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(16.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "Ganti Password",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
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
            ) {
                FilledTextField(
                    textString = currentPassword,
                    label = "Password Sekarang",
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = {
                            showPasswordSekarang.value = !showPasswordSekarang.value
                        }) {
                            Icon(
                                painter = painterResource(id = animatedPasswordSekarang),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    },
                    visualTransformation = if (showPasswordSekarang.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(14.dp))
                FilledTextField(
                    textString = currentPassword,
                    label = "Password Baru",
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = {
                            showNewPassword.value = !showNewPassword.value
                        }) {
                            Icon(
                                painter = painterResource(id = animatedPasswordNew),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    },
                    visualTransformation = if (showNewPassword.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(14.dp))
                FilledTextField(
                    textString = currentPassword,
                    label = "Konfirmasi Password Baru",
                    keyboardType = KeyboardType.Password,
                    trailingIcon = {
                        IconButton(onClick = {
                            showConfirmPassword.value = !showConfirmPassword.value
                        }) {
                            Icon(
                                painter = painterResource(id = animatedPasswordCurrent),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                            )
                        }
                    },
                    visualTransformation = if (showConfirmPassword.value) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(14.dp))
                ButtonPrimary(text = {
                    Text(
                        text = "Ubah Password",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .padding(top = 6.dp, bottom = 6.dp),
                        fontSize = 14.sp
                    )
                }) {

                }
            }
        }
    }

}