package com.example.sipentas.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun ButtonPrimary(
    text:@Composable () -> Unit,
    onClick:() -> Unit
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF00A7C0),
            contentColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
       text.invoke()
    }
}

@Composable
fun ButtonSecondary(
    text:@Composable () -> Unit,
    backgroundColor:Color,
    modifier: Modifier,
    onClick:() -> Unit
) {
    Button(
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor ,
            contentColor = Color.White
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        text.invoke()
    }
}

@Composable
fun OutlineButtonPrimary(
    text:@Composable () -> Unit,
    onClick:() -> Unit
) {
    OutlinedButton(
        onClick = {
            onClick.invoke()
        },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        text.invoke()
    }
}

