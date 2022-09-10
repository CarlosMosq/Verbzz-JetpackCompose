package com.company.verbzz_app.view_models

import android.app.Activity
import android.content.Context
import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.company.verbzz_app.navigation.ScreenList
import com.company.verbzz_app.utils.saveStatsToDatabase
import java.util.*

private const val DEFAULT_START_TIME_IN_MILLIS: Long = 300_000

class TimerViewModel: ViewModel() {
    private var timeLeftInMillis: Long = DEFAULT_START_TIME_IN_MILLIS
    private var countDownTimer: CountDownTimer? = null

    fun startTimer(
        time: MutableState<String>,
        languageViewModel: LanguageViewModel,
        context: Context,
        tense: String,
        languageState: MutableState<String>,
        verbCount: MutableState<Int>,
        rightAnswers: MutableState<Int>,
        navController: NavController,
        adViewModel: AdViewModel
    ) {
        countDownTimer = object : CountDownTimer(DEFAULT_START_TIME_IN_MILLIS, 1_000) {
            override fun onTick(remaining: Long) {
                timeLeftInMillis = remaining
                val minutes = timeLeftInMillis / 1000 / 60
                val secsToFinish = (remaining / 1000) % 60
                time.value = returnTime(minutes = minutes, secsToFinish = secsToFinish)
            }

            override fun onFinish() {
                saveStatsToDatabase(
                    languageViewModel = languageViewModel,
                    verbCount = verbCount,
                    tense = tense,
                    rightAnswers = rightAnswers,
                    languageState = languageState
                )
                adViewModel.loadInterstitialAd(
                    context = context,
                    navController = navController,
                    route = ScreenList.StatisticsScreen.name)
                adViewModel.showAd(context as Activity)
            }
        }.start()
    }

    fun returnTime(minutes: Long, secsToFinish: Long) : String {
        return String.format(Locale.getDefault(), "%02d:%02d", minutes, secsToFinish)
    }

}



