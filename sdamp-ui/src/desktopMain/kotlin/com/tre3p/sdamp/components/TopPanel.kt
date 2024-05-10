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
import androidx.compose.ui.window.AwtWindow
import com.tre3p.sdamp.mafile.MaFileManager
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.START_PADDING
import com.tre3p.sdamp.model.MaFile
import java.awt.FileDialog
import java.awt.Frame
import java.io.File
import java.nio.file.Paths

@Composable
fun TopPanel(maFileList: SnapshotStateList<MaFile>, maFileManager: MaFileManager) {
    var dropdownExpanded by remember { mutableStateOf(false) }
    var showFilesPicker by remember { mutableStateOf(false) }

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
                Text(text = "Import maFiles")
            }
        }
        if (showFilesPicker) {
            FileDialog(
                allowedExtensions = listOf(".maFile"),
                onCloseRequest = { paths ->
                    showFilesPicker = false

                    paths.let {
                        val importedMaFiles = maFileManager.importMaFiles(it.map { p -> Paths.get(p.path) })
                        maFileList.addAll(importedMaFiles)
                    }
                }
            )
        }
    }
}

@Composable
fun FileDialog(
    onCloseRequest: (pickedFiles: Set<File>) -> Unit,
    allowedExtensions: List<String>
) {
    val parentFrame: Frame? = null

    AwtWindow(
        create = {
            object: FileDialog(parentFrame, "Choose a file", LOAD) {
                override fun setVisible(b: Boolean) {
                    super.setVisible(b)
                    if (b) onCloseRequest(files.toSet())
                }
            }.apply {
                isMultipleMode = true

                setFilenameFilter { _, name ->
                    allowedExtensions.any {
                        name.endsWith(it)
                    }
                }

                file = allowedExtensions.joinToString(";") { "*$it" }
            }
        },
        dispose = FileDialog::dispose
    )
}