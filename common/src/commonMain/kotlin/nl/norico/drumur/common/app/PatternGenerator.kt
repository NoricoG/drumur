package nl.norico.drumur.common.app

import nl.norico.drumur.common.midi.MidiPlayer
import nl.norico.drumur.common.music.Pattern
import nl.norico.drumur.common.music.Sound
import kotlin.random.Random

class PatternGenerator(val midiPlayer: MidiPlayer) {
    var pattern = Pattern(8, midiPlayer)
    var sounds = emptyArray<Sound>()

    init {
        this.playRandomSound()
        sounds = arrayOf(
            Sound('B', 20),
            Sound('T', 40),
            Sound('S', 60)
        )
    }

    fun playAllSounds() {
        val highestSound = 81
        for (i in 34  until highestSound) {
            this.midiPlayer.playNote(i, 100, 300)
            this.midiPlayer.wait(300)
        }
    }

    fun playInstrument() {
        for (sound in sounds) {
            this.midiPlayer.playNote(sound.midiCode, 100, 500)
        }
        this.midiPlayer.wait(1000)
    }

    fun playPattern(times: Int) {
        for (i in 0  until times) {
            this.pattern.play()
        }
    }

    fun playRandomSound() {
        val note = Random.nextInt(108 - 21) + 21
        this.midiPlayer.playNote(note, 80, 300)
    }

    fun randomInstrument() {
         //normal notes
//         sounds = arrayOf(
//             Sound('B', 30 + Random.nextInt(40 - 30)),
//             Sound('T', 40 + Random.nextInt(50 - 40)),
//             Sound('S', 50 + Random.nextInt(60 - 50)),
//         )

//        // normal notes extended
//        sounds = arrayOf(
//            Sound('B', 21 + Random.nextInt(51 - 21)),
//            Sound('T', 51 + Random.nextInt(81 - 51)),
//            Sound('S', 81 + Random.nextInt(111 - 81))
//        )

        // percussion, on channel 9
        sounds[0].midiCode = 34 + Random.nextInt(50 - 34)
        sounds[1].midiCode = 50 + Random.nextInt(65 - 50)
        sounds[2].midiCode = 65 + Random.nextInt(80 - 65)

        for (sound in sounds) {
            println(sound.symbol + " " + sound.midiCode)
        }

        playInstrument()
    }

    fun randomSound(index: Int) {
        sounds[index].midiCode = 34 + Random.nextInt(80 - 34)
        this.midiPlayer.playNote(sounds[index].midiCode, 100, 200)
    }

    fun randomPatterns() {
        for (i in 0  until 4) {
            randomPattern()
            this.midiPlayer.wait(300)
        }
    }

    fun randomPattern() {
        pattern = Pattern(8, midiPlayer)
        // this.pattern.addEmptyBar()

        val beatSound = this.sounds[Random.nextInt(3)]
        val offBeatSound = this.sounds[Random.nextInt(3)]

        for (barIndex in 0 until this.pattern.bars.count()) {
            // sounds on beat (0 and 4)
            val onBeatProbability = 0.8
            for (i in 0 until this.pattern.barLength step 4) {
                if (Random.nextFloat() < onBeatProbability) {
                    this.pattern.addSound(
                        barIndex,
                        i,
                        beatSound
                    )
                }
            }
            // sounds off beat (2 and 6)
            val offBeatProbability = 0.7
            for (i in 2 until this.pattern.barLength step 4) {
                if (Random.nextFloat() < offBeatProbability) {
                    this.pattern.addSound(
                        barIndex,
                        i,
                        offBeatSound
                    )
                }
            }
            // sounds in between beat (1,3,5,7)
            val probability = 0.3
            for (i in 1  until this.pattern.barLength step 2) {
                if (Random.nextFloat() < probability) {
                    this.pattern.addSound(
                        barIndex,
                        i,
                        this.sounds[Random.nextInt(3)]
                    )
                }
            }
        }


        show()
        playPattern(2)
    }

    fun show() {
        println(this.pattern.toString())
    }

    fun quit() {
        this.midiPlayer.quit()
    }
}
