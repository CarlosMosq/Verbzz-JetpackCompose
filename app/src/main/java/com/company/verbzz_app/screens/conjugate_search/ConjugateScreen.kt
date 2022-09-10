package com.company.verbzz_app.screens.conjugate_search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
                languageViewModel.getCurrentLanguage(languageState = languageState)
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
                        context = context)
                }
            },
            navController = navController,
            image = null,
            description = stringResource(id = R.string.description)
        ) {
            if(!verbSearchClicked.value) {
                SplashBackground(measurement = measurement)
            }
            else if (verbSearchClicked.value && !moodButtonClicked.value){
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
            else if (verbSearchClicked.value && moodButtonClicked.value){
                ConjugatedVerbList(
                    measurement = measurement,
                    verbListViewModel = verbListViewModel,
                    languageState = languageState,
                    verb = verbState.value
                )
            }
        }
    }
//end of Scaffold
}