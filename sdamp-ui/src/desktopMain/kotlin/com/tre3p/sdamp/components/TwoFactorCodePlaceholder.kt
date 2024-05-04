package com.tre3p.sdamp.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.START_PADDING

@Composable
fun TwoFactorCodePlaceholder(twoFactorCodeText: MutableState<String>) {
    val clipboardManager = LocalClipboardManager.current

    Row(
        modifier = Modifier.padding(
            start = START_PADDING,
            end = END_PADDING,
            top = 10.dp
        )
    ) {
        Box(
            modifier = Modifier
                .border(BorderStroke(2.dp, Color.LightGray))
                .height(60.dp)
                .weight(40f),
            contentAlignment = Alignment.Center
        ) {
            SelectionContainer {
                Text(
                    text = twoFactorCodeText.value,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Button(
            onClick = { clipboardManager.setText(AnnotatedString(twoFactorCodeText.value)) },
            modifier = Modifier
                .padding(start = 20.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray)
        ) {
            Text("Copy")
        }
    }
}