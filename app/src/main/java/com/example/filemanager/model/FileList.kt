package com.example.filemanager.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.io.File

/**
 * 表示文件项的类，用于在文件资源管理器中管理文件信息。
 * @property isSelected 指示文件项是否被选中的标志。
 * @property file 与文件项关联的 [File] 对象，与构造函数参数 `f` 相同。
 * @property details 关于文件的详细信息。
 * @property name 文件的名称。
 */
class FileList(val file: File) {
    var isSelected by mutableStateOf(false)
    var details by mutableStateOf("")
    var name by mutableStateOf("")
}