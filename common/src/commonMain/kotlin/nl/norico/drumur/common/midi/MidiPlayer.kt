package nl.norico.drumur.common.midi

import nl.norico.drumur.common.music.Part
import nl.norico.drumur.common.music.Song
import org.slf4j.LoggerFactory

const val VELOCITY_IMPERFECTIONS = 5
const val DURATION_IMPERFECTIONS = 1


abstract class MidiPlayer() {
    val logger = LoggerFactory.getLogger(this::class.java)

    // TODO: use MIDI sequencer for proper timing
    fun playSong(song: Song) {
        var startTime = this.getCurrentTimestamp()

        for (part in song.parts) {
            playPart(part, startTime)
        }
    }

    fun playPart(part: Part, startTime: Long) {
        var currentTime = startTime
        for ((index, track) in part.tracks.withIndex()) {
            for (pattern in part.chosenPatterns) {
                for (trigger in pattern.triggers[index]) {
                    if (trigger.state) {
                        sendNoteOnOff(track.channel, track.note, track.velocity, currentTime, track.duration, false)
                    }
                    currentTime += track.duration
                }
            }
        }
    }

    abstract fun getCurrentTimestamp(): Long

    abstract fun sendNoteOnOff(channel: Int, note: Int, velocity: Int, timestamp: Long, duration: Long, sleep: Boolean)

    fun playNoteNow(channel: Int, note: Int, velocity: Int, duration: Long, sleep: Boolean) {
        sendNoteOnOff(channel, note, velocity, -1, duration, sleep)
    }

    abstract fun panic()

    abstract fun quit()

}

