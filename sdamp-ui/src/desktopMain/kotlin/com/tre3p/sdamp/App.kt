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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp),
    ) {
        Row(
            modifier = Modifier
                .height(intrinsicSize = IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .weight(20f)
                    .height(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = twoFactorCodeText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }

            Button(
                onClick = { println("not yet") },
                modifier = Modifier.height(56.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                Text(
                    text = "Copy",
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun MaFilesList(maFiles: List<MaFile>, twoFactorCodeText: String) {
    Column(
        modifier = Modifier
            .padding(200.dp)
            .background(Color.LightGray)
            .verticalScroll(rememberScrollState()),
    ) {
        maFiles.forEach {
            Text(
                text = it.accountName,
                textAlign = TextAlign.Center
                // TODO: assign new code to twoFactorCodeText when clicked
            )
        }
    }
}