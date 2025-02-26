package com.example.filemanager.util

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Editable
import android.text.TextWatcher
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.filemanager.R
import com.example.filemanager.model.FileItem
import com.example.filemanager.util.PrefsUtils.FileExplorerTab.listFoldersFirst
import com.example.filemanager.util.PrefsUtils.FileExplorerTab.sortingMethod
import java.io.*
import java.util.*


/**
 * 提供文件操作的工具类，包含文件排序、复制、删除、移动、重命名等功能。
 */
object FileUtils {
    // 内部存储的名称常量
    const val INTERNAL_STORAGE = "Internal Storage"

    // 创建文件的操作描述常量
    const val CREATE_FILE = "create file"

    /**
     * 创建一个比较器，用于将文件夹排在文件之前。
     * @return 返回一个比较器，用于比较两个文件对象，文件夹排在文件之前。
     */
    private fun sortFoldersFirst(): Comparator<File> {
        return Comparator { file1: File, file2: File ->
            // 如果file1是文件夹且file2不是文件夹，则file1排在前面
            if (file1.isDirectory && !file2.isDirectory) {
                return@Comparator -1
            }
            // 如果file1不是文件夹且file2是文件夹，则file2排在前面
            else if (!file1.isDirectory && file2.isDirectory) {
                return@Comparator 1
            }
            // 如果两者都是文件夹或者都是文件，则保持原有顺序
            else {
                return@Comparator 0
            }
        }
    }

    /**
     * 创建一个比较器，用于将文件排在文件夹之前。
     * @return 返回一个比较器，用于比较两个文件对象，文件排在文件夹之前。
     */
    private fun sortFilesFirst(): Comparator<File> {
        return Comparator { file2: File, file1: File ->
            // 如果file1是文件夹且file2不是文件夹，则file1排在前面
            if (file1.isDirectory && !file2.isDirectory) {
                return@Comparator -1
            }
            // 如果file1不是文件夹且file2是文件夹，则file2排在前面
            else if (!file1.isDirectory && file2.isDirectory) {
                return@Comparator 1
            }
            // 如果两者都是文件夹或者都是文件，则保持原有顺序
            else {
                return@Comparator 0
            }
        }
    }

    /**
     * 根据用户设置的排序方法和文件夹优先选项，返回一个包含多个比较器的列表。
     * @return 返回一个包含比较器的列表，用于对文件进行排序。
     */
    val comparators: ArrayList<Comparator<File>>
        get() {
            // 创建一个存储比较器的列表
            val list = ArrayList<Comparator<File>>()
            // 根据用户设置的排序方法添加相应的比较器
            when (sortingMethod) {
                PrefsUtils.SORT_NAME_A2Z -> {
                    list.add(sortNameAsc())
                }

                PrefsUtils.SORT_NAME_Z2A -> {
                    list.add(sortNameDesc())
                }

                PrefsUtils.SORT_SIZE_SMALLER -> {
                    list.add(sortSizeAsc())
                }

                PrefsUtils.SORT_SIZE_BIGGER -> {
                    list.add(sortSizeDesc())
                }

                PrefsUtils.SORT_DATE_NEWER -> {
                    list.add(sortDateDesc())
                }

                PrefsUtils.SORT_DATE_OLDER -> {
                    list.add(sortDateAsc())
                }
            }
            // 根据用户设置的文件夹优先选项添加相应的比较器
            if (listFoldersFirst()) {
                list.add(sortFoldersFirst())
            } else {
                list.add(sortFilesFirst())
            }
            return list
        }

    /**
     * 创建一个比较器，用于按文件修改日期升序排序。
     * @return 返回一个比较器，用于按文件修改日期升序排序。
     */
    private fun sortDateAsc(): Comparator<File> {
        return Comparator.comparingLong { obj: File -> obj.lastModified() }
    }

    /**
     * 创建一个比较器，用于按文件修改日期降序排序。
     * @return 返回一个比较器，用于按文件修改日期降序排序。
     */
    private fun sortDateDesc(): Comparator<File> {
        return Comparator { file1: File, file2: File ->
            file2.lastModified().compareTo(file1.lastModified())
        }
    }

    /**
     * 创建一个比较器，用于按文件名升序排序（忽略大小写）。
     * @return 返回一个比较器，用于按文件名升序排序（忽略大小写）。
     */
    private fun sortNameAsc(): Comparator<File> {
        return Comparator.comparing { file: File -> file.name.lowercase(Locale.getDefault()) }
    }

