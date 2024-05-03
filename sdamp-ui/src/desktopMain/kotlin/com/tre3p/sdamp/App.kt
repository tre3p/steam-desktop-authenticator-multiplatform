package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.model.MaFile
import java.io.File

private val USER_HOME = System.getProperty("user.home")
private const val MAFILES_DIR_NAME = "/.SDAMP-maFiles"
private val MAFILES_BASE_DIR = File("$USER_HOME$MAFILES_DIR_NAME")
    .also { it.mkdir() }

private val START_PADDING = 20.dp
private val END_PADDING = 20.dp

@Composable
@Preview
fun App() {
    val maFiles = MaFileReader.readMaFileDir(MAFILES_BASE_DIR.toPath().toAbsolutePath())

    val twoFactorCodeText by remember { mutableStateOf("A4D3C2DF") }

    MaterialTheme {
        Column {
            TopButtons()
            TwoFactorCodePlaceholder(twoFactorCodeText)
        }
    }
}

@Composable
fun TopButtons() {
    var showFilePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val isExpanded by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = START_PADDING, end = END_PADDING, top = 5.dp)
    ) {
        Button(
            onClick = { showFilePicker = true },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
        ) {
            Text("Import")
        }
        FilePicker(show = showFilePicker) {
            showFilePicker = false
            // TODO: do something with picked file
            println(it?.path)
        }
    }
}

@Composable
fun TwoFactorCodePlaceholder(twoFactorCodeText: String) {
    Row(
        modifier = Modifier.padding(
            start = START_PADDING,
            end = END_PADDING,
            top = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .border(BorderStroke(1.dp, Color.Black))
                .height(60.dp)
                .weight(40f),
            contentAlignment = Alignment.Center
        ) {
            SelectionContainer {
                Text(
                    text = twoFactorCodeText,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Button(
            onClick = {},
            modifier = Modifier
                .padding(start = 20.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Copy")
        }
    }
}

@Composable
fun MaFilesList(maFiles: List<MaFile>, twoFactorCodeText: String) {

}