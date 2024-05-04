package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.MultipleFilePicker
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
    var dropdownExpanded by remember { mutableStateOf(false) }
    var showFilesPicker by remember { mutableStateOf(false) }
    var showDirPicker by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = START_PADDING, end = END_PADDING, top = 5.dp)
    ) {
        IconButton(
            onClick = { dropdownExpanded = !dropdownExpanded },
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
            )
        }
        DropdownMenu(
            expanded = dropdownExpanded,
            onDismissRequest = { dropdownExpanded = false },
        ) {
            DropdownMenuItem(
                onClick = { showFilesPicker = true; dropdownExpanded = false }
            ) {
                Text(
                    text = "Import maFile"
                )
            }
            DropdownMenuItem(onClick = { showDirPicker = true; dropdownExpanded = false }) {
                Text("Import maFile directory")
            }
        }
        MultipleFilePicker(show = showFilesPicker, fileExtensions = listOf("maFile")) {
            showFilesPicker = false
            // TODO: do something with path
            println(it)
        }
        DirectoryPicker(show = showDirPicker) {
            showDirPicker = false
            // TODO: do something with path
            println(it)
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