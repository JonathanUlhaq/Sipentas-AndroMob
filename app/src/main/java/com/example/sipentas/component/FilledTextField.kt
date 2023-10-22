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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun FilledTextField(
    textString: MutableState<String>,
    label: String,
    minHeight: Dp = TextFieldDefaults.MinHeight,
    textColor:Color = Color(0xFF434343),
    backgroundColor:Color = Color(0xFFE8E8E8),
    labelColor:Color = Color(0xFF8f8f8f),
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    enabled:Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth(),
    trailingIcon: @Composable () -> Unit = {}
) {
    TextField(
        value = textString.value,
        onValueChange = { string ->
            textString.value = string
        },
        modifier = modifier
            .defaultMinSize(minHeight = minHeight)
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
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyMedium,

    )
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NikFilledText(
    textString: MutableState<String>,
    label: String,
    minHeight: Dp = TextFieldDefaults.MinHeight,
    textColor:Color = Color(0xFF434343),
    backgroundColor:Color = Color(0xFFE8E8E8),
    labelColor:Color = Color(0xFF8f8f8f),
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
    enabled:Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth(),
    trailingIcon: @Composable () -> Unit = {}
) {
    val maxLength = 16
    TextField(
        value = textString.value,
        onValueChange = { string ->
            if (string.length <= maxLength)  textString.value = string
        },
        modifier = modifier
            .defaultMinSize(minHeight = minHeight)
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
        enabled = enabled,
        textStyle = MaterialTheme.typography.bodyMedium,

        )
}