package com.company.verbzz_app.screens.translate

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.BaseBackground
import com.company.verbzz_app.components.BottomClockAndStats
import com.company.verbzz_app.components.InputField
import com.company.verbzz_app.components.SubmitButton
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.RandomizeVerbsAndTenses
import com.company.verbzz_app.utils.TranslationLists.englishVerbs
import com.company.verbzz_app.utils.TranslationLists.frenchVerbs
import com.company.verbzz_app.utils.playSound
import com.company.verbzz_app.view_models.AdViewModel
import com.company.verbzz_app.view_models.LanguageViewModel
import com.company.verbzz_app.view_models.TimerViewModel

@ExperimentalComposeUiApi
@Composable
fun TranslationPractice(
    navController: NavController,
    measurement: WindowMeasurement,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses,
    languageViewModel: LanguageViewModel,
    languageState: MutableState<String>,
    timerViewModel: TimerViewModel,
    adViewModel: AdViewModel
) {
    val context = LocalContext.current
    val ad = remember {
        mutableStateOf(adViewModel.loadInterstitialAd(
            context = context,
            navController = navController,
            route = ScreenList.StatisticsScreen.name))
    }
    val switchState = rememberSaveable {
        mutableStateOf(languageState.value == R.string.english.toString())
    }
    val rightAnswers = rememberSaveable { mutableStateOf(0) }
    val verbCount = rememberSaveable { mutableStateOf(0) }
    val verb = remember { mutableStateOf(setVerb(switchState.value, randomizeVerbsAndTenses)) }
    val inputAnswer = remember { mutableStateOf("") }
    val valid = remember(inputAnswer.value) { inputAnswer.value.trim().isNotEmpty() }
    val correctAnswerCheck = remember { mutableStateOf("") }
    val colorAnswerState = remember { mutableStateOf(Color.Red) }
    val enabled = remember { mutableStateOf(false) }

    BaseBackground(
        measurement = measurement,
        title = stringResource(id = R.string.translate),
        secondIcon = true,
        hasTopBar = false,
        navController = navController,
        image = painterResource(id = R.drawable.ic_baseline_auto_graph_24),
        description = stringResource(id = R.string.statisticsPage),
        languageViewModel = languageViewModel,
        context = context,
        verbCount = verbCount,
        rightAnswers = rightAnswers,
        languageState = languageState,
        adViewModel = adViewModel,
        tense = stringResource(id = R.string.translate)
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            SwitchAndVerbLayout(
                measurement = measurement,
                switchState = switchState,
                verb = verb,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses
            )

            InputAndSubmitLayout(
                answer = inputAnswer,
                measurement = measurement,
                valid = valid,
                verb = verb,
                correctAnswerState = correctAnswerCheck,
                colorAnswerState = colorAnswerState,
                enabled = enabled,
                rightAnswers = rightAnswers,
                verbCount = verbCount,
                switchState = switchState,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses
            )

            Text(
                text = correctAnswerCheck.value,
                modifier = Modifier
                    .padding(1.dp)
                    .alpha(if (enabled.value) 1f else 0f),
                style = MaterialTheme.typography.caption,
                color = colorAnswerState.value,
                maxLines = 2
            )

            BottomClockAndStats(
                timeOn = true,
                rightAnswers = rightAnswers,
                verbCount = verbCount,
                measurement = measurement,
                timerViewModel = timerViewModel,
                navController = navController,
                context = context,
                languageState = languageState,
                languageViewModel = languageViewModel,
                adViewModel = adViewModel,
                tense = stringResource(id = R.string.translate)
            )

        }
    }
}

@Composable
fun SwitchAndVerbLayout(
    measurement: WindowMeasurement,
    switchState: MutableState<Boolean>,
    verb: MutableState<List<String>>,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses
) {
    if(measurement.isPortrait) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            SwitchAndVerb(
                switchState = switchState,
                verb = verb,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SwitchAndVerb(
                switchState = switchState,
                verb = verb,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses)
        }
    }
}

