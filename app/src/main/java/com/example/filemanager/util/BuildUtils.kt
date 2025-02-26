package com.raival.fileexplorer.tab.file.misc

import android.content.Context
import android.util.Log
import com.example.filemanager.util.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

/**
 * 构建工具类，提供从assets中解压文件和管理特定JAR文件的功能。
 */
object BuildUtils {
    // 定义缓冲区大小为10KB
    private const val BUFFER_SIZE = 1024 * 10
    // 定义日志标签
    private const val TAG = "Decompress"
    /**
     * 获取lambda-stubs.jar文件。
     * 如果文件已存在，则直接返回该文件；否则，从assets中解压对应的ZIP文件。
     *
     * @param context 应用上下文，用于访问文件系统和assets资源
     * @return lambda-stubs.jar文件的引用。
     */
    fun getLambdaStubsJarFile(context: Context): File {
        // 检查lambda-stubs.jar文件是否存在
        val check = File(context.filesDir.toString() + "/build/core-lambda-stubs.jar")
        if (check.exists()) {
            // 如果文件存在，直接返回该文件
            return check
        }
        // 如果文件不存在，从assets中解压对应的ZIP文件
        unzipFromAssets(
            context,
            "build/lambda-stubs.zip",
            check.parentFile?.absolutePath
        )
        // 返回解压后的文件
        return check
    }
    /**
     * 获取rt.jar文件。
     * 首先检查外部存储中的自定义rt.jar文件是否存在；如果不存在，则检查内部存储中的文件；
     * 如果内部存储中的文件也不存在，则从assets中解压对应的ZIP文件。
     *
     * @param context 应用上下文，用于访问文件系统和assets资源
     * @return rt.jar文件的引用。
     */
    fun getRtJarFile(context: Context): File {
        // 检查外部存储中的自定义rt.jar文件是否存在
        val customRt = File(context.getExternalFilesDir(null), "build/rt.jar")
        if (customRt.exists() && customRt.isFile) {
            // 如果文件存在，直接返回该文件
            return customRt
        }
        // 检查内部存储中的rt.jar文件是否存在
        val check = File(context.filesDir.toString() + "/build/rt.jar")
        if (check.exists()) {
            // 如果文件存在，直接返回该文件
            return check
        }
        // 如果文件不存在，从assets中解压对应的ZIP文件
        unzipFromAssets(
            context,
            "build/rt.zip",
            check.parentFile?.absolutePath
        )
        // 返回解压后的文件
        return check
    }


    /**
     * 从assets中解压指定的ZIP文件到指定的目标目录。
     *
     * @param context 应用上下文
     * @param zipFile assets中的ZIP文件路径
     * @param destination 解压目标目录，如果为null或空，则使用应用的files目录
     */
    @JvmStatic
    fun unzipFromAssets(context: Context, zipFile: String, destination: String?) {
        var des = destination
        try {
            // 如果目标目录为null或空，则使用应用的files目录
            if (des == null || des.isEmpty()) des =
                context.filesDir.absolutePath
            // 从assets中打开ZIP文件的输入流
            val stream = context.assets.open(zipFile)
            // 调用unzip方法进行解压
            unzip(stream, des)
        } catch (e: IOException) {
            // 打印异常堆栈信息
            e.printStackTrace()
        }
    }

    /**
     * 检查并创建指定目录。
     *
     * @param destination 根目录
     * @param dir 要检查和创建的子目录
     */
    private fun dirChecker(destination: String?, dir: String) {
        // 创建文件对象
        val f = File(destination, dir)
        if (!f.isDirectory) {
            // 如果目录不存在，则创建目录
            val success = f.mkdirs()
            if (!success) {
                // 如果创建失败，打印警告日志
                Log.w(TAG, "Failed to create folder " + f.name)
            }
        }
    }

    /**
     * 解压输入流中的ZIP文件到指定的目标目录。
     *
     * @param stream ZIP文件的输入流
     * @param destination 解压目标目录
     */
    private fun unzip(stream: InputStream, destination: String?) {
        // 检查并创建根目录
        dirChecker(destination, "")
        // 创建缓冲区
        val buffer = ByteArray(BUFFER_SIZE)
        try {
            // 创建ZIP输入流
            val zin = ZipInputStream(stream)
            var ze: ZipEntry
            // 遍历ZIP文件中的所有条目
            while (zin.nextEntry.also { ze = it } != null) {
                // 打印解压日志
                Log.v(TAG, "Unzipping " + ze.name)
                if (ze.isDirectory) {
                    // 如果条目是目录，则检查并创建该目录
                    dirChecker(destination, ze.name)
                } else {
                    // 如果条目是文件，则创建文件并写入内容
                    val f = File(destination, ze.name)
                    if (!f.exists()) {
                        // 创建文件
                        val success = f.createNewFile()
                        if (!success) {
                            // 如果创建失败，打印警告日志并跳过该文件
                            Log.w(
                                TAG,
                                com.example.filemanager.util.Log.UNABLE_TO + " " + FileUtils.CREATE_FILE + " " + f.name
                            )
                            continue
                        }
                        // 创建文件输出流
                        val fileOutputStream = FileOutputStream(f)
                        var count: Int
                        // 从ZIP输入流中读取数据并写入文件输出流
                        while (zin.read(buffer).also { count = it } != -1) {
                            fileOutputStream.write(buffer, 0, count)
                        }
                        // 关闭ZIP条目
                        zin.closeEntry()
                        // 关闭文件输出流
                        fileOutputStream.close()
                    }
                }
            }
            // 关闭ZIP输入流
            zin.close()
        } catch (e: Exception) {
            // 打印解压异常日志
            Log.e(TAG, "unzip", e)
        }
    }
}
