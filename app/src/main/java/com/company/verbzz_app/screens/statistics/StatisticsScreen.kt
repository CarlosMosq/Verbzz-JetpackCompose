package com.company.verbzz_app.screens.statistics

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.MainBottomBar
import com.company.verbzz_app.model.Stats
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.view_models.StatisticsViewModel

@ExperimentalComposeUiApi
@Composable
fun StatisticsScreen(
    navController: NavController, 
    measurement: WindowMeasurement,
    statisticsViewModel: StatisticsViewModel
) {
    statisticsViewModel.updateListOfScores()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(navController = navController, measurement = measurement)
        }
    ) {
        BaseBackground(
            measurement = measurement,
            title = stringResource(id = R.string.statistics),
            secondIcon = false,
            hasTopBar = false,
            navController = navController,
            image = null,
            description = stringResource(id = R.string.description)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(bottom = ((measurement.biggest / 10).dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    StatsRow(
                        activity = stringResource(id = R.string.activity),
                        stat = Stats(
                            date = stringResource(id = R.string.date),
                            score = stringResource(id = R.string.score)),
                        measurement = measurement,
                        color = MaterialTheme.colors.secondary
                    )
                }
                items(statisticsViewModel.data.value) { stat ->
                    StatsRow(
                        activity = activityString(stat),
                        stat = stat,
                        measurement = measurement)
                }
            }
        }
    }
//end of Scaffold
}

@Composable
fun StatsRow(
    activity: String,
    stat: Stats,
    measurement: WindowMeasurement,
    color: Color? = MaterialTheme.colors.onPrimary
) {
    Row(modifier = Modifier
        .padding(5.dp)
        .background(color = color!!)
        .fillMaxWidth()
    ) {
        Text(
            text = activity,
            modifier = Modifier.width((measurement.width/3).dp),
            textAlign = TextAlign.Center,
            maxLines = 2)
        Text(
            text = stat.score!!,
            color = MaterialTheme.colors.secondaryVariant,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width((measurement.width/3).dp),
            textAlign = TextAlign.Center,
            maxLines = 2)
        Text(
            text = stat.date!!,
            modifier = Modifier.width((measurement.width/3).dp),
            textAlign = TextAlign.Center,
            maxLines = 2)
    }
}

fun activityString(stat: Stats) : String {
    val initializer = if(stat.language == "English") "En" else "Fr"
    return String.format("(%s) %s", initializer, stat.tense)
}