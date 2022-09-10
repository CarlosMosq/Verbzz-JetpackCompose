package com.company.verbzz_app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.company.verbzz_app.screens.authentication.AuthenticationScreen
import com.company.verbzz_app.screens.conjugate_search.ConjugateScreen
import com.company.verbzz_app.screens.forgot.ForgotPassword
import com.company.verbzz_app.screens.graded_practice.GradedPracticeScreen
import com.company.verbzz_app.screens.graded_setup.GradedPracticeSetUp
import com.company.verbzz_app.screens.home.MainScreen
import com.company.verbzz_app.screens.in_app_purchases.ShoppingScreen
import com.company.verbzz_app.screens.login.LoginScreen
import com.company.verbzz_app.screens.sign_up.SignUpScreen
import com.company.verbzz_app.screens.splash.SplashScreen
import com.company.verbzz_app.screens.statistics.StatisticsScreen
import com.company.verbzz_app.screens.translate.TranslationPractice
import com.company.verbzz_app.ui.theme.rememberWindowMeasurement
import com.company.verbzz_app.utils.RandomizeVerbsAndTenses
import com.company.verbzz_app.view_models.*

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val measurement = rememberWindowMeasurement()
    val authViewModel = hiltViewModel<AuthViewModel>()
    val languageViewModel = hiltViewModel<LanguageViewModel>()
    val statisticsViewModel = hiltViewModel<StatisticsViewModel>()
    val verbListViewModel = hiltViewModel<VerbListViewModel>()
    val timerViewModel = TimerViewModel()
    val adViewModel = AdViewModel()
    val randomizeVerbsAndTenses = RandomizeVerbsAndTenses()
    val language: MutableState<String> = remember {
        mutableStateOf("")
    }
    languageViewModel.getCurrentLanguage(language)

    NavHost(navController = navController,
        startDestination = ScreenList.SplashScreen.name) {

        composable(ScreenList.SplashScreen.name) {
            SplashScreen(
                measurement = measurement,
                navController = navController
            )
        }

        composable(ScreenList.AuthenticationScreen.name) {
            AuthenticationScreen(
                navController = navController,
                measurement = measurement
            )
        }

        composable(ScreenList.LoginScreen.name) {
            LoginScreen(
                measurement = measurement,
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(ScreenList.SignUpScreen.name) {
            SignUpScreen(
                navController = navController,
                authViewModel = authViewModel,
                measurement = measurement
            )
        }

        composable(ScreenList.ForgotPassword.name) {
            ForgotPassword(
                navController = navController,
                authViewModel = authViewModel,
                measurement = measurement
            )
        }

        composable(ScreenList.MainScreen.name) {
            MainScreen(
                navController = navController,
                measurement = measurement,
                languageViewModel = languageViewModel
            )
        }

        composable(ScreenList.ConjugateScreen.name) {
            ConjugateScreen(
                navController = navController,
                measurement = measurement,
                languageViewModel = languageViewModel,
                languageState = language,
                verbListViewModel = verbListViewModel
            )
        }

        composable(ScreenList.GradedPracticeSetUp.name) {
            GradedPracticeSetUp(
                navController = navController,
                measurement = measurement,
                language = language
            )
        }

        composable(
            route = "${ScreenList.GradedPracticeScreen.name}/{verb_count}/{verb_tense}/{time_on_off}",
            arguments = listOf(
                navArgument(name ="verb_count") {type = NavType.StringType},
                navArgument(name = "verb_tense") {type = NavType.StringType},
                navArgument(name = "time_on_off") {type = NavType.BoolType},
            )) { backStackEntry ->
            backStackEntry.arguments?.let { bundle ->
                GradedPracticeScreen(
                    navController = navController,
                    measurement = measurement,
                    verbCount = bundle.getString("verb_count").toString(),
                    verbTense = bundle.getString("verb_tense").toString(),
                    timeOnOff = bundle.getBoolean("time_on_off"),
                    timerViewModel = timerViewModel,
                    adViewModel = adViewModel,
                    languageViewModel = languageViewModel,
                    verbListViewModel = verbListViewModel,
                    randomizeVerbsAndTenses = randomizeVerbsAndTenses,
                    languageState = language
                )
            }
        }

        composable(ScreenList.StatisticsScreen.name) {
            StatisticsScreen(
                navController = navController,
                measurement = measurement,
                statisticsViewModel = statisticsViewModel)
        }

        composable(ScreenList.ShoppingScreen.name) {
            ShoppingScreen(
                navController = navController,
                measurement = measurement
            )
        }

        composable(ScreenList.TranslationPractice.name) {
            TranslationPractice(
                navController = navController,
                measurement = measurement,
                timerViewModel = timerViewModel,
                languageViewModel = languageViewModel,
                randomizeVerbsAndTenses = randomizeVerbsAndTenses,
                adViewModel = adViewModel,
                languageState = language
            )
        }

    }
}