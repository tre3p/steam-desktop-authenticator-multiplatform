package com.tre3p.sdamp

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.components.TopPanel
import com.tre3p.sdamp.components.TwoFactorCodePlaceholder
import com.tre3p.sdamp.mafile.MaFileReader
import com.tre3p.sdamp.mafile.getTwoFactor
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.MAFILES_DIR_PATH
import com.tre3p.sdamp.misc.START_PADDING
import com.tre3p.sdamp.model.MaFile

@Composable
@Preview
fun App() {
    val maFiles = MaFileReader.readMaFileDir(MAFILES_DIR_PATH.toPath())

    val twoFactorCodeText = remember { mutableStateOf("") }
    val maFilesListState = remember { mutableStateListOf(*maFiles.toTypedArray()) }
    maFilesListState.add(MaFile("aaasdasdxasdcasdcasdcasdcas", ""))

    MaterialTheme {
        Column {
            TopPanel(maFilesListState)
            TwoFactorCodePlaceholder(twoFactorCodeText)
            // TODO: progress bar
            // TODO: Search text field
            MaFilesList(maFilesListState, twoFactorCodeText)
        }
    }
}

@Composable
fun MaFilesList(maFilesListState: SnapshotStateList<MaFile>, twoFactorCodeText: MutableState<String>) {
    Box(
        modifier = Modifier
            .padding(start = START_PADDING, end = END_PADDING, top = 20.dp, bottom = 20.dp)
            .border(BorderStroke(2.dp, Color.LightGray))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            maFilesListState.forEach {
                Button(
                    onClick = { twoFactorCodeText.value = it.getTwoFactor() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    elevation = null,
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text(
                        text = if (it.accountName.length > 20) it.accountName.substring(0, 18) + "..." else it.accountName,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.Start)
                    )
                }
            }
        }
    }
}