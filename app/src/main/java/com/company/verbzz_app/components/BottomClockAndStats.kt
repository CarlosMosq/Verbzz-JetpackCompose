package com.company.verbzz_app.components

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.view_models.AdViewModel
import com.company.verbzz_app.view_models.LanguageViewModel
import com.company.verbzz_app.view_models.TimerViewModel

@Composable
fun BottomClockAndStats(
    timeOn: Boolean,
    rightAnswers: MutableState<Int>,
    verbCount: MutableState<Int>,
    measurement: WindowMeasurement,
    timerViewModel: TimerViewModel,
    navController: NavController,
    languageViewModel: LanguageViewModel,
    context: Context,
    languageState: MutableState<String>,
    tense: String,
    adViewModel: AdViewModel,
) {
    val time = remember {
        mutableStateOf("Off")
    }
    LaunchedEffect(Unit) {
        if(timeOn) {
            timerViewModel.startTimer(
                time = time,
                languageViewModel = languageViewModel,
                navController = navController,
                context = context,
                verbCount = verbCount,
                rightAnswers = rightAnswers,
                languageState = languageState,
                adViewModel = adViewModel,
                tense = tense
            )
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height((measurement.biggest / 15).dp),
        color = MaterialTheme.colors.background,
    ) {
        Row(
            modifier = Modifier.padding(3.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = if(timeOn) painterResource(id = R.drawable.ic_baseline_timer_24)
                            else painterResource(id = R.drawable.ic_baseline_timer_off_24),
                contentDescription = stringResource(id = R.string.timerIcon)
            )
            Text(text = time.value)
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_percent_24),
                contentDescription = stringResource(id = R.string.percentageImg))
            Text(text = formatPercentages(
                rightAnswers = rightAnswers.value,
                verbCount = verbCount.value)
            )
            Text(text = formatFraction(
                rightAnswers = rightAnswers.value,
                verbCount = verbCount.value)
            )
        }
    }
}

fun formatFraction(rightAnswers: Int, verbCount: Int) : String {
    return String.format("%s/%s", rightAnswers, verbCount)
}

fun formatPercentages(rightAnswers: Int, verbCount: Int) : String {
    val percentage = (rightAnswers.toDouble() / verbCount.toDouble()) * 100
    return String.format("%s%s", percentage.toInt(), "%")
}




