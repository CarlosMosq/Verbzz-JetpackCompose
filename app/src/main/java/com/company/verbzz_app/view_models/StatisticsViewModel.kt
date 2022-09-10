package com.company.verbzz_app.view_models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.verbzz_app.model.Stats
import com.company.verbzz_app.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(private val repository: FireRepository)
    : ViewModel() {
    val data: MutableState<List<Stats>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            updateListOfScores()
        }
    }

    fun updateListOfScores()
    = viewModelScope.launch {
        repository.getListOfScores(stateData = data)
    }

}

