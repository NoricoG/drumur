package nl.norico.drumur.common.midi

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import javax.sound.midi.*

class DesktopMidiPlayer : MidiPlayer {
    var synth: Synthesizer
    var rcvr: Receiver
    var timeStamp: Long = 0
     var channel = 9 // percussion
//    var channel = 0

    init {
        // TODO: not hardcoded
        val soundfont: Soundbank = MidiSystem.getSoundbank(File("/Users/Norico.Groeneveld/soundfonts/Compifont_13082016.sf2").inputStream())
        val devInfo = MidiSystem.getMidiDeviceInfo()
//    for (device in devInfo) {
//        println(device)
//    }

        val seq = MidiSystem.getSequencer()

//        synth = MidiSystem.getSynthesizer()
//        synth?.let {

        this.synth = MidiSystem.getSynthesizer()
        if (!(this.synth.isOpen)) {
            this.synth.open()
        }
        timeStamp = this.synth.getMicrosecondPosition()

//        val soundbank = this.synth.getDefaultSoundbank()
//        println(soundbank.getDescription())

        this.synth.loadAllInstruments(soundfont)
        println(this.synth.isSoundbankSupported(soundfont))
        val instList = this.synth.getAvailableInstruments()
//        println("instruments:")
//        for (instrument in instList) {
//            println(instrument)
//        }
        this.synth.loadInstrument(instList[3])

        this.rcvr = synth.getReceiver()
    }

    override fun playNote(note: Int, velocity: Int, duration: Long, sleep: Boolean, imperfections: Boolean) {
        var velocity = velocity
        var duration = duration
        if (imperfections) {
            velocity += imperfection(VELOCITY_IMPERFECTIONS)
            // TODO: make sure that duration imperfections don't add up
            duration += imperfection(DURATION_IMPERFECTIONS)
        }

        this.rcvr.send(ShortMessage(ShortMessage.NOTE_ON, this.channel, note, velocity), timeStamp)
        if (sleep) {
            Thread.sleep(duration)
        }
        this.timeStamp += duration * 1000
        this.rcvr.send(ShortMessage(ShortMessage.NOTE_OFF, this.channel, note, velocity), timeStamp)
    }

    override fun wait(duration: Long, sleep: Boolean) {
        this.timeStamp += duration * 1000
        if (sleep) {
            Thread.sleep(duration)
        }
    }

    override fun quit() {
        this.rcvr.send(ShortMessage(ShortMessage.STOP, this.channel, 0, 0), timeStamp)
        this.rcvr.close()
        this.synth.close()
    }

}
