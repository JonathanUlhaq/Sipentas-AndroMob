package com.example.sipentas.utils

import android.widget.Toast
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

@Composable
fun DropDownDummy(
    expand: MutableState<Boolean>,
    getString: (String) -> Unit
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = expand.value,
        onDismissRequest = { expand.value = false }
    ) {
        DropdownMenuItem(
            text = { Text("Dummy") },
            onClick = {
                getString.invoke("Dummy")
                expand.value = false
            }
        )
        DropdownMenuItem(
            text = { Text("Daummy") },
            onClick = {
                getString.invoke("Daummy")
                expand.value = false
            }
        )
    }
}