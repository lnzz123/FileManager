package com.example.filemanager.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.example.filemanager.extension.surroundWithBrackets
import java.io.*
import java.text.SimpleDateFormat

/**
 * 自定义日志工具类，用于将日志信息写入文件。
 */
object Log {
    // 日志标签，用于标识日志来源
    private const val TAG = "CustomLog"

    // 这是一个常用短语，在很多类中使用，表示无法完成某个操作
    const val UNABLE_TO = "Unable to"

    // 这是一个常用短语，在很多类中使用，表示操作过程中出现问题
    const val SOMETHING_WENT_WRONG = "Something went wrong while"

    // 日志文件对象
    private var logFile: File? = null

    /**
     * 初始化日志文件。
     *
     * @param context 应用程序上下文，用于获取外部存储目录。
     */
    fun start(context: Context) {
        // 创建日志文件对象，指定文件路径为外部存储目录下的 debug/log.txt
        logFile = File(context.getExternalFilesDir(null)!!.absolutePath + "/debug/log.txt")
    }

    /**
     * 记录错误日志，使用默认空消息。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param e 异常对象，包含错误信息。
     */
    fun e(tag: String?, e: Exception?) {
        // 调用重载的 e 方法，传入空消息
        e(tag, "", e)
    }

    /**
     * 记录错误日志。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param msg 日志消息，可自定义描述信息。
     * @param throwable 异常对象，包含错误信息，默认为 null。
     */
    @JvmOverloads
    fun e(tag: String?, msg: String, throwable: Throwable? = null) {
        // 调用 write 方法，将错误日志写入文件
        write(tag, "Error", msg, throwable)
    }

    /**
     * 记录警告日志，使用默认空消息。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param e 异常对象，包含警告信息。
     */
    fun w(tag: String?, e: Exception?) {
        // 调用重载的 w 方法，传入空消息
        w(tag, "", e)
    }

    /**
     * 记录警告日志。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param msg 日志消息，可自定义描述信息。
     * @param throwable 异常对象，包含警告信息，默认为 null。
     */
    @JvmOverloads
    fun w(tag: String?, msg: String, throwable: Throwable? = null) {
        // 调用 write 方法，将警告日志写入文件
        write(tag, "Warning", msg, throwable)
    }

    /**
     * 记录调试日志，使用默认空消息。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param e 异常对象，包含调试信息。
     */
    fun d(tag: String?, e: Exception?) {
        // 调用重载的 d 方法，传入空消息
        d(tag, "", e)
    }

    /**
     * 记录调试日志。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param msg 日志消息，可自定义描述信息。
     * @param throwable 异常对象，包含调试信息，默认为 null。
     */
    @JvmOverloads
    fun d(tag: String?, msg: String, throwable: Throwable? = null) {
        // 调用 write 方法，将调试日志写入文件
        write(tag, "Warning", msg, throwable)
    }

    /**
     * 记录信息日志，使用默认空消息。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param e 异常对象，包含信息内容。
     */
    fun i(tag: String?, e: Exception?) {
        // 调用重载的 i 方法，传入空消息
        i(tag, "", e)
    }

    /**
     * 记录信息日志。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param msg 日志消息，可自定义描述信息。
     * @param throwable 异常对象，包含信息内容，默认为 null。
     */
    @JvmOverloads
    fun i(tag: String?, msg: String, throwable: Throwable? = null) {
        // 调用 write 方法，将信息日志写入文件
        write(tag, "Info", msg, throwable)
    }

    /**
     * 获取当前时间的字符串表示。
     *
     * @return 当前时间的字符串，格式为 "MM/dd/yyyy HH:mm:ss.SSS"。
     */
    @get:SuppressLint("SimpleDateFormat")
    private val currentTime: String
        get() = SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS").format(System.currentTimeMillis())

    /**
     * 获取异常的堆栈跟踪信息。
     *
     * @param throwable 异常对象。
     * @return 包含堆栈跟踪信息的字符串。
     */
    fun getStackTrace(throwable: Throwable): String {
        // 创建一个 StringWriter 用于存储堆栈跟踪信息
        val result: Writer = StringWriter()
        // 创建一个 PrintWriter 用于将异常堆栈信息写入 StringWriter
        val printWriter = PrintWriter(result)
        // 将异常堆栈信息打印到 PrintWriter
        throwable.printStackTrace(printWriter)
        // 获取存储在 StringWriter 中的堆栈跟踪信息
        val stacktraceAsString = result.toString()
        // 关闭 PrintWriter
        printWriter.close()
        return stacktraceAsString
    }

    /**
     * 将日志信息写入文件。
     *
     * @param tag 日志标签，用于标识日志来源。
     * @param priority 日志优先级，如 "Error", "Warning", "Info"。
     * @param msg 日志消息，可自定义描述信息。
     * @param throwable 异常对象，包含详细信息，默认为 null。
     */
    private fun write(tag: String?, priority: String, msg: String, throwable: Throwable?) {
        // 检查日志文件是否已初始化
        if (logFile == null) return
        // 检查日志文件的父目录是否存在，如果不存在则尝试创建
        if (!logFile!!.parentFile?.exists()!! && !logFile!!.parentFile?.mkdirs()!!) {
            // 如果创建失败，记录错误日志
            Log.e(TAG, UNABLE_TO + " " + FileUtils.CREATE_FILE + ": " + logFile!!.parentFile)
            return
        }
        try {
            // 检查日志文件是否存在，如果不存在则尝试创建
            if (!logFile!!.exists() && !logFile!!.createNewFile()) {
                // 如果创建失败，记录错误日志
                Log.e(TAG, UNABLE_TO + " " + FileUtils.CREATE_FILE + ": " + logFile)
                return
            }
            // 创建一个 StringBuilder 用于构建日志内容
            val logToWrite = StringBuilder()
            // 如果日志文件已有内容，添加换行符
            if (logFile!!.length() > 0) logToWrite.append(System.lineSeparator())
            // 添加当前时间、日志优先级、日志标签到日志内容
            logToWrite.append(currentTime.surroundWithBrackets())
                .append(priority.surroundWithBrackets())
                .append(tag!!.surroundWithBrackets())
                .append(":").append(" ")
            // 如果有日志消息，添加到日志内容
            if (msg.isNotEmpty()) {
                logToWrite.append(msg)
            }
            // 如果有异常信息，添加异常堆栈跟踪信息到日志内容
            if (throwable != null) {
                logToWrite.append(System.lineSeparator()).append(getStackTrace(throwable))
            }
            // 创建一个 FileWriter 用于将日志内容写入文件
            val fileWriter = FileWriter(logFile, true)
            // 将日志内容写入文件
            fileWriter.write(logToWrite.toString())
            // 刷新缓冲区
            fileWriter.flush()
            // 关闭 FileWriter
            fileWriter.close()
        } catch (e: Exception) {
            // 如果写入文件时发生异常，记录错误日志
            Log.e(TAG, UNABLE_TO + " write to log file" + System.lineSeparator() + e)
            // 同时记录原始的日志信息
            Log.e(tag, msg, throwable)
        }
    }
}