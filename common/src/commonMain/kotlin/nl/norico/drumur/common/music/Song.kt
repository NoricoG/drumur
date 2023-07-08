package nl.norico.drumur.common.music

import org.slf4j.LoggerFactory

class Song() {

    val patternLength = 8
    var parts = arrayOf(Part("Drums 1", patternLength), Part("Drums 2", patternLength))

    // TODO: cleanup
    val BeatsPerMinute = 180
    val SoundsPerBeat = 2
    val MillisecondsPerSound = (60 * 1000 / (SoundsPerBeat * BeatsPerMinute)).toLong()
    val BeatsPerBar = 8
    val SoundsPerBar = SoundsPerBeat * BeatsPerBar

    var barCount = 4

//    init {
//
//    }

    companion object {
//        private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun play() {
        // TODO: play everything
    }

    fun addBar() {
        // TODO: make sure it is handled well in the other classes
    }

    fun removeBar() {
        // TODO: make sure it is handled well in the other classes
    }

}
