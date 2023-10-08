package com.example.sipentas.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipentas.R
import com.example.sipentas.utils.DropDownDummy

@Composable
fun DropdownField(
    kategoriPpks: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    label:String,
    stringText:String,
    isEnable:Boolean = true,
    dropDownMenu:@Composable () -> Unit) {

    val rotateFloat by animateFloatAsState(targetValue = if (kategoriPpks.value) 0f else -90f)
    val icon by animateIntAsState(targetValue = if (isEnable) R.drawable.dropdown else R.drawable.larangan_icon)
    val colorSelect by animateColorAsState(targetValue = if (isEnable) Color(0xFF585757) else Color(0xFF585757).copy(0.5f))

    Box {
        Surface(
            color = Color(0xFFE8E8E8),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable {
                    if (isEnable) {
                        kategoriPpks.value = true
                    }
                }
        ) {
            Row(
                modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp,
                        top = 14.dp,
                        bottom = 14.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = 12.sp,
                        color = Color(0xFF8F8F8F)
                    )
                    AnimatedVisibility(visible = stringText.isNotEmpty()) {
                        Text(
                            text = stringText,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            color = Color(0xFF434343)
                        )
                    }
                }
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = colorSelect,
                    modifier = Modifier
                        .size(12.dp)
                        .rotate(rotateFloat)
                )
            }
        }
            dropDownMenu.invoke()
    }
}