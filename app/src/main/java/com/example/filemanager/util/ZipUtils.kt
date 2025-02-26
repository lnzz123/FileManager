package com.example.filemanager.util

import net.lingala.zip4j.ZipFile
import java.io.File

/**
 * 将指定的文件列表压缩成一个ZIP文件。
 *
 * @param filesToCompress 需要压缩的文件列表。
 * @param zipFile 目标ZIP文件，如果为null则可能会引发异常。
 */
fun archive(filesToCompress: ArrayList<File>, zipFile: File?) {
    // 创建一个ZipFile对象，用于操作ZIP文件
    val zip = ZipFile(zipFile)
    // 遍历需要压缩的文件列表
    for (file in filesToCompress) {
        // 检查文件是否为普通文件
        if (file.isFile) {
            // 如果是普通文件，则将其添加到ZIP文件中
            zip.addFile(file)
        } else {
            // 如果是文件夹，则将整个文件夹添加到ZIP文件中
            zip.addFolder(file)
        }
    }
}

/**
 * 从指定的ZIP文件列表中提取文件到指定的目录。
 *
 * @param filesToExtract 需要提取的ZIP文件列表。
 * @param directory 提取文件的目标目录。
 */
fun extract(filesToExtract: ArrayList<File>, directory: File) {
    // 遍历需要提取的ZIP文件列表
    for (file in filesToExtract) {
        // 检查文件是否为普通文件
        if (file.isFile) {
            // 构建输出目录，使用ZIP文件的名称作为目录名
            val output = File(directory, file.name.substring(0, file.name.lastIndexOf(".")))
            // 尝试创建输出目录
            if (output.mkdir()) {
                // 如果目录创建成功，则将ZIP文件中的内容提取到该目录
                ZipFile(file).extractAll(output.absolutePath)
            } else {
                // 如果目录创建失败，则将ZIP文件中的内容提取到指定的目标目录
                ZipFile(file).extractAll(directory.absolutePath)
            }
        }
    }
}
