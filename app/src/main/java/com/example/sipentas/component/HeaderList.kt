package com.example.sipentas.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sipentas.utils.SharePrefs
import com.example.sipentas.view.login.LoginViewModel
import javax.inject.Inject


@Composable
fun HeaderList(
    search: MutableState<String>,
    label: String,
    vm: LoginViewModel,
    list: List<*>?
) {

    vm.getUser()
    val userState = vm.userState.collectAsState().value
    if (!userState.isEmpty()) {
        vm.prefs.saveName(userState[0].username!!)
        vm.prefs.saveSatker(userState[0].sentra!!)
        vm.prefs.saveTipeSatker(userState[0].tipe_user!!)
    }


    Box(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (!list.isNullOrEmpty()) {
                        Text(
                            text = "${list.count()}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }

                if (!userState.isEmpty()) {
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "${userState[0].username}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${userState[0].sentra}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            FilledTextField(
                textString = search, label = "Cari data ...",
                textColor = Color(0xFFFFFFFF),
                backgroundColor = Color(0xFF136C7A).copy(0.3F),
                labelColor = Color(0xFFEAEAEA)
            )

        }

    }
}