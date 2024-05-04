package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.tre3p.sdamp.components.TopPanel
import com.tre3p.sdamp.components.TwoFactorCodePlaceholder
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.misc.MAFILES_DIR_PATH
import com.tre3p.sdamp.model.MaFile

@Composable
@Preview
fun App() {
    val maFiles = MaFileReader.readMaFileDir(MAFILES_DIR_PATH.toPath())

    val twoFactorCodeText by remember { mutableStateOf("A4D3C2DF") }

    MaterialTheme {
        Column {
            TopPanel()
            TwoFactorCodePlaceholder(twoFactorCodeText)
        }
    }
}

@Composable
fun MaFilesList(maFiles: List<MaFile>, twoFactorCodeText: String) {

}