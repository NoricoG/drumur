package nl.norico.drumur.common.music

import org.slf4j.LoggerFactory
import nl.norico.drumur.common.midi.MidiPlayer

const val BAR_SEPARATOR = " "

class Pattern(val barLength: Int, val midi: MidiPlayer) {
    var bars = emptyArray<Bar>()

    init {
        this.addEmptyBar()
    }

    companion object {
//        private val logger by lazy { LoggerFactory.getLogger(this::class.java) }
        private val logger = LoggerFactory.getLogger(this::class.java)
    }


    override fun toString(): String  {
        return this.bars.map{it.toString()}.joinToString(BAR_SEPARATOR)
    }

    fun play() {
        for (bar in this.bars) {
            bar.play()
        }
    }

    fun addEmptyBar() {
        this.bars = this.bars + Bar(this.barLength, this.midi)
    }

    fun addSound(barIndex: Int, index: Int, sound: Sound) {
        try {
            this.bars[barIndex].add(index, sound)
        } catch (e: ArrayIndexOutOfBoundsException) {
            logger.error("Can't add sound to bar {barIndex} because there are only ${this.bars.size} bars", e)
        }
    }

    fun removeSound(barIndex: Int, index: Int) {
        try {
            this.bars[barIndex].remove(index)
        } catch (e: ArrayIndexOutOfBoundsException) {
            logger.error("Can't remove sound from bar {barIndex} because there are only ${this.bars.size} bars", e)
        }
    }
}
