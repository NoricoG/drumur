package nl.norico.drumur.common.midi

import nl.norico.drumur.common.music.Song

class AndroidMidiPlayer() : MidiPlayer() {
    init {
       println("midi init")
    }

    override fun playSong(song: Song) {
        TODO("Not yet implemented")
    }

    override fun playNoteNow(channel: Int, note: Int, velocity: Int, duration: Long, sleep: Boolean) {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun quit() {
        TODO("Not yet implemented")
    }

    override fun panic() {
        TODO("Not yet implemented")
    }
}
