package com.company.verbzz_app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.ui.theme.WindowMeasurement

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 measurement: WindowMeasurement,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(10.dp)
            .width(((measurement.smallest/4)*3).dp),
        enabled = !loading && validInputs,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        ),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))
    }

}