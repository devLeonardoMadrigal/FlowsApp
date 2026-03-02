package com.example.flowsapp.flows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {
    /*
    Cold flows: ->
    Hot flows ->
        ->they retain the latest information
        ->They don't start from scratch every time a new collector calls them
        -> There are two types:
            ->Stateflow
            -> SharedFlow

     */



    //////////////////////////

    val _timer = MutableStateFlow<Int>(0)
       val timer  = _timer.asStateFlow()


    private var counter = 0

    private var isRunning : Boolean = false


    fun starTimer(){
        //Main -> Coroutines to the UI thread

        isRunning = true
        viewModelScope.launch(Dispatchers.IO) {
            while (isRunning){
                counter++
                delay(1000)


                //.update{} -> thread same
                // .value= counter -> not thread same
                _timer.update {
                    counter
                }
            }

        }
    }

}

