package nl.norico.drumur.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import nl.norico.drumur.common.app.Action
import nl.norico.drumur.common.app.PatternGenerator
import nl.norico.drumur.common.app.FiniteDrumMachine
import nl.norico.drumur.common.app.State
import nl.norico.drumur.common.midi.MidiPlayer

@Composable
fun App() {
    val platformName = getPlatformName()

    val midiPlayer by remember { mutableStateOf(getMidiPlayer()) }
    val patternGenerator by remember { mutableStateOf(PatternGenerator(midiPlayer)) }
    val fdm by remember { mutableStateOf(FiniteDrumMachine()) }

    var pattern by remember { mutableStateOf(patternGenerator.pattern.toString())}
    var state by remember { mutableStateOf(fdm.getInitialState()) }

    var onClick: (Action) -> Unit = { action ->
        run {
            state = fdm.execute(patternGenerator, action);
            pattern = patternGenerator.pattern.toString()
        }
    }

    getInterface(pattern, state, onClick)
}



@Composable
fun getInterface(pattern: String, state: State, onClick: (Action) -> Unit) {
    Column {
        // argh not dynamic
        getPatternText(pattern)
        for (action in state.actions) {
            getButton(action, onClick)
        }
    }
}

@Composable
fun getPatternText(pattern: String) {
    return Text(
        pattern,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun getButton(action: Action, onClick: (Action) -> Unit) {
    return Button(onClick = { onClick(action) }) {
        Text(action.name)
    }
}


expect fun getMidiPlayer(): MidiPlayer