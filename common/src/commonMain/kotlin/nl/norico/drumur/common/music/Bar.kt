package nl.norico.drumur.common.music

import org.slf4j.LoggerFactory
import nl.norico.drumur.common.midi.MidiPlayer

class Bar(val length: Int, val midi: MidiPlayer) {
    val NoSoundSymbol = '.'
    val Duration = 100L

    var sounds = arrayOfNulls<Sound?>(length)

    companion object {
        val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun toString(): String {
        val symbols = this.sounds.map { it?.symbol ?: NoSoundSymbol}
        return symbols.joinToString("")
    }

    fun play() {
        var currentSound: Sound? = null
        var currentDuration = 0L

        for (sound in this.sounds) {
            // if there is a sound, keep track of it
            if (sound != null) {
                if (currentSound != null) {
                    // play the old sound before replacing it with the new sound
                    this.midi.playNote(currentSound.midiCode, 100, currentDuration)
                } else {
                    // play the silence until the first sound starts
                    this.midi.wait(currentDuration)
                }
                currentSound = sound
                currentDuration = Duration
            } else {
                // if there is no sound, extend the previous sound (or silence)
                currentDuration += Duration
            }
        }

        // TODO: DRY, is same as above
        if (currentSound != null) {
            // play sound until end of beat
            this.midi.playNote(currentSound.midiCode, 100, currentDuration)
        } else {
            // wait until end of beat
            this.midi.wait(currentDuration)
        }
    }

    fun add(index: Int, sound: Sound) {
        try {
            this.sounds[index] = sound
        } catch (e: ArrayIndexOutOfBoundsException) {
            logger.error("Can't add sound at index {index} because there are only ${this.sounds.size} sounds", e)
        }

    }

    fun remove(index: Int) {
        try {
            this.sounds[index] = null
        } catch (e: ArrayIndexOutOfBoundsException) {
            logger.error("Can't remove sound at index {index} because there are only ${this.sounds.size} sounds", e)
        }

    }
}
