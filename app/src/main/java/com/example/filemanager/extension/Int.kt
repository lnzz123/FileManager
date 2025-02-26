package com.example.filemanager.extension

import android.content.Context
import android.util.TypedValue

/**
 * 将 Int 类型的像素值转换为设备独立像素（dp）值。
 *
 * @param context 应用上下文，用于获取资源显示指标。
 * @return 转换后的 dp 值，四舍五入为 Int 类型。
 */
fun Int.toDp(context: Context): Int = TypedValue.applyDimension(
    // 指定转换的目标单位为 dp
    TypedValue.COMPLEX_UNIT_DIP,
    // 将当前 Int 类型的像素值转换为 Float 类型
    this.toFloat(),
    // 获取传入上下文的资源显示指标，用于适配不同屏幕密度
    context.resources.displayMetrics
    // 将转换结果转换为 Int 类型
).toInt()

/**
 * 将 Float 类型的像素值转换为设备独立像素（dp）值。
 *
 * @param context 应用上下文，用于获取资源显示指标。
 * @return 转换后的 dp 值，保持 Float 类型。
 */
fun Float.toDp(context: Context): Float = TypedValue.applyDimension(
    // 指定转换的目标单位为 dp
    TypedValue.COMPLEX_UNIT_DIP,
    // 当前 Float 类型的像素值
    this,
    // 获取传入上下文的资源显示指标，用于适配不同屏幕密度
    context.resources.displayMetrics
)
