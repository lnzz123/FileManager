package com.example.filemanager.util

import com.raival.fileexplorer.tab.file.misc.BuildUtils.unzipFromAssets
import java.io.File

object APKSignerUtils {
    /**
     * 获取 PK8 文件。
     * 如果指定路径下的 PK8 文件已存在，则直接返回该文件；
     * 若不存在，则从 assets 中解压对应的 ZIP 文件。
     *
     * @param context 应用上下文，用于访问文件系统和 assets 资源
     * @return PK8 文件的引用
     */
    fun getPk8(context: android.content.Context): File {
        val check = File(context.filesDir.toString() + "/build/testkey.pk8")
        if (check.exists()) {
            return check
        }
        unzipFromAssets(
            context,
            "build/testkey.pk8.zip",
            check.parentFile?.absolutePath
        )
        return check
    }

    /**
     * 获取 PEM 文件。
     * 如果指定路径下的 PEM 文件已存在，则直接返回该文件；
     * 若不存在，则从 assets 中解压对应的 ZIP 文件。
     *
     * @param context 应用上下文，用于访问文件系统和 assets 资源
     * @return PEM 文件的引用
     */
    fun getPem(context: android.content.Context): File {
        val check = File(context.filesDir.toString() + "/build/testkey.x509.pem")
        if (check.exists()) {
            return check
        }
        unzipFromAssets(
            context,
            "build/testkey.x509.pem.zip",
            check.parentFile?.absolutePath
        )
        return check
    }
}