    /**
     * 创建一个比较器，用于按文件名降序排序（忽略大小写）。
     * @return 返回一个比较器，用于按文件名降序排序（忽略大小写）。
     */
    private fun sortNameDesc(): Comparator<File> {
        return Comparator { file1: File, file2: File ->
            file2.name.lowercase(Locale.getDefault()).compareTo(
                file1.name.lowercase(
                    Locale.getDefault()
                )
            )
        }
    }

    /**
     * 创建一个比较器，用于按文件大小升序排序。
     * @return 返回一个比较器，用于按文件大小升序排序。
     */
    private fun sortSizeAsc(): Comparator<File> {
        return Comparator.comparingLong { obj: File -> obj.length() }
    }

    /**
     * 创建一个比较器，用于按文件大小降序排序。
     * @return 返回一个比较器，用于按文件大小降序排序。
     */
    private fun sortSizeDesc(): Comparator<File> {
        return Comparator { file1: File, file2: File ->
            file2.length().compareTo(file1.length())
        }
    }

    /**
     * 检查所选文件列表是否只包含一个文件夹。
     * @param selectedFiles 所选文件列表。
     * @return 如果列表中只有一个文件夹，则返回true；否则返回false。
     */
    fun isSingleFolder(selectedFiles: ArrayList<File>): Boolean {
        return selectedFiles.size == 1 && !selectedFiles[0].isFile
    }

    /**
     * 检查所选文件列表是否只包含一个文件。
     * @param selectedFiles 所选文件列表。
     * @return 如果列表中只有一个文件，则返回true；否则返回false。
     */
    fun isSingleFile(selectedFiles: ArrayList<File>): Boolean {
        return selectedFiles.size == 1 && selectedFiles[0].isFile
    }

    /**
     * 检查所选文件列表是否只包含文件。
     * @param selectedFiles 所选文件列表。
     * @return 如果列表中只包含文件，则返回true；否则返回false。
     */
    fun isOnlyFiles(selectedFiles: ArrayList<File>): Boolean {
        for (file in selectedFiles) {
            if (!file.isFile) return false
        }
        return true
    }

    /**
     * 检查所选文件列表是否只包含存档文件。
     * @param selectedFiles 所选文件列表。
     * @return 如果列表中只包含存档文件，则返回true；否则返回false。
     */
    fun isArchiveFiles(selectedFiles: ArrayList<File>): Boolean {
        for (file in selectedFiles) {
            if (!FileMimeTypes.archiveType.contains(file.extension.lowercase())) return false
        }
        return true
    }

    /**
     * 复制文件或文件夹到指定的目标文件夹。
     * @param fileToCopy 要复制的文件或文件夹。
     * @param destinationFolder 目标文件夹。
     * @param overwrite 是否覆盖已存在的文件或文件夹。
     * @throws Exception 如果要复制的文件或文件夹不存在，则抛出异常。
     */
    fun copy(fileToCopy: File, destinationFolder: File, overwrite: Boolean) {
        if (!fileToCopy.exists()) throw Exception("File " + fileToCopy.absolutePath + " doesn't exist")
        if (fileToCopy.isFile) {
            copyFile(fileToCopy, destinationFolder, overwrite)
        } else copyFolder(fileToCopy, destinationFolder, overwrite)
    }

    /**
     * 复制文件到指定目标文件夹，使用原文件名。
     *
     * @param fileToCopy 要复制的文件。
     * @param destinationFolder 目标文件夹。
     * @param overwrite 是否覆盖已存在的文件。
     */
    fun copyFile(fileToCopy: File, destinationFolder: File, overwrite: Boolean) {
        // 调用另一个 copyFile 方法，使用原文件名进行复制
        copyFile(fileToCopy, fileToCopy.name, destinationFolder, overwrite)
    }

