package com.tre3p.sdamp.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.darkrockstudios.libraries.mpfilepicker.DirectoryPicker
import com.darkrockstudios.libraries.mpfilepicker.MultipleFilePicker
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.START_PADDING
import com.tre3p.sdamp.model.MaFile
import java.nio.file.Paths

@Composable
fun TopPanel(maFileList: SnapshotStateList<MaFile>) {
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
        MultipleFilePicker(show = showFilesPicker, fileExtensions = listOf("maFile")) { paths ->
            showFilesPicker = false

            paths?.forEach {
                maFileList.add(MaFileReader.readMaFile(Paths.get(it.path)))
            }
        }
        DirectoryPicker(show = showDirPicker) {
            showDirPicker = false
            println(it)

            it?.let { dirPath ->
                println(MaFileReader.readMaFileDir(Paths.get(dirPath)).size)
                maFileList.addAll(MaFileReader.readMaFileDir(Paths.get(dirPath)))
            }
        }
    }
}