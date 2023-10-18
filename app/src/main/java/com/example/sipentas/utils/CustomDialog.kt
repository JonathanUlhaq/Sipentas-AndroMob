package com.example.sipentas.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.sipentas.component.ButtonSecondary


@Composable
fun ComposeDialog(
    title: String ,
    desc: String ,
    boolean: MutableState<Boolean>,
    confirm: () -> Unit
) {
    if (boolean.value) {
        Dialog(onDismissRequest = {
            boolean.value = false
        }) {
            CustomDialog(title = title,
                desc = desc ,
                cancel = { boolean.value = false }) {
                confirm.invoke()
            }
        }
    }
}

@Composable
fun CustomDialog(
    title:String,
    desc:String,
    cancel:() -> Unit,
    confirm:() -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp, start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = desc,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface,
                textAlign = TextAlign.Center
            )

        }
        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            ButtonSecondary(text = {
                Text(text = "Iya",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 12.sp)
            }, backgroundColor = Color(0xFFD34B4B) , modifier = Modifier
                .fillMaxWidth(0.5f)) {
                confirm.invoke()
            }
            Spacer(modifier = Modifier.width(4.dp))
            ButtonSecondary(text = {
                Text(text = "Tidak",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 12.sp)
            }, backgroundColor = MaterialTheme.colorScheme.primary , modifier = Modifier
                .fillMaxWidth()) {
                cancel.invoke()

            }
        }
    }
}