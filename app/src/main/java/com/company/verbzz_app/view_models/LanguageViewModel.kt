package com.company.verbzz_app.view_models

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.verbzz_app.repository.FireRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(private val repository: FireRepository)
    : ViewModel(){
    private val userUID = FirebaseAuth.getInstance().currentUser?.uid

        fun getCurrentLanguage(languageState: MutableState<String>)
        = viewModelScope.launch {
            repository.getCurrentLanguage(languageState = languageState, userID = userUID!!)
        }

        fun setCurrentLanguage(language: String, context: Context)
        = viewModelScope.launch {
            repository.setCurrentLanguage(language = language, context = context, userID = userUID!!)
        }

        fun saveStatsToDatabase(tense: String, scores: String, date: String, language: String)
        = viewModelScope.launch {
            repository.saveStatsToDatabase(
                tense = tense,
                scores = scores,
                date = date,
                language = language
            )
        }




}