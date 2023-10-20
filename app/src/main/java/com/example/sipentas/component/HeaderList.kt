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
    vm: LoginViewModel
) {


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
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )

                Column {
                    Text(
                        text = "${vm.getName()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${vm.getSatker()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
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