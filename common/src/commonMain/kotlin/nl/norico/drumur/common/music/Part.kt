package nl.norico.drumur.common.music

import nl.norico.drumur.common.music.Pattern
import nl.norico.drumur.common.music.Track


class Part(val label: String, pattern_length: Int) {
    val tracks = arrayOf(Track("Hi-Hat", 31), Track("Snare", 32), Track("Kick", 33))

    val allPatterns: Array<Pattern> = arrayOf(Pattern("A", tracks.count(),pattern_length), Pattern("B", tracks.count(),8))
    val chosenPatterns: Array<Pattern> = arrayOf(allPatterns[0], allPatterns[1], allPatterns[0], allPatterns[1])


    fun addTrack() {
        // TODO: make sure it is handled well in the patterns
    }

    fun removeTrack() {
        // TODO: make sure it is handled well in the patterns
    }
}