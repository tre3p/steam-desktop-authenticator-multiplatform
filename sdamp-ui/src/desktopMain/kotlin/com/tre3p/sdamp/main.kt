package com.tre3p.sdamp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "steam-desktop-authenticator-multiplatform",
    ) {
        App()
    }
}