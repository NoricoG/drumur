package nl.norico.drumur.common

import nl.norico.drumur.common.midi.AndroidMidiPlayer
import nl.norico.drumur.common.midi.MidiPlayer

actual fun getPlatformName(): String {
    return "Android"
}

actual fun getMidiPlayer(): MidiPlayer {
    return AndroidMidiPlayer()
}