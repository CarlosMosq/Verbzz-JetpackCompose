package com.company.verbzz_app.screens.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement

@Composable
fun AuthenticationScreen(navController: NavController, measurement: WindowMeasurement) {

    Surface(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = (measurement.height / 30).dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

           TopRow(measurement = measurement)

           Text(text = stringResource(id = R.string.style_phrase),
               modifier = Modifier.padding(1.dp),
               fontSize = 20.sp,
               style = MaterialTheme.typography.caption)

           BottomButtons(measurement = measurement, navController = navController)

        }

    }

}

@Composable
fun TopRow(measurement: WindowMeasurement) {
    Row(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nicubunu_honey),
            contentDescription = stringResource(id = R.string.description),
            modifier = Modifier.size((measurement.biggest/15).dp)
        )
        Text(
            text = stringResource(id = R.string.title),
            modifier = Modifier.padding(1.dp),
            style = MaterialTheme.typography.h4,
            color = MaterialTheme.colors.primaryVariant
        )
        Image(
            painter = painterResource(id = R.drawable.ic_biene),
            contentDescription = stringResource(id = R.string.animatedBee),
            modifier = Modifier.size((measurement.biggest/15).dp)
        )
    }
}

@Composable
fun BottomButtons(measurement: WindowMeasurement, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .height(
            if (measurement.isPortrait)(measurement.height/3).dp
            else (measurement.height/2).dp
        )
    ) {

        Box(modifier = Modifier
            .fillMaxWidth()
            .height((measurement.height/8).dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_wave__1_),
                contentDescription = stringResource(id = R.string.wave),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
            Image(
                painter = painterResource(id = R.drawable.ic_wave),
                contentDescription = stringResource(id = R.string.wave),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxWidth()
            )
        }


        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = MaterialTheme.colors.primaryVariant) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        navController.navigate(ScreenList.SignUpScreen.name)
                    },
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 6.dp,
                        pressedElevation = 8.dp,
                        disabledElevation = 0.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.getStarted),
                        color = MaterialTheme.colors.onSecondary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = stringResource(id = R.string.log_in),
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.clickable {
                        navController.navigate(ScreenList.LoginScreen.name)
                    })
            }

        }
    }


}