package com.example.filemanager.screen

import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.filemanager.R
import com.example.filemanager.component.AppButton
import com.example.filemanager.component.ButtonState
import com.example.filemanager.component.CustomCheckbox
import com.example.filemanager.component.CustomDialog
import com.example.filemanager.component.FileItem
import com.example.filemanager.component.SecondaryActionBar
import com.example.filemanager.component.TipDialog
import com.example.filemanager.extension.getFileDetails
import com.example.filemanager.extension.getUsedMemoryBytes
import com.example.filemanager.extension.openFileWith
import com.example.filemanager.model.FileList
import com.example.filemanager.ui.theme.ThemeColors
import com.example.filemanager.util.FileUtils
import java.io.File


@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun InternalStorageScreen() {
    val context = LocalContext.current
    // 存储空间信息
    var storageInfo by remember { mutableStateOf("Loading...") }
    // 当前目录路径
    var currentPath by remember { mutableStateOf("/") }
    // 存储文件列表
    val fileLists = remember { mutableStateListOf<FileList>() }
    // 存储操作模式
    var operationMode by remember { mutableStateOf(false) }
    // 新增选中文件集合
    val selectedFiles = remember { mutableStateListOf<File>() }
    // 新增弹窗相关状态
    var dialogType by remember { mutableStateOf<DialogType?>(null) }
    var dialogData by remember { mutableStateOf(DialogData()) }
    // 新增刷新标识
    var refreshKey by remember { mutableStateOf(0) }
    // 弹窗控制逻辑
    dialogType?.let { type ->
        // 修改刷新方法
        fun refreshFileList() {
            refreshKey++
        }
        UniversalDialog(
            dialogType = type,
            initialData = dialogData,
            onDismiss = { dialogType = null },
            onConfirm = { data ->
                when (type) {
                    DialogType.NEW_FOLDER -> {
                        val newFolder = File(currentPath, data.currentName)
                        if (!newFolder.exists()) {
                            newFolder.mkdir()
                            Toast.makeText(context, "新建成功", Toast.LENGTH_SHORT).show()
                            // 刷新文件列表
                            refreshFileList()
                        }
                    }
                    DialogType.RENAME -> {
                        data.targetFile?.let { file ->
                            val newFile = File(file.parent, data.currentName)
                            if (file.renameTo(newFile)) {
                                Toast.makeText(context, "重命名成功", Toast.LENGTH_SHORT).show()
                                // 刷新文件列表
                                refreshFileList()
                            }
                        }
                    }
                    DialogType.DELETE -> {
                        var successCount = 0
                        val iterator = selectedFiles.iterator()
                        while (iterator.hasNext()) {
                            val file = iterator.next()
                            if (file.deleteRecursively()) {
                                successCount++
                                iterator.remove()
                            }
                        }
                        if (successCount > 0) {
                            Toast.makeText(context, "成功删除 $successCount 个文件", Toast.LENGTH_SHORT).show()
                            refreshFileList() // 手动刷新文件列表
                            selectedFiles.clear()
                            operationMode = false // 退出多选模式
                        }
                    }
                }
                dialogType = null
            }
        )
    }

    BackHandler(enabled = currentPath != Environment.getExternalStorageDirectory().path) {
        currentPath = File(currentPath).parent ?: Environment.getExternalStorageDirectory().path
    }

    LaunchedEffect (Unit){
        // 更新当前路径为外部存储目录的路径
        currentPath = Environment.getExternalStorageDirectory().path
        // 获取外部存储目录已使用的内存字节数
        val used = Environment.getExternalStorageDirectory().getUsedMemoryBytes()
        // 获取外部存储目录可用的内存字节数
        val available = Environment.getExternalStorageDirectory().usableSpace
        // 格式化存储信息，显示已使用和可用内存
        storageInfo = FileUtils.getFormattedSize(used) +"/"+ FileUtils.getFormattedSize(available)
    }
    /**
     * 当当前路径 currentPath 发生变化时，重新加载文件列表和存储信息
     *
     * @param currentPath 当前显示的文件目录路径
     */
    LaunchedEffect(currentPath,refreshKey) {
        // 清空现有的文件列表
        fileLists.clear()
        // 根据当前路径创建 File 对象
        val dir = File(currentPath)
        // 遍历当前目录下的文件并排序
        dir.listFiles()?.sortedBy { it.name }?.forEach { file ->
            // 创建 FileList 对象并添加到文件列表中
            fileLists.add(FileList(file).apply {
                // 设置文件名
                name = file.name
                // 设置文件详细信息
                details = file.getFileDetails()
                // 打印日志，记录文件、文件名和详细信息
                Log.d("InternalStorageScreen", "file: $file, name: $name, details: $details")
            })
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        // 显示二级操作栏
        SecondaryActionBar(
            leftContent = {
                if (operationMode) {
                    Row (
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        AppButton (
                            text = "取消",
                            state = ButtonState.NORMAL.ordinal,
                            modifier = Modifier
                                .height(40.dp)
                                .width(80.dp),
                            shape = RoundedCornerShape(4.dp),
                            onClick = {
                                operationMode = false
                                fileLists.forEach { it.isSelected = false }
                                selectedFiles.clear()
                            }
                        )
                        CustomCheckbox(
                            text = "全选",
                            checked = selectedFiles.size == fileLists.size,
                            onCheckedChange = { checked ->
                                if (checked) {
                                    selectedFiles.clear()
                                    fileLists.forEach {
                                        it.isSelected = true
                                        selectedFiles.add(it.file)
                                    }
                                } else {
                                    fileLists.forEach { it.isSelected = false }
                                    selectedFiles.clear()
                                }
                            }
                        )
                    }
                } else {
                    Text(
                        text = storageInfo,
                        color = ThemeColors.WhiteMedium,
                        fontSize = 16.sp,
                    )
                }
            },
            //右侧按钮
            rightContent = {
                val btnList1 = listOf(
                    Triple(R.drawable.ic_search, "搜索") { println("搜索按钮点击") },
                    Triple(R.drawable.ic_sort, "排序") { /* 排序功能实现 */ },
                    Triple(R.drawable.ic_add, "新建") {
                        dialogType = DialogType.NEW_FOLDER
                        dialogData = DialogData(currentName = "")
                    },
                    Triple(R.drawable.ic_edit, "多选") {
                        operationMode = !operationMode
                        Log.d("InternalStorageScreen", "operationMode: $operationMode")
                    }
                )
                val btnList2 = listOf(
                    Triple(R.drawable.ic_copy, "复制") { },
                    Triple(R.drawable.ic_cut, "剪切") {
                        Log.d("InternalStorageScreen", "selectedFiles: ${selectedFiles.toList().joinToString { it.name }}")
                    },
                    Triple(R.drawable.ic_rename, "重命名") {
                        selectedFiles.firstOrNull()?.let {
                            dialogType = DialogType.RENAME
                            dialogData = DialogData(
                                currentName = it.name,
                                targetFile = it
                            )
                        }
                    },
                    Triple(R.drawable.ic_favorite, "收藏") { },
                    Triple(R.drawable.ic_delete, "删除") {
                        dialogType = DialogType.DELETE
                        dialogData = DialogData(
                            tipContent = "确认删除 ${selectedFiles.size} 个文件？"
                        )
                    },
                )
                val btn = @Composable {image: Int ,action:String,chick: () -> Unit ->
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = action,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable { chick() }
                        )
                }
                LazyRow(
                    modifier = Modifier
                    .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                    if (operationMode) {
                        itemsIndexed(btnList2) { index, item ->
                            btn(item.first,item.second) {
                                item.third()
                            }
                        }
                    } else {
                        itemsIndexed(btnList1) { index, item ->
                            btn(item.first,item.second) {
                                item.third()
                            }
                        }
                    }
                }
            }
        )
        //文件列表
        LazyColumn(modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 16.dp, vertical = 8.dp)
        , verticalArrangement = Arrangement.spacedBy(16.dp)) {
            itemsIndexed(fileLists) { index, fileList ->
                FileItem(
                    fileLists = fileList,
                    isSelected = fileList.isSelected,
                    operationMode = operationMode,
                    clickable =
                    {
                        if (operationMode) {
                            if (fileList.isSelected) {
                                fileList.isSelected = false
                                selectedFiles.remove(fileList.file)
                            } else {
                                fileList.isSelected = true
                                selectedFiles.add(fileList.file)
                            }
                        } else {
                            // 非多选模式下的点击逻辑
                            if (fileList.file.isDirectory) {
                                currentPath = fileList.file.path
                            } else {
                                // 处理文件点击事件
                                fileList.file.openFileWith(context,false)
                                Log.d("InternalStorageScreen", "File clicked: ${fileList.file.path}")
                            }
                        }
                    },
                    longClickable = {
                        // 长按逻辑
                        if (!operationMode) {
                            operationMode = true
                            fileList.isSelected = true
                            selectedFiles.add(fileList.file)
                        }
                    },
                    onSelectionChange = { checked ->
                        fileList.isSelected = checked
                        if (checked) {
                            selectedFiles.add(fileList.file)  // 添加当前文件到选中集合
                        } else {
                            selectedFiles.remove(fileList.file)  // 从选中集合移除当前文件
                        }
                    }
                )
            }
        }
    }
}

