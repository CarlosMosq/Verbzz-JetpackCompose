package com.company.verbzz_app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement

@Composable
fun MainBottomBar(navController: NavController, measurement: WindowMeasurement) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()) {
        Divider(
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.height(1.dp)
        )
        Row (modifier = Modifier
            .fillMaxWidth()
            .height((measurement.biggest / 10).dp)
            .background(color = MaterialTheme.colors.surface),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_nicubunu_honey),
                contentDescription = stringResource(id = R.string.description),
                modifier = Modifier
                    .size((measurement.biggest / 15).dp)
                    .padding(3.dp)
                    .clickable {
                        navController.navigate(ScreenList.MainScreen.name)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_auto_graph_24),
                contentDescription = stringResource(id = R.string.description),
                modifier = Modifier
                    .size((measurement.biggest / 15).dp)
                    .padding(3.dp)
                    .clickable {
                        navController.navigate(ScreenList.StatisticsScreen.name)
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_add_shopping_cart_24),
                contentDescription = stringResource(id = R.string.description),
                modifier = Modifier
                    .size((measurement.biggest / 15).dp)
                    .padding(3.dp)
                    .clickable {
                        navController.navigate(ScreenList.ShoppingScreen.name)
                    }
            )
        }
    }

}