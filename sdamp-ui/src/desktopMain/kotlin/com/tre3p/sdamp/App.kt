package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.tre3p.sdamp.components.*
import com.tre3p.sdamp.mafile.MaFileDirManager
import com.tre3p.sdamp.mafile.MaFileManager
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.mafile.getTwoFactor
import com.tre3p.sdamp.misc.MAFILES_DIR_PATH
import com.tre3p.sdamp.steam.SteamChunkChangeListener
import com.tre3p.sdamp.model.MaFile
import java.nio.file.Paths

@Composable
@Preview
fun App() {
    val maFileManager = MaFileManager(
        MaFileReader(),
        MaFileDirManager(Paths.get(MAFILES_DIR_PATH))
    )

    val maFiles = maFileManager.loadMaFiles()

    val twoFactorCodeText = remember { mutableStateOf("") }
    val maFilesListState = remember { mutableStateListOf(*maFiles.toTypedArray()) }
    val currentlySelectedMaFile = remember { mutableStateOf<MaFile?>(null) }
    val searchText = remember { mutableStateOf("") }

    initChangeTwoFactorCodeOnSteamChunkChanges(currentlySelectedMaFile, twoFactorCodeText)

    MaterialTheme {
        Column {
            TopPanel(maFilesListState, maFileManager)
            TwoFactorCodePlaceholder(twoFactorCodeText)
            AuthCodeProgressBar()
            MaFileSearchTextField(searchText)
            MaFilesList(maFilesListState, twoFactorCodeText, searchText, currentlySelectedMaFile)
        }
    }
}

private fun initChangeTwoFactorCodeOnSteamChunkChanges(
    currentlySelectedMaFile: MutableState<MaFile?>,
    twoFactorCodeText: MutableState<String>
) {
    SteamChunkChangeListener(
        actionOnChunkChange = {
            if (currentlySelectedMaFile.value != null) {
                twoFactorCodeText.value = currentlySelectedMaFile.value!!.getTwoFactor()
            }
        }
    )
}