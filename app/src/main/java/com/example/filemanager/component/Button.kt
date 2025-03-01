package com.example.filemanager.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.filemanager.extension.withAlpha
import com.example.filemanager.ui.theme.ThemeColors

@Composable
fun AppButton(
    text: String? = null,
    state: Int, // 按钮状态
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable RowScope.() -> Unit = {}
) {
    // 根据状态设置按钮样式
    val (buttonColors, buttonElevation) = when (state) {
        ButtonState.NORMAL.ordinal -> Pair(
            ButtonDefaults.buttonColors(Color.White.withAlpha(0.1f)),
            ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
        )
        ButtonState.FOCUSED.ordinal -> Pair(
            ButtonDefaults.buttonColors(ThemeColors.GraphicFocus), // 焦点状态背景色
            ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // 焦点状态阴影
        )
        ButtonState.DISABLED.ordinal -> Pair(
            ButtonDefaults.buttonColors(Color.White.withAlpha(0.05f)), // 失效状态背景色
            ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // 失效状态无阴影
        )
        ButtonState.DISABLED_FOCUSED.ordinal -> Pair(
            ButtonDefaults.buttonColors(ThemeColors.GraphicFocus.withAlpha(0.4f)), // 焦点失效状态背景色
            ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // 焦点失效状态阴影
        )
        else -> Pair(colors, elevation)
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = buttonColors,
        elevation = buttonElevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        // 如果提供了文本，显示文本
        if (text != null) {
            Text(
                text = text,
                color = ThemeColors.WhiteHigh
            )
        }
        // 支持自定义内容
        content()
    }
}

enum class ButtonState {
    NORMAL, // 普通按钮
    FOCUSED, // 焦点按钮
    DISABLED, // 普通失效
    DISABLED_FOCUSED // 焦点失效
}