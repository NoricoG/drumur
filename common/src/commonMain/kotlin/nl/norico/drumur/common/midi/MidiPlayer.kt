package nl.norico.drumur.common.midi

import nl.norico.drumur.common.music.Part
import nl.norico.drumur.common.music.Song
import org.slf4j.LoggerFactory

const val VELOCITY_IMPERFECTIONS = 5
const val DURATION_IMPERFECTIONS = 1


abstract class MidiPlayer() {
    val logger = LoggerFactory.getLogger(this::class.java)

    abstract fun playSong(song: Song)

    abstract fun playNoteNow(channel: Int, note: Int, velocity: Int, duration: Long, sleep: Boolean)

    abstract fun stop()

    abstract fun panic()

    abstract fun quit()

}

