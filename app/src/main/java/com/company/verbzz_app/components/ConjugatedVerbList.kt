package com.company.verbzz_app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.view_models.VerbListViewModel

@Composable
fun ConjugatedVerbList(
    measurement: WindowMeasurement,
    verbListViewModel: VerbListViewModel,
    languageState: MutableState<String>,
    verb: String
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.onPrimary
    ) {
        if(measurement.isPortrait) {
            LazyColumn {
                itemsIndexed(verbListViewModel.moodList.data!!) { index, list ->
                    ConjugatedVerbCard(
                        verbListViewModel = verbListViewModel,
                        verb = verb,
                        tense = verbListViewModel.tenseList.data!![index],
                        language = languageState.value,
                        list = list,
                        measurement = measurement)
                }
            }
        } else {
            LazyRow {
                itemsIndexed(verbListViewModel.moodList.data!!) { index, list ->
                    ConjugatedVerbCard(
                        verbListViewModel = verbListViewModel,
                        verb = verb,
                        tense = verbListViewModel.tenseList.data!![index],
                        language = languageState.value,
                        list = list,
                        measurement = measurement)
                }
            }
        }
    }
}

@Composable
fun ConjugatedVerbCard(
    verbListViewModel: VerbListViewModel,
    verb: String,
    tense: String,
    language: String,
    list: List<String>,
    measurement: WindowMeasurement
) {
    Card(
        shape = RoundedCornerShape(25.dp),
        backgroundColor = MaterialTheme.colors.onPrimary,
        elevation = 6.dp,
        modifier = Modifier
            .padding(5.dp)
            .width(measurement.smallest.dp)
            .heightIn(0.dp, (measurement.biggest / 3).dp)
    ) {
        LazyColumn (
            modifier = Modifier.padding(top = 20.dp, bottom = 20.dp).wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            item {
                Text(
                    text = tense,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier
                        .background(MaterialTheme.colors.primary)
                        .padding(start = 20.dp)
                        .width(measurement.smallest.dp))
            }
            item {
                Spacer(modifier = Modifier.height(5.dp))
            }
            itemsIndexed(list) { index, _ ->
                Text(
                    modifier = Modifier.width(measurement.smallest.dp),
                    style = MaterialTheme.typography.h6,
                    textAlign = TextAlign.Center,
                    text = verbListViewModel.formatLine(
                        verb = verb,
                        tense = tense,
                        list = list,
                        index = index,
                        language = language
                    )
                )
            }
        }
    }
}
