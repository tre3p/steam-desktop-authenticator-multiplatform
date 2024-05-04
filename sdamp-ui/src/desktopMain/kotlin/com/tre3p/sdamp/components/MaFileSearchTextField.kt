package com.tre3p.sdamp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tre3p.sdamp.misc.END_PADDING
import com.tre3p.sdamp.misc.START_PADDING

@Composable
fun MaFileSearchTextField(searchTextState: MutableState<String>) {
    TextField(
        value = searchTextState.value,
        onValueChange = { searchTextState.value = it },
        placeholder = { Text("Search") },
        colors = TextFieldDefaults.textFieldColors(cursorColor = Color.Black, focusedIndicatorColor = Color.LightGray),
        modifier = Modifier
            .padding(start = START_PADDING, end = END_PADDING, top = 20.dp)
            .fillMaxWidth()
    )
}