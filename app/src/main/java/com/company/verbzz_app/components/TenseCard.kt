package com.company.verbzz_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.Constants.englishMoods
import com.company.verbzz_app.utils.Constants.frenchMoods

@Composable
fun Moods(
    language: String,
    measurement: WindowMeasurement,
    onClick: (String) -> Unit
) {
    val list = if(language == "English") englishMoods else frenchMoods
    if(measurement.isPortrait) {
        LazyColumn (
            modifier = Modifier.fillMaxWidth().padding(bottom = (measurement.biggest / 10).dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            items(list) { mood ->
                MoodCard(text = mood, measurement = measurement) {
                    onClick.invoke(mood)
                }
            }
        }
    } else {
        LazyRow(
            modifier = Modifier.fillMaxSize().padding(bottom = (measurement.biggest / 10).dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(list) { mood ->
                MoodCard(text = mood, measurement = measurement) {
                    onClick.invoke(mood)
                }
            }
        }
    }

}

@Composable
fun MoodCard(
    text: String,
    measurement: WindowMeasurement,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(25.dp),
        backgroundColor = MaterialTheme.colors.secondaryVariant,
        elevation = 6.dp,
        modifier = Modifier
            .padding(5.dp)
            .width(((measurement.smallest / 4) * 3).dp)
            .height((measurement.biggest / 8).dp)
            .clickable {
                onClick.invoke()
            }
    ) {
        Column(
            modifier = Modifier.padding(2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = text)
        }
    }
}
