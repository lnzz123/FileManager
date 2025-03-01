package com.example.filemanager.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ThemeColors.Gradients.GlobalBackground)
            ) {
                content()
            }
        }
    )
}