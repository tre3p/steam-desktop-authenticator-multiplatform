package com.tre3p.sdamp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.mafile.getTwoFactor
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.START_PADDING
import com.tre3p.sdamp.model.MaFile

@Composable
fun MaFilesList(
    maFilesListState: SnapshotStateList<MaFile>,
    twoFactorCodeText: MutableState<String>,
    maFileFilterText: MutableState<String>,
) {
    Box(
        modifier = Modifier
            .padding(start = START_PADDING, end = END_PADDING, top = 20.dp, bottom = 20.dp)
            .border(BorderStroke(2.dp, Color.LightGray))
            .fillMaxHeight()
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
        ) {
            maFilesListState.forEach {
                // TODO: change this logic, cause it has performance issues
                if (it.accountName.contains(maFileFilterText.value)) {
                    Button(
                        onClick = { twoFactorCodeText.value = it.getTwoFactor() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = null,
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text(
                            text = if (it.accountName.length > 20) it.accountName.substring(
                                0,
                                18
                            ) + "..." else it.accountName,
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
}