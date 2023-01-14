package nl.norico.drumur.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import nl.norico.drumur.common.app.PatternGenerator
import nl.norico.drumur.common.app.FiniteDrumMachine
import nl.norico.drumur.common.midi.MidiPlayer

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }
    val platformName = getPlatformName()

    val midiPlayer by remember { mutableStateOf(getMidiPlayer()) }
    val patternGenerator by remember { mutableStateOf(PatternGenerator(midiPlayer)) }
    val fdm by remember { mutableStateOf(FiniteDrumMachine()) }

    Column {
        for (action in fdm.currentState.actions) {
            Button(onClick = { fdm.execute(patternGenerator, action) }) {
                Text(action.name)
            }
        }
    }
}

expect fun getMidiPlayer(): MidiPlayer