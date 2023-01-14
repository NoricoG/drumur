import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nl.norico.drumur.common.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
