package com.example.filemanager.util

import com.example.filemanager.extension.getStringList
import com.google.gson.Gson
import com.pixplicity.easyprefs.library.Prefs

/**
 * PrefsUtils 对象用于管理应用的各种偏好设置。
 * 这些设置通过 EasyPrefs 库存储在本地，方便应用在不同组件和场景中使用。
 */
object PrefsUtils {
    // 定义文件排序方式的常量
    const val SORT_NAME_A2Z = 1
    const val SORT_NAME_Z2A = 2
    const val SORT_SIZE_SMALLER = 3
    const val SORT_SIZE_BIGGER = 4
    const val SORT_DATE_OLDER = 5
    const val SORT_DATE_NEWER = 6

    /**
     * TextEditor 对象用于管理文本编辑器的偏好设置。
     */
    object TextEditor {
        /**
         * 获取或设置文本编辑器是否启用自动换行。
         * 默认值为 false。
         */
        var textEditorWordwrap: Boolean
            get() = Prefs.getBoolean("text_editor_wordwrap", false)
            set(wordwrap) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_wordwrap", wordwrap)
            }
        /**
         * 获取或设置文本编辑器是否显示行号。
         * 默认值为 true。
         */
        var textEditorShowLineNumber: Boolean
            get() = Prefs.getBoolean("text_editor_show_line_number", true)
            set(showLineNumber) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_show_line_number", showLineNumber)
            }
        /**
         * 获取或设置文本编辑器是否固定行号显示。
         * 默认值为 true。
         */
        var textEditorPinLineNumber: Boolean
            get() = Prefs.getBoolean("text_editor_pin_line_number", true)
            set(pinLineNumber) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_pin_line_number", pinLineNumber)
            }
        /**
         * 获取或设置文本编辑器是否启用放大镜功能。
         * 默认值为 true。
         */
        var textEditorMagnifier: Boolean
            get() = Prefs.getBoolean("text_editor_magnifier", true)
            set(magnifier) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_magnifier", magnifier)
            }
        /**
         * 获取或设置文本编辑器是否为只读模式。
         * 默认值为 false。
         */
        var textEditorReadOnly: Boolean
            get() = Prefs.getBoolean("text_editor_read_only", false)
            set(readOnly) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_read_only", readOnly)
            }
        /**
         * 获取或设置文本编辑器是否启用自动完成功能。
         * 默认值为 false。
         */
        var textEditorAutocomplete: Boolean
            get() = Prefs.getBoolean("text_editor_autocomplete", false)
            set(autocomplete) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("text_editor_autocomplete", autocomplete)
            }
        /**
         * 获取文件浏览器标签页的书签列表。
         * 书签列表以 JSON 字符串形式存储在本地偏好中，通过 getStringList 扩展方法转换为 ArrayList。
         */
        val fileExplorerTabBookmarks: ArrayList<String>
            get() = Prefs.getString("file_explorer_tab_bookmarks", "[]").getStringList()
    }

    /**
     * FileExplorerTab 对象用于管理文件浏览器标签页的偏好设置。
     */
    object FileExplorerTab {
        /**
         * 获取或设置文件浏览器的排序方法。
         * 默认排序方法为按名称升序排列（SORT_NAME_A2Z）。
         */
        @JvmStatic
        var sortingMethod: Int
            get() = Prefs.getInt("sorting_method", SORT_NAME_A2Z)
            set(method) {
                // 将设置保存到本地偏好中
                Prefs.putInt("sorting_method", method)
            }

        /**
         * 设置文件浏览器是否优先显示文件夹。
         * @param b 是否优先显示文件夹。
         */
        fun setListFoldersFirst(b: Boolean) {
            // 将设置保存到本地偏好中
            Prefs.putBoolean("list_folders_first", b)
        }

        /**
         * 获取文件浏览器是否优先显示文件夹。
         * 默认值为 true。
         */
        @JvmStatic
        fun listFoldersFirst(): Boolean {
            return Prefs.getBoolean("list_folders_first", true)
        }
    }

    /**
     * Settings 对象用于管理应用的全局设置。
     */
    object Settings {
        /**
         * 获取或设置深度搜索的文件大小限制。
         * 默认限制为 6MB。
         */
        var deepSearchFileSizeLimit: Long
            get() = Prefs.getLong(
                "settings_deep_search_file_size_limit",
                (6 * 1024 * 1024).toLong()
            )
            set(limit) {
                // 将设置保存到本地偏好中
                Prefs.putLong("settings_deep_search_file_size_limit", limit)
            }

        /**
         * 获取或设置是否显示底部工具栏的标签。
         * 默认值为 true。
         */
        var showBottomToolbarLabels: Boolean
            get() = Prefs.getBoolean("settings_show_bottom_toolbar_labels", true)
            set(showBottomToolbarLabels) {
                // 将设置保存到本地偏好中
                Prefs.putBoolean("settings_show_bottom_toolbar_labels", showBottomToolbarLabels)
            }
    }

    /**
     * General 对象用于管理通用的偏好设置。
     */
    object General {
        /**
         * 设置文件浏览器标签页的书签列表。
         * 书签列表以 JSON 字符串形式存储在本地偏好中。
         * @param list 书签列表。
         */
        fun setFileExplorerTabBookmarks(list: ArrayList<String>) {
            // 使用 Gson 将列表转换为 JSON 字符串并保存到本地偏好中
            Prefs.putString("file_explorer_tab_bookmarks", Gson().toJson(list))
        }
    }
}
