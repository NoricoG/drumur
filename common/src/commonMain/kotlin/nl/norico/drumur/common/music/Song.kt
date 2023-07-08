package nl.norico.drumur.common.music

import org.slf4j.LoggerFactory
import javax.sound.midi.*
import javax.sound.midi.Track

class Song() {

    val patternLength = 8

    var tracks1 = arrayOf(Track("H", 0, Notes.C3), Track("M", 0, Notes.E3), Track("L", 0, Notes.G3))
    var tracks2 = arrayOf(Track("Hi-Hat", 9, 44), Track("Snare", 9, 39), Track("Kick", 9, 36))
    var parts = arrayOf(Part("Lead", tracks1, patternLength), Part("Drums 2", tracks2, patternLength))
    var resolution = 4 // PPQ
    var defaultDuration = resolution.toLong()

    var tempo = 120f

//    init {
//
//    }

    companion object {
//        private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun toSequence(): Sequence {
        val sequence = Sequence(Sequence.PPQ, resolution)

        var sequence_index = 0
        parts.forEach { part ->
            for ((track_index, track) in part.tracks.withIndex()) {
                var tick = 0L

                sequence.createTrack()
                val sequence_track = sequence.tracks[sequence_index]

                for (pattern in part.chosenPatterns) {
                    for (trigger in pattern.triggers[track_index]) {
                        if (trigger.state) {
                            val messageOn = ShortMessage(ShortMessage.NOTE_ON, track.channel, track.note, track.velocity)
                            val messageOff = ShortMessage(ShortMessage.NOTE_OFF, track.channel, track.note, 0)

                            sequence_track.add(MidiEvent(messageOn, tick))
                            sequence_track.add(MidiEvent(messageOff, tick + defaultDuration))
                        }
                        tick += defaultDuration
                    }
                }
                sequence_index += 1
            }
        }

        return sequence
    }

    fun addBar() {
        // TODO: make sure it is handled well in the other classes
    }

    fun removeBar() {
        // TODO: make sure it is handled well in the other classes
    }
}
