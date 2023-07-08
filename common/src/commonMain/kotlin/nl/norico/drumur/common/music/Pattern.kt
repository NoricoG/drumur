package nl.norico.drumur.common.music

class Pattern (val label: String, val track_count: Int, val length: Int) {
    var triggers = Array(track_count) { Array(length) {Trigger() } }
}