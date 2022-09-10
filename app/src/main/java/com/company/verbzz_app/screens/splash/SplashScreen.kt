package com.company.verbzz_app.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.company.verbzz_app.R
import com.company.verbzz_app.components.SplashBackground
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.ui.theme.WindowMeasurement
import com.company.verbzz_app.utils.playSound
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@ExperimentalAnimationApi
@Composable
fun SplashScreen(navController: NavController, measurement: WindowMeasurement) {
    val state = remember {
        MutableTransitionState(false).apply { targetState = true }
    }
    val context = LocalContext.current

    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn()
    ) {
        Surface(
            modifier = Modifier
                .padding(15.dp)
                .wrapContentSize()
                .animateEnterExit(fadeIn(tween(2000, easing = FastOutLinearInEasing))),
            color = MaterialTheme.colors.background,
        ) {
            SplashBackground(measurement = measurement)
        }
    }

    LaunchedEffect(key1 = true) {
        playSound(context = context, audio = R.raw.bee)

        delay(3000L)

        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(ScreenList.AuthenticationScreen.name)
        } else {
            navController.navigate(ScreenList.MainScreen.name)
        }

    }



}