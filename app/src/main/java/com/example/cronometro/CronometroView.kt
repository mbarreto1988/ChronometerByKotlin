package com.example.cronometro

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cronometro.ui.theme.CronometroTheme
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@SuppressLint("DefaultLocale")
@Composable
fun CronometroView(modifier: Modifier = Modifier) {

    val listaDeTiempos = remember { mutableStateListOf<Long>() }
    var estaEncendido by remember { mutableStateOf(false) }
    var tiempo by remember { mutableLongStateOf(0L) }
    var ultimaVuelta by remember { mutableLongStateOf(0L) }
    val minutos = TimeUnit.MILLISECONDS.toMinutes(tiempo)
    val segundos = TimeUnit.MILLISECONDS.toSeconds(tiempo) % 60
    val milliseconds = tiempo % 1000

    LaunchedEffect(estaEncendido) {
        while (estaEncendido){
            delay(10)
            tiempo += 10L
        }
    }

    Column( modifier = modifier.fillMaxSize().padding(20.dp) ) {
        Box( modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center ) {
            Text( text = String.format("%02d:%02d,%02d",minutos,segundos,milliseconds/10), style = MaterialTheme.typography.titleLarge )
        }
        if (estaEncendido){
            Row( modifier = Modifier.fillMaxWidth() ) {
                Button(
                    onClick = {
                        listaDeTiempos.add(tiempo-ultimaVuelta)
                        ultimaVuelta = tiempo
                    }
                ) { Text("Lap") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        estaEncendido = false
                    }
                ) { Text("Stop") }
            }
        } else {
            Row( modifier = Modifier .fillMaxWidth() ) {
                Button(
                    onClick = {
                        tiempo = 0
                        ultimaVuelta = 0
                        listaDeTiempos.clear()
                    }
                ) { Text("Reset") }

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        estaEncendido = true
                    }
                ) { Text("Start") }
            }
        }

        LazyColumn( modifier = Modifier.padding(top = 20.dp).weight(1f).fillMaxWidth() ) {
            items(listaDeTiempos){ tiempo ->
                Row (modifier = Modifier.fillMaxWidth()) {
                    Text("Lap")
                    Spacer(modifier.weight(1f))
                    val minutos = TimeUnit.MILLISECONDS.toMinutes(tiempo)
                    val segundos = TimeUnit.MILLISECONDS.toSeconds(tiempo) % 60
                    val milliseconds = tiempo % 1000
                    val texto = String.format("%02d:%02d,%02d",minutos,segundos,milliseconds/10)
                    Text(texto)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CronometroViewPreview() {
    CronometroTheme {
        CronometroView()
    }
}