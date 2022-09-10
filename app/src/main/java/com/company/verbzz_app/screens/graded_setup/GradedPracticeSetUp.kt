package com.company.verbzz_app.screens.graded_setup

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.DropdownMenuComponent
import com.company.verbzz_app.components.SubmitButton
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.Constants.englishTenses
import com.company.verbzz_app.utils.Constants.frenchTenses
import com.company.verbzz_app.utils.Constants.nbrOfVerbs

@ExperimentalComposeUiApi
@Composable
fun GradedPracticeSetUp(
    navController: NavController,
    measurement: WindowMeasurement,
    language: MutableState<String>
) {
    val selectedCount = remember {mutableStateOf("")}
    val selectedTense = remember {mutableStateOf("")}
    val time = remember { mutableStateOf("5") }
    val checkedState = remember { mutableStateOf(true) }
    val list = if(language.value == "English") englishTenses else frenchTenses

    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.customize),
        secondIcon = false,
        isConjugateScreen = false,
        languageState = language,
        hasTopBar = false,
        navController = navController,
        image = null,
        description = stringResource(id = R.string.description)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(top = 15.dp)
        ) {
            ItemsLayout(
                measurement = measurement,
                listOfTenses = list,
                selectedCount = selectedCount,
                selectedTense = selectedTense,
                time = time,
                checkedState = checkedState,
            ) { verbCount, verbTense, timeOnOff ->
                navController.navigate(route =
                    "${ScreenList.GradedPracticeScreen.name}/$verbCount/$verbTense/$timeOnOff")
            }
        }
    }
}

@Composable
fun ItemsLayout(
    measurement: WindowMeasurement,
    listOfTenses: List<String>,
    selectedCount: MutableState<String>,
    selectedTense: MutableState<String>,
    time: MutableState<String>,
    checkedState: MutableState<Boolean>,
    onDone: (String, String, Boolean) -> Unit
) {
    if(measurement.isPortrait) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Selectors(
                measurement = measurement,
                listOfTenses = listOfTenses,
                selectedCount = selectedCount,
                selectedTense = selectedTense)
            TimeAndButton(
                measurement = measurement,
                selectedCount = selectedCount,
                selectedTense = selectedTense,
                time = time,
                checkedState = checkedState,
                onDone = onDone)
        }
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Selectors(
                measurement = measurement,
                listOfTenses = listOfTenses,
                selectedCount = selectedCount,
                selectedTense = selectedTense)
            TimeAndButton(
                measurement = measurement,
                selectedCount = selectedCount,
                selectedTense = selectedTense,
                time = time,
                checkedState = checkedState,
                onDone = onDone)
        }
    }
}

@Composable
fun Selectors(
    measurement: WindowMeasurement,
    listOfTenses: List<String>,
    selectedCount: MutableState<String>,
    selectedTense: MutableState<String>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        DropdownMenuComponent(
            measurement = measurement,
            list = nbrOfVerbs,
            label = stringResource(id = R.string.selectCount),
            selectedItem = selectedCount,
            description = stringResource(id = R.string.selectCount))
        DropdownMenuComponent(
            measurement = measurement,
            list = listOfTenses,
            label = stringResource(id = R.string.selectTense),
            selectedItem = selectedTense,
            description = stringResource(id = R.string.selectTense))
    }
}

@Composable
fun TimeAndButton(
    measurement: WindowMeasurement,
    selectedCount: MutableState<String>,
    selectedTense: MutableState<String>,
    time: MutableState<String>,
    checkedState: MutableState<Boolean>,
    onDone: (String, String, Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.width(((measurement.smallest / 4) * 3).dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = "${time.value} MIN",
                readOnly = true,
                singleLine = true,
                onValueChange = { time.value = it },
                modifier = Modifier.width(100.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center)
            )
            Switch(
                checked = checkedState.value,
                modifier = Modifier.width(30.dp),
                onCheckedChange = {
                checkedState.value = it
                time.value = if(checkedState.value) "5"
                else "Off"
            })
        }

        SubmitButton(
            textId = stringResource(id = R.string.start),
            loading = false,
            measurement = measurement,
            validInputs = true) {
            onDone(selectedCount.value, selectedTense.value, checkedState.value)
        }
    }
}