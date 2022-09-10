package com.company.verbzz_app.screens.in_app_purchases

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.MainBottomBar
import com.company.verbzz_app.ui.theme.WindowMeasurement

@ExperimentalComposeUiApi
@Composable
fun ShoppingScreen(navController: NavController, measurement: WindowMeasurement) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(navController = navController, measurement = measurement)
        }
    ) {
        BaseBackground(
            measurement = measurement,
            title = stringResource(id = R.string.Purchases),
            secondIcon = false,
            hasTopBar = false,
            navController = navController,
            image = null,
            description = stringResource(id = R.string.description)
        ) {
            Column(
                modifier = Modifier.width(((measurement.width / 3)*2).dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.disable),
                    modifier = Modifier.padding(10.dp),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
//end of Scaffold
}