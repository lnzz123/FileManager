package com.example.filemanager.util

import android.content.Context
import android.content.res.Configuration
import android.util.TypedValue
import androidx.annotation.ColorInt
import java.util.*

/**
 * 工具类，包含一些通用的工具方法。
 */
object Utils {
    /**
     * 允许用于生成随机字符串的字符集合。
     */
    private const val ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm_"

    /**
     * 根据给定的属性 ID 从主题中获取颜色值。
     *
     * @param id 颜色属性的资源 ID。
     * @param context 上下文对象，用于访问主题资源。
     * @return 颜色的整数值。
     */
    @ColorInt
    fun getColorAttribute(id: Int, context: Context): Int {
        // 创建一个 TypedValue 对象用于存储解析后的属性值
        val out = TypedValue()
        // 从上下文的主题中解析指定的属性 ID，并将结果存储在 out 中
        context.theme.resolveAttribute(id, out, true)
        // 返回解析后的颜色数据
        return out.data
    }


    /**
     * 生成指定长度的随机字符串。
     *
     * @param sizeOfRandomString 要生成的随机字符串的长度。
     * @return 生成的随机字符串。
     */
    fun getRandomString(sizeOfRandomString: Int): String {
        // 创建一个 Random 对象用于生成随机数
        val random = Random()
        // 创建一个 StringBuilder 对象，初始容量为指定的字符串长度
        val sb = StringBuilder(sizeOfRandomString)
        // 循环指定的次数，每次从允许的字符集合中随机选取一个字符添加到 StringBuilder 中
        for (i in 0 until sizeOfRandomString) {
            sb.append(ALLOWED_CHARACTERS[random.nextInt(ALLOWED_CHARACTERS.length)])
        }
        // 将 StringBuilder 中的内容转换为字符串并返回
        return sb.toString()
    }
}
