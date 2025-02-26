package com.example.filemanager.model

import java.io.File

/**
 * 抽象类 Task 表示一个任务，它定义了任务的基本属性和行为。
 * 具体的任务类应该继承这个抽象类并实现其抽象方法。
 */
abstract class Task {
    /**
     * 任务的名称，可能为 null。
     */
    abstract val name: String?
    /**
     * 任务的详细描述，可能为 null。
     */
    abstract val details: String?
    /**
     * 指示任务是否有效的标志。
     */
    abstract val isValid: Boolean

    /**
     * 设置任务的活动目录。
     *
     * @param directory 要设置为活动目录的 [File] 对象。
     */
    abstract fun setActiveDirectory(directory: File)

    /**
     * 启动任务，并在任务更新和完成时通知相应的监听器。
     *
     * @param onUpdate 任务更新时调用的监听器。
     * @param onFinish 任务完成时调用的监听器。
     */
    abstract fun start(onUpdate: OnUpdateListener, onFinish: OnFinishListener)

    /**
     * 任务更新监听器接口，定义了任务更新时的回调方法。
     */
    interface OnUpdateListener {
        /**
         * 当任务更新时调用此方法。
         *
         * @param progress 表示任务进度的字符串。
         */
        fun onUpdate(progress: String)
    }

    /**
     * 任务完成监听器接口，定义了任务完成时的回调方法。
     */
    interface OnFinishListener {
        /**
         * 当任务完成时调用此方法。
         *
         * @param result 表示任务结果的字符串。
         */
        fun onFinish(result: String)
    }
}
