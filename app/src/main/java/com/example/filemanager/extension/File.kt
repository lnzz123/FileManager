package com.example.filemanager.extension

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.os.StatFs
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.filemanager.util.FileMimeTypes
import com.example.filemanager.util.FileUtils
import java.io.File
import java.text.SimpleDateFormat

/**
 * 获取文件的详细信息字符串，包含文件的最后修改日期和文件大小或文件夹中的项目数量。
 *
 * @return 包含文件详细信息的字符串。
 */
fun File.getFileDetails(): String {
    // 创建一个 StringBuilder 对象，用于构建文件详细信息字符串
    val sb = StringBuilder()
    // 添加文件的最后修改日期
    sb.append(getLastModifiedDate("yyyy-MM-dd"))
    // 添加分隔符
    sb.append("  |  ")
    // 如果是文件，则添加文件大小
    if (this.isFile) {
        sb.append(length().toFormattedSize())
    } else {
        // 如果是文件夹，则添加文件夹中的项目数量
        sb.append(getFormattedFileCount())
    }
    return sb.toString()
}

/**
 * 获取文件的短标签，可指定最大长度。
 *
 * @param maxLength 标签的最大长度。
 * @return 短标签字符串。
 */
fun File.getShortLabel(maxLength: Int): String {
    // 获取文件路径的最后一段作为文件名
    var name = Uri.parse(this.absolutePath).lastPathSegment
    // 如果是外部存储文件夹，则使用内部存储的标签
    if (isExternalStorageFolder()) {
        name = FileUtils.INTERNAL_STORAGE
    }
    // 如果文件名长度超过最大长度，则进行截断并添加省略号
    if (name!!.length > maxLength) {
        name = name.substring(0, maxLength - 3) + "..."
    }
    return name
}

/**
 * 判断文件是否为外部存储文件夹。
 *
 * @return 如果是外部存储文件夹则返回 true，否则返回 false。
 */
fun File.isExternalStorageFolder(): Boolean {
    // 比较文件的绝对路径是否与外部存储目录的绝对路径相同
    return this.absolutePath == Environment.getExternalStorageDirectory().absolutePath
}

/**
 * 获取文件所在存储设备的可用内存字节数。
 *
 * @return 可用内存字节数。
 */
fun File.getAvailableMemoryBytes(): Long {
    // 创建 StatFs 对象，用于获取存储设备的信息
    val statFs = StatFs(this.absolutePath)
    // 计算可用内存字节数
    return statFs.blockSizeLong * statFs.availableBlocksLong
}

/**
 * 获取文件所在存储设备的总内存字节数。
 *
 * @return 总内存字节数。
 */
fun File.getTotalMemoryBytes(): Long {
    // 创建 StatFs 对象，用于获取存储设备的信息
    val statFs = StatFs(this.absolutePath)
    // 计算总内存字节数
    return statFs.blockSizeLong * statFs.blockCountLong
}

/**
 * 获取文件所在存储设备的已使用内存字节数。
 *
 * @return 已使用内存字节数。
 */
fun File.getUsedMemoryBytes(): Long {
    // 总内存字节数减去可用内存字节数得到已使用内存字节数
    return getTotalMemoryBytes() - getAvailableMemoryBytes()
}

/**
 * 获取文件夹中文件和文件夹的格式化数量信息。
 *
 * @return 包含文件和文件夹数量的格式化字符串。
 */
fun File.getFormattedFileCount(): String {
    // 空文件夹的提示信息
    val noItemsString = "Empty folder"
    // 如果是文件，则返回空文件夹提示信息
    if (this.isFile) {
        return noItemsString
    }
    // 初始化文件和文件夹数量
    var files = 0
    var folders = 0
    // 获取文件夹中的文件列表
    val fileList = this.listFiles() ?: return noItemsString
    // 遍历文件列表，统计文件和文件夹数量
    for (item in fileList) {
        if (item.isFile) files++ else folders++
    }
    // 创建 StringBuilder 对象，用于构建数量信息字符串
    val sb = java.lang.StringBuilder()
    // 如果有文件夹，添加文件夹数量信息
    if (folders > 0) {
        sb.append(folders)
        sb.append(" folder")
        if (folders > 1) sb.append("s")
        if (files > 0) sb.append(", ")
    }
    // 如果有文件，添加文件数量信息
    if (files > 0) {
        sb.append(files)
        sb.append(" file")
        if (files > 1) sb.append("s")
    }
    // 如果文件夹和文件数量都为 0，则返回空文件夹提示信息，否则返回构建好的字符串
    return if (folders == 0 && files == 0) noItemsString else sb.toString()
}