    /**
     * Copy file to a new folder
     *
     * @param fileToCopy:        the file that needs to be copied
     * @param fileName:          The name of the copied file in the destination folder
     * @param destinationFolder: The folder to copy the file into
     * @param overwrite:         Whether or not to overwrite the already existed file in the destination folder
     * @throws Exception: Any errors that occur during the copying process
     */
    fun copyFile(
        fileToCopy: File,
        fileName: String,
        destinationFolder: File,
        overwrite: Boolean
    ) {
        // 检查目标文件夹是否存在，如果不存在则尝试创建
        if (!destinationFolder.exists() && !destinationFolder.mkdirs()) {
            // 如果创建失败，抛出异常
            throw Exception(Log.UNABLE_TO + " create folder: " + destinationFolder)
        }
        // 创建目标文件对象
        val newFile = File(destinationFolder, fileName)
        // 如果目标文件已存在且不允许覆盖，则直接返回
        if (newFile.exists() && !overwrite) return
        // 如果目标文件不存在且创建失败，抛出异常
        if (!newFile.exists() && !newFile.createNewFile()) {
            throw Exception(Log.UNABLE_TO + " " + CREATE_FILE + ": " + newFile)
        }
        // 创建输入流，用于读取要复制的文件
        val fileInputStream = FileInputStream(fileToCopy)
        // 创建输出流，用于写入目标文件
        val fileOutputStream = FileOutputStream(newFile, false)
        // 创建缓冲区，用于提高读写效率
        val buff = ByteArray(1024)
        var length: Int
        // 循环读取文件内容并写入目标文件
        while (fileInputStream.read(buff).also { length = it } > 0) {
            fileOutputStream.write(buff, 0, length)
        }
        // 关闭输入流
        fileInputStream.close()
        // 关闭输出流
        fileOutputStream.close()
    }

    /**
     * 复制文件夹到指定目标文件夹，使用原文件夹名。
     *
     * @param folderToCopy 要复制的文件夹。
     * @param destinationFolder 目标文件夹。
     * @param overwrite 是否覆盖已存在的文件。
     */
    fun copyFolder(folderToCopy: File, destinationFolder: File, overwrite: Boolean) {
        // 调用另一个 copyFolder 方法，使用原文件夹名进行复制
        copyFolder(folderToCopy, folderToCopy.name, destinationFolder, overwrite)
    }

    /**
     * Copy folder to another new folder
     *
     * @param folderToCopy:      the folder that needs to be copied
     * @param folderName:        The name of the copied folder in the destination folder
     * @param destinationFolder: The folder to copy into
     * @param overwrite:         Whether or not to overwrite the already existed files in the destination folder
     * @throws Exception: Any errors that occur during the copying process
     */
    fun copyFolder(
        folderToCopy: File,
        folderName: String,
        destinationFolder: File,
        overwrite: Boolean
    ) {
        // 创建目标文件夹对象
        val newFolder = File(destinationFolder, folderName)
        // 检查目标文件夹是否存在，如果不存在则尝试创建
        if (!newFolder.exists() && !newFolder.mkdirs()) {
            // 如果创建失败，抛出异常
            throw Exception(Log.UNABLE_TO + " create folder: " + newFolder)
        }
        // 检查目标文件夹是否为文件，如果是则抛出异常
        if (newFolder.isFile) {
            throw Exception(
                """${Log.UNABLE_TO} create folder: $newFolder.
A file with the same name exists."""
            )
        }
        // 获取要复制的文件夹中的所有文件和子文件夹
        val folderContent = folderToCopy.listFiles()
        if (folderContent != null) {
            // 遍历文件夹中的所有文件和子文件夹
            for (file in folderContent) {
                if (file.isFile) {
                    // 如果是文件，则调用 copyFile 方法进行复制
                    copyFile(file, file.name, newFolder, overwrite)
                } else {
                    // 如果是文件夹，则递归调用 copyFolder 方法进行复制
                    copyFolder(file, file.name, newFolder, overwrite)
                }
            }
        }
    }

    /**
     * 删除指定文件或文件夹。
     *
     * @param file 要删除的文件或文件夹。
     * @throws Exception 如果文件或文件夹不存在或删除失败，抛出异常。
     */
    fun deleteFile(file: File) {
        // 检查文件或文件夹是否存在，如果不存在则抛出异常
        if (!file.exists()) {
            throw Exception("File $file doesn't exist")
        }
        // 如果是文件夹
        if (!file.isFile) {
            // 获取文件夹中的所有文件和子文件夹
            val fileArr = file.listFiles()
            if (fileArr != null) {
                // 遍历文件夹中的所有文件和子文件夹
                for (subFile in fileArr) {
                    if (subFile.isDirectory) {
                        // 如果是子文件夹，则递归调用 deleteFile 方法进行删除
                        deleteFile(subFile)
                    }
                    if (subFile.isFile) {
                        // 如果是文件，则尝试删除，如果删除失败则抛出异常
                        if (!subFile.delete()) throw Exception(Log.UNABLE_TO + " delete file: " + subFile)
                    }
                }
            }
        }
        // 尝试删除文件或文件夹，如果删除失败则抛出异常
        if (!file.delete()) throw Exception(Log.UNABLE_TO + " delete file: " + file)
    }