// 弹窗类型枚举
enum class DialogType {
    NEW_FOLDER,
    RENAME,
    DELETE, // 删除类型使用纯文本提示
}

// 弹窗数据类（扩展提示字段）
data class DialogData(
    val currentName: String = "",
    val targetFile: File? = null,
    val tipContent: String = "" // 新增提示文本字段
)

// 通用弹窗组件
@Composable
fun UniversalDialog(
    dialogType: DialogType,
    initialData: DialogData,
    onDismiss: () -> Unit,
    onConfirm: (DialogData) -> Unit
) {
    var currentData by remember { mutableStateOf(initialData) }

    when (dialogType) {
        DialogType.DELETE -> {
            TipDialog(
                showDialog = true,
                title = "删除确认",
                onDismissRequest = onDismiss,
                message = currentData.tipContent, // 直接使用传入的提示文本
                onConfirmText = "确定",
                onDismissText = "取消",
                onConfirm = { onConfirm(currentData) },
                onDismiss = onDismiss
            )
        }
        else -> { // 其他类型保持使用带输入框的CustomDialog
            CustomDialog(
                showDialog = true,
                onDismissRequest = onDismiss,
                title = when (dialogType) {
                    DialogType.NEW_FOLDER -> "新建文件夹"
                    DialogType.RENAME -> "重命名"
                    else -> ""
                },
                content = {
                    Column(Modifier.fillMaxWidth()) {
                        TextField(
                            value = currentData.currentName,
                            onValueChange = { currentData = currentData.copy(currentName = it) },
                            label = { Text(
                                when (dialogType) {
                                    DialogType.NEW_FOLDER -> "文件夹名称"
                                    else -> "新名称"
                                }
                            )}
                        )
                    }
                },
                onConfirmText = when (dialogType) {
                    DialogType.NEW_FOLDER -> "确定"
                    DialogType.RENAME -> "保存"
                    else -> ""
                },
                onDismissText = "取消",
                onConfirm = { onConfirm(currentData) },
                onDismiss = onDismiss
            )
        }
    }
}
