package nl.norico.drumur.common.midi

import nl.norico.drumur.common.music.Part
import nl.norico.drumur.common.music.Song
import org.slf4j.LoggerFactory
import java.io.File
import javax.sound.midi.*


class DesktopMidiPlayer() : MidiPlayer()  {
    var synth: Synthesizer
    var rcvr: Receiver
    var seq: Sequencer
    var soundfont: Soundbank? = null
    val useSoundfont = true

    init {
        seq = MidiSystem.getSequencer()
        synth = MidiSystem.getSynthesizer()
        rcvr = synth.receiver
        seq.transmitter.receiver = rcvr

        synth.open()
        seq.open()

        if (useSoundfont) {
            // TODO: nicer way to set path
            val soundfontPath = "/Users/Norico.Groeneveld/soundfonts/Compifont_13082016.sf2"
            soundfont = MidiSystem.getSoundbank(File(soundfontPath).inputStream())
            this.synth.loadAllInstruments(soundfont)
            logger.info("Loaded soundfont completely ${this.synth.isSoundbankSupported(soundfont)}")
        } else {
            logger.warn("No soundfont loaded, some percussion notes might not be available")
        }

        val instList = this.synth.availableInstruments
        synth.loadInstrument(instList[3])

        logInfo()
    }

    fun logInfo() {
        val devInfo = MidiSystem.getMidiDeviceInfo()
        for (device in devInfo) {
            logger.info(device.toString())
        }

        logger.info(synth.defaultSoundbank.description)

        val instList = this.synth.getAvailableInstruments()
        logger.info("instruments:")
        for (instrument in instList) {
            logger.info(instrument.toString())
        }


    }

    fun setReverbChorus() {
        // test of reverb and chorus
//        this.rcvr.send(ShortMessage(ShortMessage.CONTROL_CHANGE, this.channel, 91, 127), this.timeStamp)
//        this.rcvr.send(ShortMessage(ShortMessage.CONTROL_CHANGE, this.channel, 93, 127), this.timeStamp)
//        val pp = 0x01.toByte()
//        val vv = 0x7F.toByte()
//        this.rcvr.send(SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, byteArrayOf(0x7F, 0x7F, 0x04, 0x05, 0x01, 0x01, 0x01, 0x01, 0x01, pp, vv), 11), this.timeStamp)
    }

    override fun playSong(song: Song) {
        this.seq.tempoInBPM = song.tempo
        this.seq.sequence = song.toSequence()
        this.seq.start()

        logger.info("Started")
    }

    fun playPart(part: Part, startTime: Long) {
//        var currentTime = startTime
//        for ((index, track) in part.tracks.withIndex()) {
//            for (pattern in part.chosenPatterns) {
//                for (trigger in pattern.triggers[index]) {
//                    if (trigger.state) {
//                        sendNoteOnOff(track.channel, track.note, track.velocity, currentTime, track.duration, false)
//                    }
//                    currentTime += track.duration
//                }
//            }
//        }
    }

    override fun playNoteNow(channel: Int, note: Int, velocity: Int, duration: Long, sleep: Boolean) {
//        val now = this.synth.microsecondPosition
        val now = -1L
        this.rcvr.send(ShortMessage(ShortMessage.NOTE_ON, channel, note, velocity), now)
        this.rcvr.send(ShortMessage(ShortMessage.NOTE_OFF, channel, note, velocity), now + duration)
        logger.info("Sent note to receiver")
    }

    override fun stop() {
        this.seq.stop()
    }

    override fun panic() {
//        this.rcvr.send(ShortMessage(ShortMessage.STOP, channel, 0, 0), timeStamp)
    }

    override fun quit() {
//        this.panic()
        this.rcvr.close()
//        this.synth.close()
    }
}
