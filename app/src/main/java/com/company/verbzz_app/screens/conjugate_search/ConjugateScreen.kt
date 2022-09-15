package com.company.verbzz_app.screens.conjugate_search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.*
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.view_models.LanguageViewModel
import com.company.verbzz_app.view_models.VerbListViewModel
import kotlinx.coroutines.launch

@ExperimentalComposeUiApi
@Composable
fun ConjugateScreen(
    navController: NavController,
    measurement: WindowMeasurement,
    languageViewModel: LanguageViewModel,
    languageState: MutableState<String>,
    verbListViewModel: VerbListViewModel
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val verbSearchClicked = remember { mutableStateOf(false) }
    val moodButtonClicked = remember { mutableStateOf(false) }
    val verbState = remember { mutableStateOf("") }
    val loaded = remember { mutableStateOf(false) }
    val doesNotExist = remember { mutableStateOf(false) }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if(!moodButtonClicked.value) {
                MainBottomBar(navController = navController, measurement = measurement)
            }
        },
        drawerContent = {
            DrawerContent(measurement = measurement) { languageToSet ->
                languageViewModel.setCurrentLanguage(language = languageToSet, context = context)
                languageViewModel.getCurrentLanguage(
                    languageState = languageState)
                verbSearchClicked.value = false
                scope.launch { scaffoldState.drawerState.close() }
            }
        }
    ) {
        BaseBackground(
            measurement = measurement,
            title = "",
            secondIcon = false,
            isConjugateScreen = true,
            hasTopBar = true,
            languageState = languageState,
            openDrawer = {
                scope.launch { scaffoldState.drawerState.open() }
            },
            searchVerb = { verb ->
                verbState.value = verb
                verbSearchClicked.value = true
                moodButtonClicked.value = false
                scope.launch {
                    verbListViewModel.getVerbData(
                        language = languageState.value,
                        verbText = verbState,
                        context = context,
                        loaded = loaded,
                        doesNotExist = doesNotExist)
                }
            },
            navController = navController,
            image = null,
            description = stringResource(id = R.string.description)
        ) {
            if(!verbSearchClicked.value || doesNotExist.value) {
                SplashBackground(measurement = measurement)
            }
            else if(verbSearchClicked.value && !loaded.value) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(modifier = Modifier.size((measurement.biggest/10).dp))
                }
            }
            else if (verbSearchClicked.value && loaded.value && !moodButtonClicked.value){
                Moods(
                    language = languageState.value,
                    measurement = measurement) { mood ->
                    scope.launch {
                        verbListViewModel.getMoodData(mood = mood)
                        verbListViewModel.getTenseTitle(mood = mood)
                        moodButtonClicked.value = true
                    }
                }
            }
            else if (verbSearchClicked.value && moodButtonClicked.value && loaded.value){
                ConjugatedVerbList(
                    measurement = measurement,
                    verbListViewModel = verbListViewModel,
                    languageState = languageState,
                    verb = verbState.value
                )
                BackHandler {
                    moodButtonClicked.value = false
                }
            }
        }
    }
//end of Scaffold
}