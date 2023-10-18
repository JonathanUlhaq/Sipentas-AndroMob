package com.example.sipentas.widgets

import android.app.DatePickerDialog
import android.content.Context
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sipentas.R
import java.util.Calendar
import java.util.Date

@Composable
fun DatePicker(
    context: Context,
    date: MutableState<String>,
    modifier:Modifier = Modifier,
    boolean: Boolean = true,
    label:String =  "Pilih tanggal lahir"
) {
    val year:Int
    val month:Int
    val day:Int

    val calendar = Calendar.getInstance()

    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = Date()

    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, years: Int, mobths: Int, days: Int ->
            date.value = "$days/${mobths+1}/$years"
        }, year, month, day
    )

    Surface(
        color = Color(0xFFE8E8E8),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { if (boolean) datePickerDialog.show() },
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
                    text = date.value.ifEmpty { label },
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 12.sp,
                    color = Color(0xFF8F8F8F)
                )
            }
            Icon(
                painter = painterResource(id = R.drawable.date_icon),
                contentDescription = null,
                tint = Color(0xFF8F8F8F),
                modifier = Modifier
                    .size(12.dp)
            )
        }
    }

}