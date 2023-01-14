package nl.norico.drumur.common.midi

import kotlin.random.Random

const val VELOCITY_IMPERFECTIONS = 5
const val DURATION_IMPERFECTIONS = 1

interface MidiPlayer {
    fun imperfection(amplitude: Int): Int {
        return Random.nextInt(2 * amplitude + 1) - amplitude
    }

    fun playNote(note: Int, velocity: Int, duration: Long, sleep: Boolean = true, imperfections: Boolean = true)

    fun wait(duration: Long, sleep: Boolean = true)

    fun quit()
}

