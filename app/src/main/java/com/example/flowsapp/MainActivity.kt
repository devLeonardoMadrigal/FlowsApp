package com.example.flowsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.flowsapp.flows.FlowViewModel
import com.example.flowsapp.ui.theme.FlowsAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val flowVm by viewModels<FlowViewModel>()

            FlowsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(title={
                        Text("Flows app", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                    })
                }) {
                    val fc = flow<Int>{
                        for (i in 1 until 100){
                            emit(i)
                            delay(500)
                        }
                    }

                    Column() {
                        Row() {
                            //FlowsScreen(Modifier, fc)

                        }
                        Row() {
//                            FlowsScreen2(
//                                Modifier,
//                                flowVm.timer.collectAsStateWithLifecycle(),
//                                { flowVm.starTimer() }
//                            )

                        }
                        Row() {
                            FlowsScreen3(
                                Modifier,
                                flowVm.timer,
                                { flowVm.starTimer() }
                            )

                        }

                    }

                }
            }
        }
    }
}

@Composable
fun FlowsScreen(modifier: Modifier = Modifier, fc: Flow<Int>) {

    var f1 by remember { mutableStateOf(0) }
    var f2 by remember { mutableStateOf(0)}

    val scope = rememberCoroutineScope ()


    Column(Modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("Cold Flows", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Text("Flow 1 -> $f1", fontSize = 32.sp)
        Spacer(Modifier.size(12.dp))

        Button(onClick = {
            scope.launch {
                fc.collect {
                    f1 = it
                    println("T1 $f1")
                }
            }
        }) {
            Text("Collect for T2", fontSize = 32.sp)

        }

        Spacer(Modifier.size(24.dp))

        Text("Flow 2 -> $f2", fontSize = 32.sp)
        Spacer(Modifier.size(12.dp))

        Button(onClick = {
            scope.launch {
                fc.collect {
                    f2 = it
                    println("T2 $f2")

                }
            }
        }) {
            Text("Collect for T2", fontSize = 32.sp)
        }
    }
}


@Composable
fun FlowsScreen2(modifier: Modifier = Modifier, counterState : State<Int>,
                 startTimer : () -> Unit) {

    var f1 by remember { mutableStateOf(0) }
    var f2 by remember { mutableStateOf(0)}

    val scope = rememberCoroutineScope ()


    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("Hot Flows", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Text(
            "Flow 1 -> ${counterState.value}",
            fontSize = 32.sp)
        Spacer(Modifier.size(12.dp))

        Button(onClick = {
            startTimer()

        }) {
            Text("Collect for T1", fontSize = 32.sp)

        }
    }
}

@Composable
fun FlowsScreen3(
    modifier: Modifier = Modifier,
    fc : StateFlow<Int>,
    startTimer : () -> Unit) {

    var f1 by remember { mutableStateOf(0) }
    var f2 by remember { mutableStateOf(0)}

    val scope = rememberCoroutineScope ()


    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("Hot Flows Screen 3", fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Text(
            "Flow 1 -> $f1",
            fontSize = 32.sp)
        Spacer(Modifier.size(12.dp))

        Button(onClick = {
            startTimer()////// working on this
            scope.launch {
                fc.collect {
                    f1 = it
                }
            }
        }) {
            Text("Collect for T1", fontSize = 32.sp)

        }

        Text(
            "Flow 2 -> $f2",
            fontSize = 32.sp)
        Spacer(Modifier.size(12.dp))

        Button(onClick = {
            scope.launch {
                fc.collect {
                    f2 = it
                }
            }
        }) {
            Text("Collect for T2", fontSize = 32.sp)

        }

    }
}