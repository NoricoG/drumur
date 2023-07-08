package nl.norico.drumur.common.midi

import org.slf4j.LoggerFactory
import java.io.File
import javax.sound.midi.*


class DesktopMidiPlayer() : MidiPlayer()  {
    var synth: Synthesizer
    var rcvr: Receiver

    init {
        var soundfontPath: String? = null
        var soundfont: Soundbank? = null
        val useSoundfont = false

        if (useSoundfont) {
            logger.info("Loading soundfont")
            soundfontPath = "/Users/Norico.Groeneveld/soundfonts/Compifont_13082016.sf2"
            soundfont = MidiSystem.getSoundbank(File(soundfontPath).inputStream())
            logger.info("Loaded soundfont")
        }

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

//        val soundbank = this.synth.getDefaultSoundbank()
//        println(soundbank.getDescription())

        if (useSoundfont) {
            this.synth.loadAllInstruments(soundfont)
            logger.info("Loaded soundfont completely")
            println(this.synth.isSoundbankSupported(soundfont))
        }

        val instList = this.synth.getAvailableInstruments()
//        println("instruments:")
//        for (instrument in instList) {
//            println(instrument)
//        }
        this.synth.loadInstrument(instList[3])

        this.rcvr = synth.getReceiver()

  }

    fun setReverbChorus() {
        // test of reverb and chorus
//        this.rcvr.send(ShortMessage(ShortMessage.CONTROL_CHANGE, this.channel, 91, 127), this.timeStamp)
//        this.rcvr.send(ShortMessage(ShortMessage.CONTROL_CHANGE, this.channel, 93, 127), this.timeStamp)
//        val pp = 0x01.toByte()
//        val vv = 0x7F.toByte()
//        this.rcvr.send(SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, byteArrayOf(0x7F, 0x7F, 0x04, 0x05, 0x01, 0x01, 0x01, 0x01, 0x01, pp, vv), 11), this.timeStamp)
    }

    // TODO: remove after using sequencer
    override fun getCurrentTimestamp(): Long {
        return this.synth.microsecondPosition
    }

    override fun sendNoteOnOff(channel: Int, note: Int, velocity: Int, timestamp: Long, duration: Long, sleep: Boolean) {
        this.rcvr.send(ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity), timestamp)
        if (sleep) {
            Thread.sleep(duration)
        }
        this.rcvr.send(ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), timestamp + duration)
    }

    override fun panic() {
//        this.rcvr.send(ShortMessage(ShortMessage.STOP, channel, 0, 0), timeStamp)
    }

    override fun quit() {
//        this.panic()
        this.rcvr.close()
        this.synth.close()
    }
}
