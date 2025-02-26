package com.example.filemanager.extension

import androidx.compose.ui.graphics.Color

/**
 * 为 Color 添加透明度扩展函数
 * @param alpha 透明度值（0.0 - 1.0）
 * 示例：Color.Red.withAlpha(0.5f)
 */
fun Color.withAlpha(alpha: Float): Color {
    return this.copy(alpha = alpha.coerceIn(0f, 1f))
}

/**
 * 百分比透明度版本（可选）
 * @param percent 透明度百分比（0 - 100）
 * 示例：Color.Blue.transparent(30) // 30% 透明度
 */
fun Color.transparent(percent: Int): Color {
    val alpha = (percent.coerceIn(0, 100) / 100f).coerceIn(0f, 1f)
    return this.copy(alpha = alpha)
}