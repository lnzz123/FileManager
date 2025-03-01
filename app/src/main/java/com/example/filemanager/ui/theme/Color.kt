package com.example.filemanager.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.geometry.Offset

object ThemeColors {
    // 基础色
    val TextFocus = Color(0xFFFF8614)// 文字焦点色
    val GraphicFocus = Color(0xFFCB7231)// 图形焦点色
    val Alert = Color(0xFFD15050)// 警示色
    val Safe = Color(0xFF4FD163)// 安全色

    // 白色透明梯度 (采用 ARGB 格式命名)
    val WhiteHigh = Color(0xDBFFFFFF)  // 86% 不透明度
    val WhiteMedium = Color(0xB3FFFFFF) // 70% 不透明度
    val WhiteLow = Color(0x66FFFFFF)    // 40% 不透明度
    val WhiteDisabled = Color(0x33FFFFFF) // 20% 不透明度

    // 渐变效果
    object Gradients {
        // 焦点渐变 (315° 对角线渐变)
        val Focus = Brush.linearGradient(
            colors = listOf(
                Color(0xFFD6520B),
                Color(0xFFE58E41)
            ),
            start = Offset(0f, 0f),
            end = Offset(1f, 1f)
        )

        // 图标灰白渐变 (270° 垂直渐变)
        val IconGrayscale = Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFFFFFF),
                Color(0xFF9E9E9E)
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )

        // 全局背景渐变（270° 垂直渐变）
        val GlobalBackground = Brush.verticalGradient(
            colors = listOf(
                Color(0xFF131516), // 深灰起始色
                Color(0xFF2C2F32)  // 深灰终止色
            ),
            startY = 0f,
            endY = Float.POSITIVE_INFINITY
        )
    }
}