/**
 * 从文件获取 MIME 类型。
 *
 * @return 文件的 MIME 类型。
 */
fun File.getMimeTypeFromFile(): String {
    // 尝试从文件扩展名获取 MIME 类型，如果获取失败则从自定义的 MIME 类型映射中获取
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        ?: getMimeTypeFromExtension()
}

/**
 * 从文件扩展名获取 MIME 类型。
 *
 * @return 文件的 MIME 类型。
 */
fun File.getMimeTypeFromExtension(): String {
    // 从自定义的 MIME 类型映射中获取 MIME 类型，如果没有则返回默认 MIME 类型
    val type = FileMimeTypes.mimeTypes[extension]
    return type ?: FileMimeTypes.default
}

/**
 * 使用指定的方式打开文件。
 *
 * @param context 上下文对象，用于启动 Activity 和显示 Toast
 * @param anonymous 是否使用通用的 MIME 类型打开文件。
 */
fun File.openFileWith(context: Context, anonymous: Boolean) {
    // 创建一个用于打开文件的 Intent
    val i = Intent(Intent.ACTION_VIEW)
    // 获取文件的 URI
    val uri = FileProvider.getUriForFile(
        context, context.packageName + ".provider",
        this
    )
    // 设置 Intent 的数据和类型
    i.setDataAndType(uri, if (anonymous) "*/*" else this.getMimeTypeFromFile())
    // 添加新任务标志
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    // 添加读取 URI 权限标志
    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    // 添加写入 URI 权限标志
    i.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
    try {
        // 启动 Activity 打开文件
        context.startActivity(i)
    } catch (e: ActivityNotFoundException) {
        // 如果没有找到可以打开文件的应用
        if (!anonymous) {
            // 尝试使用通用的 MIME 类型再次打开文件
            this.openFileWith(context, true)
        } else {
            // 显示提示信息
            Toast.makeText(context, "Couldn't find any app that can open this type of files", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        // 显示打开文件失败的提示信息
        Toast.makeText(context, "Failed to open this file", Toast.LENGTH_SHORT).show()
    }
}

/**
 * 递归计算文件夹的大小。
 *
 * @return 文件夹的大小（字节）。
 */
fun File.getFolderSize(): Long {
    // 初始化文件夹大小为 0
    var size: Long = 0
    // 获取文件夹中的文件列表
    val list = listFiles()
    if (list != null) {
        // 遍历文件列表
        for (child in list) {
            size = if (child.isFile) {
                // 如果是文件，累加文件大小
                size + child.length()
            } else {
                // 如果是文件夹，递归计算文件夹大小并累加
                size + child.getFolderSize()
            }
        }
    }
    return size
}

/**
 * 递归获取指定目录下所有具有指定扩展名的文件的绝对路径。
 *
 * @param extension 文件扩展名。
 * @return 包含所有匹配文件绝对路径的 ArrayList。
 */
fun File.getAllFilesInDir(extension: String): ArrayList<String> {
    // 如果文件不存在或不是文件夹，则返回空列表
    if (!exists() || isFile) {
        return ArrayList()
    }
    // 创建一个用于存储匹配文件路径的列表
    val list = arrayListOf<String>()
    // 获取文件夹中的文件列表
    val content = listFiles()
    if (content != null) {
        // 遍历文件列表
        for (file in content) {
            if (file.isFile && file.name.endsWith(".$extension")) {
                // 如果是文件且扩展名匹配，则添加到列表中
                list.add(file.absolutePath)
            } else {
                // 如果是文件夹，则递归调用该方法并将结果添加到列表中
                list.addAll(file.getAllFilesInDir(extension))
            }
        }
    }
    return list
}

/**
 * 获取文件的最后修改日期，并将其格式化为指定的字符串格式。
 *
 * @param REGULAR_DATE_FORMAT 日期格式化的模式，默认为 "MMM dd , hh:mm a"。
 *                            例如，"MMM dd , hh:mm a" 会将日期格式化为 "Jan 01, 12:00 AM" 这样的形式。
 * @return 格式化后的文件最后修改日期字符串。
 */
@SuppressLint("SimpleDateFormat")
fun File.getLastModifiedDate(REGULAR_DATE_FORMAT: String = "MMM dd , hh:mm a"): String {
    // 使用指定的日期格式将文件的最后修改时间戳格式化为字符串
    return SimpleDateFormat(REGULAR_DATE_FORMAT).format(lastModified())
}
