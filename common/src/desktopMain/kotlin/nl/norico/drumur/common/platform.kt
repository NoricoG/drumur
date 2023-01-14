package nl.norico.drumur.common

import nl.norico.drumur.common.midi.DesktopMidiPlayer
import nl.norico.drumur.common.midi.MidiPlayer

actual fun getPlatformName(): String {
    return "Desktop"
}

actual fun getMidiPlayer(): MidiPlayer {
    return DesktopMidiPlayer()
}