package com.example.flowsapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.flowsapp.ui.theme.FlowsAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlowsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {

//                    val d = DocFlow()

//                    var currentText by remember { mutableStateOf("") }
//                        runBlocking {
//                        d.intFlow.collect{
//                            println("Main sync $it")
//                            currentText = "$it"
//                        }
//                    }

                    val fc = flow<Int>{
                        for (i in 1 until 100){
                            emit(i)
                            delay(500)
                        }
                    }

                    FlowsScreen(Modifier, fc)

//                    Column(
//                        modifier = Modifier.fillMaxSize(),
//                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
//                            Text(currentText)
//                    }

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


    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text("Flow 1 -> $f1")
        Button(onClick = {
            scope.launch {
                fc.collect {
                    f1 = it
                }
            }
        }) {
            Text("Collect for T2")

        }
        Text("Flow 2 -> $f2")
        Button(onClick = {
            scope.launch {
                fc.collect {
                    f2 = it
                }
            }
        }) {
            Text("Collect for T2")
        }
    }
}

//class DocFlow {
//    val intFlow = flow<Int> {
//        for(i in 1 until 100){
//            emit(i)
//            delay(500)
//        }
//    }
//}
