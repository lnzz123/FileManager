package com.example.filemanager.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.filemanager.component.AppTopBar

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun PasteScreen() {
    var pasteTo by remember { mutableStateOf(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部栏
        AppTopBar("选择粘贴位置")




        when (pasteTo) {
            PasteScreenState.BUILT_IN_STORAGE -> {
            }
            PasteScreenState.USB -> {
            }
            PasteScreenState.HARD_DISK -> {
            }
        }
    }
}
enum class PasteScreenState {
    BUILT_IN_STORAGE,
    USB,
    HARD_DISK,
}