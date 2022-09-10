package com.company.verbzz_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.R
import com.company.verbzz_app.ui.theme.WindowMeasurement

@Composable
fun SplashBackground(measurement: WindowMeasurement) {
    Column(
        modifier = Modifier.padding(1.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier.size(
                if(measurement.isPortrait) (measurement.height/4).dp
                else (measurement.height/3).dp
            ),
            painter = painterResource(id = R.drawable.ic_nicubunu_honey),
            contentDescription = stringResource(id = R.string.description)
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = stringResource(id = R.string.title),
            style = MaterialTheme.typography.h2,
            color = MaterialTheme.colors.primaryVariant)
    }
}