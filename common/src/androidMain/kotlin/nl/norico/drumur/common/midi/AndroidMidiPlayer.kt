package nl.norico.drumur.common.midi

class AndroidMidiPlayer : MidiPlayer {
//    lateinit var synth: Synthesizer
//    lateinit var rcvr: Receiver
    var timeStamp: Long = 0
    // var channel = 9 // percussion
    var channel = 0

    init {
       println("midi init")
    }

    override fun playNote(note: Int, velocity: Int, duration: Long, sleep: Boolean, imperfections: Boolean) {
        println("midi playNote $note $velocity $duration")
    }

    override fun wait(duration: Long, sleep: Boolean) {
        println("midi wait $duration")
        this.timeStamp += duration * 1000
        if (sleep) {
            Thread.sleep(duration)
        }
    }

    override fun quit() {
        println("midi quit")
    }

}
