package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.model.MaFile
import java.nio.file.Paths

private const val MAFILES_BASE_DIR = "../maFiles/"

@Composable
@Preview
fun App() {
    val maFiles = MaFileReader.readMaFileDir(Paths.get(MAFILES_BASE_DIR).toAbsolutePath())
    MaterialTheme {
        TwoFactorPlaceholder()
        MaFilesList(maFiles)
    }
}

@Composable
fun TwoFactorPlaceholder() {
    var twoFactorCode by remember { mutableStateOf("") }

    Row {
        Text(
            text = twoFactorCode,
            modifier = Modifier
                .background(Color.White)
                .size(100.dp)
        )
        Button(onClick = { println("Clipboard copying logic goes here") }) {
            Text("Copy")
        }
    }
}

@Composable
fun MaFilesList(maFiles: List<MaFile>) {
    Column(
        modifier = Modifier
            .padding(200.dp)
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        maFiles.forEach {
            Text(
                text = it.accountName,
                textAlign = TextAlign.Center
            )
        }
    }
}