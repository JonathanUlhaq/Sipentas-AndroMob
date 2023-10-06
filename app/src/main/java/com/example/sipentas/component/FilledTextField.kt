package com.example.sipentas.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FilledTextField(
    textString: MutableState<String>,
    label: String,
    minHeight: Int = 1,
    textColor:Color = Color(0xFF434343),
    backgroundColor:Color = MaterialTheme.colorScheme.onSurface,
    labelColor:Color = MaterialTheme.colorScheme.surface,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    enabled:Boolean = true,
    trailingIcon: @Composable () -> Unit = {}
) {
    TextField(
        value = textString.value,
        onValueChange = { string ->
            textString.value = string
        },
        modifier = Modifier
            .fillMaxWidth()
         ,
        colors = TextFieldDefaults.textFieldColors(
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            containerColor =backgroundColor ,
            unfocusedLabelColor = labelColor,
            textColor = textColor,
            focusedTrailingIconColor = labelColor,
            unfocusedTrailingIconColor = labelColor,
            cursorColor = labelColor,
            disabledTextColor = textColor,
            disabledLabelColor = labelColor

        ),
        shape = RoundedCornerShape(12.dp),
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium
            )
        },
        trailingIcon = {
            trailingIcon.invoke()
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        visualTransformation = visualTransformation,
        singleLine = singleLine,
        enabled = enabled
    )
}