package nl.norico.drumur.common.music

// TODO: move note to Trigger in case of melodic track?
class Track (val label: String, val note: Int) {
    var channel = 9
    var velocity = 127
    var duration = 500L // TODO: move default value to song?
    var symbol = "X"
}