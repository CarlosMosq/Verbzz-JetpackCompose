package com.company.verbzz_app.screens.graded_practice

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
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
import com.company.verbzz_app.utils.playSound
import com.company.verbzz_app.view_models.AdViewModel
import com.company.verbzz_app.view_models.LanguageViewModel
import com.company.verbzz_app.view_models.TimerViewModel
import com.company.verbzz_app.view_models.VerbListViewModel

@ExperimentalComposeUiApi
@Composable
fun GradedPracticeScreen(
    navController: NavController,
    measurement: WindowMeasurement,
    verbCount: String,
    verbTense: String,
    timerViewModel: TimerViewModel,
    adViewModel: AdViewModel,
    languageViewModel: LanguageViewModel,
    verbListViewModel: VerbListViewModel,
    languageState: MutableState<String>,
    randomizeVerbsAndTenses: RandomizeVerbsAndTenses,
    timeOnOff: Boolean
) {
    val context = LocalContext.current
    val ad = remember {
        mutableStateOf(adViewModel.loadInterstitialAd(
            context = context,
            navController = navController,
            route = ScreenList.StatisticsScreen.name))
    }
    val job = remember {
        mutableStateOf(verbListViewModel.setPronounAndVerb(
            verbCount = verbCount,
            tense = verbTense,
            language = languageState.value,
            randomizeVerbsAndTenses = randomizeVerbsAndTenses,
            context = context))
    }
    val pronounAndVerb = remember {
        mutableStateOf(String.format("%s%s",
            verbListViewModel.pronoun.value,
            verbListViewModel.verb.value))
    }
    val answerState = remember { mutableStateOf("") }
    val rightAnswers = rememberSaveable { mutableStateOf(0) }
    val verbTotal = rememberSaveable { mutableStateOf(0) }
    val valid = remember(answerState.value) { answerState.value.trim().isNotEmpty() }
    val correctAnswerCheck = remember { mutableStateOf("") }
    val colorAnswerState = remember { mutableStateOf(Color.Red) }
    val enabled = remember { mutableStateOf(false) }

    BaseBackground(
        measurement = measurement,
        title = verbTense,
        secondIcon = true,
        isConjugateScreen = false,
        hasTopBar = false,
        navController = navController,
        tense = verbTense,
        image = painterResource(id = R.drawable.ic_baseline_auto_graph_24),
        description = stringResource(id = R.string.statisticsPage),
        context = context,
        verbCount = verbTotal,
        rightAnswers = rightAnswers,
        adViewModel = adViewModel,
        languageViewModel = languageViewModel,
        languageState = languageState,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 10.dp, bottom = 20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            VerbAndInputLayout(
                measurement = measurement,
                pronounAndVerb = pronounAndVerb,
                answerState = answerState
            )

            SubmitButton(
                textId = stringResource(id = R.string.checkAnswer),
                loading = false,
                measurement = measurement,
                validInputs = true) {
                if(valid) {
                    val list = verbListViewModel.getTenseData(
                        language = languageState.value,
                        tense = verbTense
                    )
                    val toCompare =
                        if(list.size <= 6) list[verbListViewModel.pronounIndex.value]
                        else list[verbListViewModel.pronounIndex.value * 2]
                    if(answerState.value == toCompare) {
                        correctAnswerCheck.value = context.getString(R.string.correct)
                        colorAnswerState.value = Color.Green
                        rightAnswers.value++
                        playSound(context = context, audio = R.raw.bell)
                    } else {
                        correctAnswerCheck.value =
                            "${context.getString(R.string.status)} $toCompare"
                        colorAnswerState.value = Color.Red
                        playSound(context = context, audio = R.raw.error)
                    }
                }
                verbTotal.value++
                answerState.value = ""
                enabled.value = true
                verbListViewModel.setPronounAndVerb(
                    verbCount = verbCount,
                    tense = verbTense,
                    language = languageState.value,
                    randomizeVerbsAndTenses = randomizeVerbsAndTenses,
                    context = context)
                pronounAndVerb.value = String.format("%s%s",
                    verbListViewModel.pronoun.value,
                    verbListViewModel.verb.value)
            }

            Text(
                text = correctAnswerCheck.value,
                modifier = Modifier
                    .padding(1.dp)
                    .alpha(if (enabled.value) 1f else 0f)
                    .wrapContentHeight(),
                style = MaterialTheme.typography.caption,
                color = colorAnswerState.value
            )

            BottomClockAndStats(
                timeOn = timeOnOff,
                rightAnswers = rightAnswers,
                verbCount = verbTotal,
                measurement = measurement,
                timerViewModel = timerViewModel,
                navController = navController,
                adViewModel = adViewModel,
                context = context,
                languageState = languageState,
                languageViewModel = languageViewModel,
                tense = verbTense
            )

        }
    }
}

@Composable
fun VerbAndInputLayout(
    measurement: WindowMeasurement,
    pronounAndVerb: MutableState<String>,
    answerState: MutableState<String>
) {
    if(measurement.isPortrait) {
        Column (
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Text(
                text = pronounAndVerb.value,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            InputField(
                valueState = answerState,
                labelId = stringResource(id = R.string.answer),
                enabled = true,
                measurement = measurement)
        }
    } else {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(1.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Text(
                text = pronounAndVerb.value,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center
            )
            InputField(
                valueState = answerState,
                labelId = stringResource(id = R.string.answer),
                enabled = true,
                measurement = measurement)
        }
    }
}

