package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.components.*
import com.tre3p.sdamp.mafile.MaFileDirManager
import com.tre3p.sdamp.mafile.MaFileManager
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.MAFILES_DIR_PATH
import com.tre3p.sdamp.misc.START_PADDING
import java.nio.file.Paths

@Composable
@Preview
fun App() {
    val maFileManager = initMaFileManager()
    val maFiles = maFileManager.loadMaFiles()

    val twoFactorCodeText = remember { mutableStateOf("") }
    val maFilesListState = remember { mutableStateListOf(*maFiles.toTypedArray()) }
    val searchText = remember { mutableStateOf("") }

    MaterialTheme {
        Column {
            TopPanel(maFilesListState, maFileManager)
            TwoFactorCodePlaceholder(twoFactorCodeText)
            AuthCodeProgressBar()
            MaFileSearchTextField(searchText)
            MaFilesList(maFilesListState, twoFactorCodeText, searchText)
        }
    }
}

private fun initMaFileManager(): MaFileManager {
    return MaFileManager(
        MaFileReader(),
        MaFileDirManager(Paths.get(MAFILES_DIR_PATH))
    )
}