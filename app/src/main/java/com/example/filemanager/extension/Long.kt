package com.example.filemanager.extension

import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

/**
 * 将 Long 类型的字节数转换为格式化后的文件大小字符串。
 *
 * 此函数会根据字节数的大小，将其转换为合适的单位（如 B、kB、MB 等），并以格式化的字符串形式返回。
 *
 * @return 格式化后的文件大小字符串，例如 "1.2 kB" 或 "2.5 GB"。
 */
fun Long.toFormattedSize(): String {
    // 如果字节数小于等于 0，则直接返回 "0 B"
    if (this <= 0) return "0 B"
    // 定义文件大小的单位数组
    val units = arrayOf("B", "kB", "MB", "GB", "TB")
    // 计算字节数对应的单位索引，通过对数运算确定
    val digitGroups = (log10(this.toDouble()) / log10(1024.0)).toInt()
    // 使用 DecimalFormat 格式化文件大小，保留一位小数
    return DecimalFormat("#,##0.#").format(
        // 将字节数转换为对应的单位大小
        this / 1024.0.pow(digitGroups.toDouble())
    ) + " " + units[digitGroups]
}
