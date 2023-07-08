package nl.norico.drumur.common.midi

class AndroidMidiPlayer() : MidiPlayer() {
    init {
       println("midi init")
    }

    override fun getCurrentTimestamp(): Long {
        TODO("Not yet implemented")
    }

    override fun sendNoteOnOff(channel: Int, note: Int, velocity: Int, timestamp: Long, duration: Long, sleep: Boolean) {
        TODO("Not yet implemented")
    }

    override fun quit() {
        TODO("Not yet implemented")
    }

    override fun panic() {
        TODO("Not yet implemented")
    }
}
