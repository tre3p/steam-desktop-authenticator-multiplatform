package com.tre3p.sdamp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.model.MaFile
import java.io.File

private val USER_HOME = System.getProperty("user.home")
private const val MAFILES_DIR_NAME = "/.SDAMP-maFiles"
private val MAFILES_BASE_DIR = File("$USER_HOME$MAFILES_DIR_NAME")
    .also { it.mkdir() }

@Composable
fun App() {
    val maFiles = MaFileReader.readMaFileDir(MAFILES_BASE_DIR.toPath().toAbsolutePath())

    val twoFactorCodeText by remember { mutableStateOf("Test") }

    MaterialTheme {
        TwoFactorCodePlaceholder(twoFactorCodeText)
        MaFilesList(maFiles, twoFactorCodeText)
    }
}

@Composable
fun TwoFactorCodePlaceholder(twoFactorCodeText: String) {

}

@Composable
fun MaFilesList(maFiles: List<MaFile>, twoFactorCodeText: String) {

}