    /**
     * 删除指定的文件列表。
     *
     * @param selectedFiles 要删除的文件列表。
     */
    fun deleteFiles(selectedFiles: ArrayList<File>) {
        // 遍历文件列表，调用 deleteFile 方法删除每个文件
        for (file in selectedFiles) {
            deleteFile(file)
        }
    }

    /**
     * 移动文件或文件夹到指定目标位置。
     *
     * @param file 要移动的文件或文件夹。
     * @param destination 目标位置。
     * @throws IOException 如果移动失败，抛出异常。
     */
    fun move(file: File, destination: File?) {
        if (file.isFile) {
            // 如果是文件，尝试重命名文件到目标位置，如果失败则抛出异常
            if (!file.renameTo(File(destination, file.name))) {
                throw IOException("Failed to move file: " + file.absolutePath)
            }
        } else {
            // 如果是文件夹，创建目标文件夹
            val parent = File(destination, file.name)
            if (parent.mkdir()) {
                // 获取文件夹中的所有文件和子文件夹
                val files = file.listFiles()
                if (files != null) {
                    // 遍历文件夹中的所有文件和子文件夹，递归调用 move 方法进行移动
                    for (child in files) {
                        move(child, parent)
                    }
                }
                // 移动完成后，删除原文件夹，如果删除失败则抛出异常
                if (!file.delete()) {
                    throw IOException("Failed to delete file: " + file.absolutePath)
                }
            } else {
                // 如果目标文件夹创建失败，则抛出异常
                throw IOException("Failed to create folder: $parent")
            }
        }
    }


    /**
     * 检查文件名是否有效。
     *
     * @param name 要检查的文件名。
     * @return 如果文件名不为空且不包含无效字符，则返回 true；否则返回 false。
     */
    fun isValidFileName(name: String): Boolean {
        // 若文件名为空，返回 false；否则检查是否包含无效字符
        return if (name.isEmpty()) false else !hasInvalidChar(
            name
        )
    }

    /**
     * 检查字符串中是否包含无效字符。
     *
     * @param name 要检查的字符串。
     * @return 如果包含无效字符，则返回 true；否则返回 false。
     */
    private fun hasInvalidChar(name: String): Boolean {
        // 遍历字符串中的每个字符
        for (ch in name.toCharArray()) {
            // 检查字符是否为常见的无效字符
            when (ch) {
                '"', '*', '/', ':', '>', '<', '?', '\\', '|', '\n', '\t', 0x7f.toChar() -> {
                    return true
                }

                else -> {}
            }
            // 检查字符的 ASCII 码是否小于等于 0x1f
            if (ch.code <= 0x1f) return true
        }
        return false
    }

    /**
     * 重命名文件。
     *
     * @param file 要重命名的文件。
     * @param newName 新的文件名。
     * @return 如果重命名成功，则返回 true；否则返回 false。
     */
    fun rename(file: File, newName: String): Boolean {
        // 尝试将文件重命名为新名称
        return file.renameTo(File(file.parentFile, newName))
    }

