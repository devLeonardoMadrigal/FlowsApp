package com.example.flowsapp.mvi

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.flowsapp.ScreenToggle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScreenToggleViewModel : ViewModel()  {


    private val _currentState = MutableStateFlow<String>("A")
    val currentState = _currentState.asStateFlow()

    fun toggle(intent: ScreenToggle){

        when(intent) {
            ScreenToggle.GoToB -> {
                if(_currentState.value == "A"){
                    _currentState.update {"B"}
                }
            }
            ScreenToggle.GoToC -> {
                if(_currentState.value == "B"){
                    _currentState.update { "C" }
                }
            }
            ScreenToggle.GoToA -> {
                if(_currentState.value == "C"){
                    _currentState.update { "A" }
                }
            }
        }
    }

}