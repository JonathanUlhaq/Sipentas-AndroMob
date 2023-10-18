package com.example.sipentas.view.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sipentas.R
import com.example.sipentas.component.FilledTextField
import com.example.sipentas.navigation.AppRoute
import com.example.sipentas.repositories.ProfileRepository
import com.example.sipentas.utils.ComposeDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
    navController: NavController,
    vm:ProfileViewModel
) {

    val name = remember {
        mutableStateOf("")
    }
    val logoutConfirm = remember {
        mutableStateOf(false)
    }

    ComposeDialog(title = "Logout", desc = "Apakah anda ingin logout ?" , boolean = logoutConfirm ) {
    vm.logout(onFailure = {}, onSuccess = {
        navController.navigate(AppRoute.Login.route)
    })
    }

    Scaffold {
        Surface(
            Modifier
                .padding(it)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column (
                                   ) {
                Box {
                    Surface(
                        Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .fillMaxWidth()
                                .wrapContentWidth(CenterHorizontally)
                        ) {
                            Text(
                                text = "Profile",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.background
                            )
                        }
                    }

                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(CenterHorizontally)
                        .offset(y = 72.dp)) {
                        Surface(
                            shape = CircleShape,
                            color = MaterialTheme.colorScheme.background,
                            shadowElevation = 0.dp
                        ) {
                            Icon(painter = painterResource(id = R.drawable.profile_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .size(62.dp))
                        }
                    }
                }
                Column(
                    Modifier
                        .padding(16.dp)

                ) {
                    Spacer(modifier = Modifier.height(34.dp))
                    FilledTextField(textString = name , label = "Nama" )
                    Spacer(modifier = Modifier.height(14.dp))
                    ButtonSecondary(
                        MaterialTheme.colorScheme.primary,
                        R.drawable.arrow_right,
                        "Ubah Password"
                    ) {
                        navController.navigate(AppRoute.UpdatePassword.route)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    ButtonSecondary(
                        Color(0xFFD34B4B),
                        R.drawable.logout_icon,
                        "Logout"
                    ) {
                        logoutConfirm.value = true
                    }
                }

            }
        }
    }
}

@Composable
private fun ButtonSecondary(
    color:Color,
    icon:Int,
    label:String,
    onClick:() -> Unit
) {
    Surface(
        color = color,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke()
            }
    ) {
        Row(
            Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 18.dp,
                    bottom = 18.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.background
            )
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .size(12.dp)
            )
        }
    }
}