@Composable
fun InputAndSubmitLayout(
    answer: MutableState<String>,
    verb: MutableState<List<String>>,
    measurement: WindowMeasurement,
    correctAnswerState: MutableState<String>,
    colorAnswerState: MutableState<Color>,
    enabled: MutableState<Boolean>,
    rightAnswers: MutableState<Int>,
    verbCount: MutableState<Int>,
    switchState: MutableState<Boolean>,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses,
    valid: Boolean
) {
    if(measurement.isPortrait) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            InputAndSubmit(
                answer = answer,
                measurement = measurement,
                valid = valid,
                verb = verb,
                correctAnswerState = correctAnswerState,
                colorAnswerState = colorAnswerState,
                enabled = enabled,
                rightAnswers = rightAnswers,
                verbCount = verbCount,
                switchState = switchState,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses
            )
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputAndSubmit(
                answer = answer,
                measurement = measurement,
                valid = valid,
                verb = verb,
                correctAnswerState = correctAnswerState,
                colorAnswerState = colorAnswerState,
                enabled = enabled,
                rightAnswers = rightAnswers,
                verbCount = verbCount,
                switchState = switchState,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses
            )
        }
    }
}

@Composable
fun SwitchAndVerb(
    switchState: MutableState<Boolean>,
    verb: MutableState<List<String>>,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses
) {
    Switch(
        checked = switchState.value,
        modifier = Modifier.width(30.dp),
        onCheckedChange = {
            switchState.value = it
            verb.value = setVerb(switchState.value, randomizeVerbsAndTenses)
        })
    Text(
        text = verb.value[0],
        modifier = Modifier.padding(1.dp),
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.Center,
        maxLines = 2
    )
}

@Composable
fun InputAndSubmit(
    answer: MutableState<String>,
    verb: MutableState<List<String>>,
    measurement: WindowMeasurement,
    correctAnswerState: MutableState<String>,
    colorAnswerState: MutableState<Color>,
    enabled: MutableState<Boolean>,
    rightAnswers: MutableState<Int>,
    verbCount: MutableState<Int>,
    switchState: MutableState<Boolean>,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses,
    valid: Boolean
) {
    val context = LocalContext.current
    InputField(
        valueState = answer,
        labelId = stringResource(id = R.string.answer),
        enabled = true,
        measurement = measurement)

    SubmitButton(
        textId = stringResource(id = R.string.checkAnswer),
        loading = false,
        measurement = measurement,
        validInputs = valid) {
        if(valid) {
            if(answer.value == verb.value[1]) {
                correctAnswerState.value = context.getString(R.string.correct)
                colorAnswerState.value = Color.Green
                rightAnswers.value++
                playSound(context = context, audio = R.raw.bell)
            }
            else if(verb.value[1].contains(answer.value)) {
                correctAnswerState.value = "${context.getString(R.string.status)} ${verb.value[1]}"
                colorAnswerState.value = Color.Green
                rightAnswers.value++
                playSound(context = context, audio = R.raw.bell)
            }
            else {
                correctAnswerState.value = "${context.getString(R.string.status)} ${verb.value[1]}"
                colorAnswerState.value = Color.Red
                playSound(context = context, audio = R.raw.error)
            }
            enabled.value = true
            verbCount.value++
            verb.value = setVerb(
                englishOn = switchState.value,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses
            )
            answer.value = ""
        }
    }
}

private fun setVerb(
    englishOn: Boolean,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses
) : List<String> {
    //this activity works with lists of exactly 100 verbs
    val totalIndexes = 99
    val randomIndex = randomizeVerbsAndTenses.returnRandomIndex(totalIndexes)
    return if(englishOn) {
        listOf(englishVerbs[randomIndex], frenchVerbs[randomIndex])
    }
    else listOf(frenchVerbs[randomIndex], englishVerbs[randomIndex])
}


