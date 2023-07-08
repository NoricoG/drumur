package nl.norico.drumur.common

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import nl.norico.drumur.common.app.InterfaceState
import nl.norico.drumur.common.midi.MidiPlayer
import nl.norico.drumur.common.music.Song
import nl.norico.drumur.common.music.Pattern
import nl.norico.drumur.common.music.Part

@Composable
fun App() {
    // TODO: better state so refresh button is not needed anymore
    val midiPlayer by remember { mutableStateOf(getMidiPlayer()) }
    val song by remember { mutableStateOf(Song())}
    val interfaceState by remember { mutableStateOf(InterfaceState()) }

    getInterface(song, midiPlayer, interfaceState)
}

@Composable
fun getInterface(song: Song, midiPlayer: MidiPlayer, interfaceState: InterfaceState) {
    var refreshes by remember { mutableStateOf(0)}
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
    ) {

        Button(onClick = { refreshes += 1 }) {Text("Refresh $refreshes")}

        getNoteButtons(midiPlayer, 9, 27, 87)
        getNoteButtons(midiPlayer, 0, 20, 100)

        Text ("Drumur", Modifier.padding(10.dp), fontSize=30.sp)

        getSongGrid(song, interfaceState, refreshes)

        Button(onClick = {midiPlayer.playSong(song)}) { Text("Play")}
        Button(onClick = {midiPlayer.stop()}) { Text("Stop")}

        getPartEditor(interfaceState.editPart, refreshes, song)
        getSongOptions(song)

    }
}

@Composable
fun getSongGrid(song: Song, interfaceState: InterfaceState, refreshes: Int) {
    Column {
        song.parts.forEach { part ->
            getPartRow(part, interfaceState)
        }
    }
}

@Composable
fun getPartRow(part: Part, interfaceState: InterfaceState) {
    Row {
        Text(part.label, modifier = Modifier.padding(1.dp))

        Button(onClick = {}) { Text("Mute")}
        Button(onClick = {}) { Text("Solo")}
        Button(onClick = {interfaceState.editPart = part}) { Text("Edit")}
        for (index in part.chosenPatterns.indices) {
            getPatternPicker(index, part)
        }
    }
}

@Composable
fun getPatternPicker(index: Int, part: Part) {
    var expanded by remember { mutableStateOf(false) }
    Box {
        Button(onClick = { expanded = true }) {
            Text(part.chosenPatterns[index].label)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            part.allPatterns.forEach { pattern ->
                DropdownMenuItem(onClick = {
                    part.chosenPatterns[index] = pattern
                    expanded = false
                }) {
                    Text(pattern.label)
                }
            }
        }
    }
}

@Composable
fun getNoteButtons(midiPlayer: MidiPlayer, track: Int, from: Int, to: Int) {
    val notes = from..to
    Row {
        notes.forEach { note ->
            Button(onClick = {midiPlayer.playNoteNow(track, note, 100, 100, true)}) { Text(note.toString())}
        }
    }
}

@Composable
fun getPartEditor(part: Part?, refreshes: Int, song: Song) {
    if (part != null) {
        Column {
            Row {
                Column {
                    Text(part.label)
                    part.tracks.forEach {
                        Button(onClick = {}) { Text("TODO Track edit") }
                    }
                }
                Row {
                    part.chosenPatterns.forEach {
                        getPatternGrid(it)
                    }
                }
            }

            Button(onClick = {}) { Text("Part property 1") }
            Button(onClick = {}) { Text("Part property 2") }
        }
    } else {
        Text("Select a part to edit")
    }
}

@Composable
fun getPatternGrid(pattern: Pattern) {
    Column {
        Text (pattern.label)
        for (track in pattern.triggers) {
            Row {
                track.forEach { trigger ->
                    Button(onClick = { trigger.state = !trigger.state }) {
                        if (trigger.state) {
                            Text("X")
                        } else
                            Text("")
                    }
                }
            }
        }
    }
}

@Composable
fun getSongOptions(song: Song) {
    Text("Tempo")
//    TextField(
//        value = song.tempo.toString(),
//        onValueChange = { value: String ->
//            val tempo = value.toFloatOrNull()
//            if (tempo != null && tempo > 0 && tempo < 1000) {
//                song.tempo = tempo
//            }
//        }
//    )
    Button(onClick = { song.tempo *= 2 }) { Text("Double") }
    Button(onClick = { song.tempo /= 2 }) { Text("Half") }

}

expect fun getMidiPlayer(): MidiPlayer