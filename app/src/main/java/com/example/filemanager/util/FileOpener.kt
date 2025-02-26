package com.example.filemanager.util

import android.content.Context
import android.content.Intent
import com.example.filemanager.MainActivity
import com.example.filemanager.extension.openFileWith
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

/**
 * 该类用于处理文件打开操作，根据文件类型选择不同的打开方式。
 *
 * @param mainActivity 主活动实例，用于启动新的 Activity 或显示对话框。
 */
class FileOpener(private val mainActivity: MainActivity) {
    /**
     * 打开指定的文件。
     *
     * 此方法会尝试处理已知文件扩展名，如果文件扩展名未知，则使用通用的 MIME 类型打开文件。
     *
     * @param file 要打开的文件。
     */
    fun openFile(context: Context,file: File) {
        // 尝试处理已知文件扩展名
        if (!handleKnownFileExtensions(context,file)) {
            // 如果无法处理，则使用通用的 MIME 类型打开文件
            file.openFileWith(context,false)
        }
    }

    /**
     * 处理已知文件扩展名的文件。
     *
     * 此方法会检查文件的扩展名，如果是文本或代码文件，则使用文本编辑器打开；
     * 如果是 APK 文件，则显示安装确认对话框。
     *
     * @param file 要处理的文件。
     * @return 如果文件扩展名已知并成功处理，则返回 true；否则返回 false。
     */
    private fun handleKnownFileExtensions(context: Context,file: File): Boolean {
        // 检查文件扩展名是否为 APK 类型
        if (file.extension == FileMimeTypes.apkType) {
            // 创建一个 Material 风格的警告对话框
            val dialog = MaterialAlertDialogBuilder(mainActivity)
                // 设置对话框的消息内容
               .setMessage("Do you want to install this app?")
                // 设置对话框的确认按钮，并在点击时打开 APK 文件进行安装
               .setPositiveButton("Install") { _, _ -> file.openFileWith(context,false) }
            // 在主线程中启动一个协程
            CoroutineScope(Dispatchers.Main).launch {
                // 设置对话框的标题为 APK 文件的名称
                dialog.setTitle(FileUtils.getApkName(context,file))
                // 设置对话框的图标为 APK 文件的图标
                dialog.setIcon(FileUtils.getApkIcon(context,file))
                // 显示对话框
                dialog.show()
            }
            // 返回 true 表示文件已成功处理
            return true
        }
        // 如果文件扩展名未知，则返回 false
        return false
    }

    companion object {
        // 日志标签，用于标识该类的日志信息
        private val TAG = FileOpener::class.java.simpleName
    }
}
