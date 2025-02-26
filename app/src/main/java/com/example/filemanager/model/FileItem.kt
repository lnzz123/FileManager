package com.example.filemanager.model

import java.io.File

/**
 * 表示文件项的类，用于在文件资源管理器中管理文件信息。
 *
 * @property f 与文件项关联的 [File] 对象。
 * @property isSelected 指示文件项是否被选中的标志。
 * @property file 与文件项关联的 [File] 对象，与构造函数参数 `f` 相同。
 * @property details 关于文件的详细信息。
 * @property name 文件的名称。
 */
class FileItem(var f: File) {
    // 标记文件项是否被选中
    @JvmField
    var isSelected = false

    // 存储文件对象
    @JvmField
    var file: File = f

    // 存储文件的详细信息
    var details = ""

    // 存储文件的名称
    var name = ""
}