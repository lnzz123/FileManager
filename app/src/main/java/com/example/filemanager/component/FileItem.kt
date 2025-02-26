package com.example.filemanager.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.filemanager.R
import com.example.filemanager.extension.withAlpha
import com.example.filemanager.ui.theme.AppColors
import java.io.File

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun FileItem(){
    Row (modifier = Modifier.fillMaxWidth().height(80.dp), verticalAlignment = Alignment.CenterVertically) {
        CustomCheckbox(
            checked = false,
            onCheckedChange = {  }
        )
        Spacer(modifier = Modifier.width(8.dp))
        Card(
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.withAlpha(0.07f)),
        ){
            Box(modifier = Modifier.fillMaxSize().padding(end = 24.dp))
            {
                Row (
                    modifier = Modifier.fillMaxHeight().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically)
                {
                    FileIcon(modifier = Modifier.fillMaxHeight(), isDirectory = true)
                    Spacer(modifier = Modifier.width(16.dp))
                    Column (modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                        Text(text = "文件名称", fontSize = 16.sp, color = AppColors.WhiteMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "2025-06-12｜23.98M", fontSize = 12.sp, color = AppColors.WhiteLow)
                    }
                }
                Icon(painter = painterResource(id = R.drawable.ic_right),
                    contentDescription = "more",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(32.dp).align(Alignment.CenterEnd))
            }
        }
    }
}

private val fileIconMap = mapOf(
    // 文本类
    "txt" to R.drawable.txt_file_extension,
    "md" to R.drawable.txt_file_extension,
    "log" to R.drawable.txt_file_extension,

    // 编程语言
    "java" to R.drawable.java_file_extension,
    "kt" to R.drawable.kt_file_extension,
    "xml" to R.drawable.xml_file_extension,
    "sql" to R.drawable.sql_file_extension,

    // 文档
    "pdf" to R.drawable.pdf_file_extension,
    "doc" to R.drawable.doc_file_extension,
    "docx" to R.drawable.doc_file_extension,

    // 压缩文件
    "zip" to R.drawable.archive_file_extension,
    "rar" to R.drawable.archive_file_extension,

    // 多媒体
    "mp3" to R.drawable.music_file_extension,
    "mp4" to R.drawable.video_file_extension,

    // 图片
    "jpg" to R.drawable.image_file_extension,
    "png" to R.drawable.image_file_extension,

    // 可执行文件
    "apk" to R.drawable.apk_placeholder
)
// 特殊类型集合
private val codeExtensions = setOf("c", "cpp", "h", "js", "py")
private val archiveExtensions = setOf("7z", "gz", "tar")


/**
 * 用于显示文件图标的可组合函数。
 *
 * @param file 文件对象，可选参数，默认为 null。
 * @param extension 文件扩展名，可选参数，默认为 null。
 * @param isDirectory 是否为目录，可选参数，默认为 false。
 * @param modifier 用于修改组件布局和样式的修饰符，可选参数，默认为 Modifier。
 * @param contentDescription 图像的内容描述，用于无障碍访问，可选参数，默认为 null。
 */
@Preview
@Composable
fun FileIcon(
    file: File? = null,
    extension: String? = null,
    isDirectory: Boolean = false,
    modifier: Modifier = Modifier.size(48.dp),
    contentDescription: String? = null
) {
    // 调用 calculateIconRes 函数计算文件对应的图标资源 ID
    val iconRes = calculateIconRes(file, extension, isDirectory)

    // 使用 Image 组件显示图标
    Image(
        // 通过 painterResource 获取图标资源
        painter = painterResource(id = iconRes),
        // 设置图像的内容描述，若未提供则使用默认值
        contentDescription = contentDescription ?: "File icon",
        // 应用传入的修饰符
        modifier = modifier
    )
}


/**
 * 计算文件对应的图标资源ID。
 *
 * 此函数根据文件对象、文件扩展名和是否为目录来确定应显示的图标资源ID。
 * 如果是目录，将返回目录图标；如果是特定文件类型，将从预定义的映射表中查找对应图标；
 * 对于特殊类型的文件，会检查特殊类型集合；如果都不匹配，则返回未知类型图标。
 *
 * @param file 文件对象，可选参数，若传入 null 则不使用文件对象获取扩展名。
 * @param extension 文件扩展名，可选参数，若传入非空值则优先使用该扩展名。
 * @param isDirectory 是否为目录，若为 true 则返回目录图标。
 * @return 对应的图标资源ID。
 */
@Composable
private fun calculateIconRes(
    file: File?,
    extension: String?,
    isDirectory: Boolean
): Int {
    // 优先处理目录类型，如果是目录则直接返回目录图标资源ID
    if (isDirectory) return R.drawable.ic_folder

    // 获取扩展名的三种方式，按优先级依次选择
    val finalExtension = when {
        // 若传入的扩展名不为空且不为 null，则使用传入的扩展名
        !extension.isNullOrEmpty() -> extension
        // 若传入了文件对象，则从文件对象中获取扩展名并转换为小写
        file != null -> file.extension.lowercase()
        // 若以上两种情况都不满足，则使用空字符串作为扩展名
        else -> ""
    }

    return when {
        // 检查特殊类型集合，如果扩展名在代码文件扩展名集合中，则返回代码文件图标资源ID
        codeExtensions.contains(finalExtension) -> R.drawable.code_file_extension
        // 检查特殊类型集合，如果扩展名在归档文件扩展名集合中，则返回归档文件图标资源ID
        archiveExtensions.contains(finalExtension) -> R.drawable.archive_file_extension

        // 查找映射表，如果扩展名在文件图标映射表中，则返回对应的图标资源ID
        fileIconMap.containsKey(finalExtension) -> fileIconMap[finalExtension]!!

        // 默认未知类型，如果以上情况都不匹配，则返回未知文件图标资源ID
        else -> R.drawable.unknown_file_extension
    }
}