    /**
     * 分享文件列表。
     *
     * @param context 上下文对象，用于获取资源和启动意图
     * @param filesToShare 要分享的文件列表。
     * @param activity 当前活动，用于启动分享意图。
     */
    fun shareFiles(context: android.content.Context, filesToShare: ArrayList<File>, activity: Activity) {
        // 如果文件列表中只有一个文件
        if (filesToShare.size == 1) {
            val file = filesToShare[0]
            // 如果该文件是文件夹，提示文件夹不能分享并返回
            if (file.isDirectory) {
                Toast.makeText(context, "Folders cannot be shared", Toast.LENGTH_SHORT).show()

                return
            }
            // 获取文件的 URI
            val uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                file
            )
            // 创建分享单个文件的意图
            val intent = Intent(Intent.ACTION_SEND)
            // 添加读取和写入 URI 的权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            // 设置文件的 MIME 类型
            intent.type =
                MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.extension)
            // 添加要分享的文件 URI
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            // 启动分享选择器
            activity.startActivity(Intent.createChooser(intent, "Share file"))
            return
        }
        // 创建分享多个文件的意图
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        // 添加读取和写入 URI 的权限
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        // 设置 MIME 类型为所有类型
        intent.type = "*/*"
        // 创建存储文件 URI 的列表
        val uriList = ArrayList<Uri>()
        // 遍历文件列表
        for (file in filesToShare) {
            // 如果文件是普通文件
            if (file.isFile) {
                // 获取文件的 URI
                val uri = FileProvider.getUriForFile(
                    context,
                    context.packageName + ".provider",
                    file
                )
                // 将 URI 添加到列表中
                uriList.add(uri)
            }
        }
        // 添加要分享的文件 URI 列表
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList)
        // 启动分享选择器
        activity.startActivity(Intent.createChooser(intent, "Share files"))
    }


    /**
     * 根据指定格式获取文件大小的格式化字符串。
     *
     * @param length 文件的大小（以字节为单位）。
     * @param format 格式化字符串，用于指定小数位数。
     * @return 格式化后的文件大小字符串，包含单位（GB、MB、KB 或 B）。
     */
    fun getFormattedSize(length: Long, format: String): String {
        // 如果文件大小大于 1GB
        if (length > 1073741824) return String.format(
            Locale.ENGLISH,
            format,
            length.toFloat() / 1073741824
        ) + "GB"
        // 如果文件大小大于 1MB
        if (length > 1048576) return String.format(
            Locale.ENGLISH,
            format,
            length.toFloat() / 1048576
        ) + "MB"
        // 如果文件大小大于 1KB
        return if (length > 1024) String.format(
            Locale.ENGLISH,
            format,
            length.toFloat() / 1024
        ) + "KB"
        // 如果文件大小小于等于 1KB，则直接返回以字节为单位的大小
        else length.toString() + "B"
    }

    /**
     * 获取文件大小的格式化字符串，默认保留两位小数。
     *
     * @param length 文件的大小（以字节为单位）。
     * @return 格式化后的文件大小字符串，包含单位（GB、MB、KB 或 B）。
     */
    fun getFormattedSize(length: Long): String {
        // 调用另一个 getFormattedSize 方法，使用默认的格式化字符串 "%.02f"
        return getFormattedSize(length, "%.02f")
    }

    /**
     * 从输入流中读取内容并转换为字符串。
     *
     * @param inputStream 输入流。
     * @return 从输入流中读取的字符串内容。
     */
    fun copyFromInputStream(inputStream: InputStream): String {
        // 创建一个 ByteArrayOutputStream 用于存储从输入流中读取的数据
        val outputStream = ByteArrayOutputStream()
        // 创建一个缓冲区，大小为 1024 字节
        val buf = ByteArray(1024)
        // 用于存储每次读取的字节数
        var i: Int
        try {
            // 循环读取输入流中的数据，直到读取到流的末尾（返回 -1）
            while (inputStream.read(buf).also { i = it } != -1) {
                // 将读取的数据写入输出流
                outputStream.write(buf, 0, i)
            }
            // 关闭输出流
            outputStream.close()
            // 关闭输入流
            inputStream.close()
        }
        // 忽略读取过程中可能出现的 IOException
        catch (ignored: IOException) {
        }
        // 将输出流中的数据转换为字符串并返回
        return outputStream.toString()
    }

       /**
     * 获取 APK 文件的图标。
     *
     * @param context 上下文对象，用于获取包管理器和资源
     * @param file APK 文件。
     * @return APK 文件的图标，如果获取失败则返回默认的未知文件图标。
     */
    fun getApkIcon(context: android.content.Context, file: File): Drawable? {
        // 获取 APK 文件的包信息
        val pi = context.packageManager.getPackageArchiveInfo(file.absolutePath, 0)
        return if (pi != null) {
            // 设置应用信息的源目录和公共源目录
            pi.applicationInfo?.sourceDir = file.absolutePath
            pi.applicationInfo?.publicSourceDir = file.absolutePath
            // 加载 APK 文件的图标
            pi.applicationInfo?.loadIcon(context.packageManager)
        }
        // 如果获取包信息失败，则返回默认的未知文件图标
        else {
            ContextCompat.getDrawable(context, R.drawable.unknown_file_extension)
        }
    }


    /**
     * 获取 APK 文件的名称。
     *
     * @param context 上下文对象，用于获取包管理器
     * @param file APK 文件。
     * @return APK 文件的名称，如果获取失败则返回 null。
     */
    fun getApkName(context: android.content.Context, file: File): String? {
        // 获取 APK 文件的包信息
        val pi = context.packageManager.getPackageArchiveInfo(file.absolutePath, 0)
        return if (pi != null) {
            // 获取 APK 文件的应用标签并转换为字符串
            pi.applicationInfo?.let { appInfo ->
                context.packageManager.getApplicationLabel(appInfo).toString()
            }
        }
        // 如果获取包信息失败，则返回 null
        else {
            null
        }
    }

}