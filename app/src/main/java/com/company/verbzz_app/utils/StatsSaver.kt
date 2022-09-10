package com.company.verbzz_app.utils

import androidx.compose.runtime.MutableState
import com.company.verbzz_app.components.formatFraction
import com.company.verbzz_app.components.formatPercentages
import com.company.verbzz_app.view_models.LanguageViewModel
import java.util.*

fun saveStatsToDatabase(
    languageViewModel: LanguageViewModel,
    tense: String,
    verbCount: MutableState<Int>,
    rightAnswers: MutableState<Int>,
    languageState: MutableState<String>
) {
    val date = Date().toString()
    languageViewModel.saveStatsToDatabase(
        tense = tense,
        scores = String.format("%s (%s)",
            formatFraction(rightAnswers = rightAnswers.value, verbCount = verbCount.value),
            formatPercentages(rightAnswers = rightAnswers.value, verbCount = verbCount.value)
        ),
        date = date,
        language = languageState.value
    )
}