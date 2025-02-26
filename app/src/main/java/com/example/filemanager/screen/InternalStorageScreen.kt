package com.example.filemanager.screen

import android.app.appsearch.StorageInfo
import android.os.Environment
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filemanager.R
import com.example.filemanager.component.AppTopBar
import com.example.filemanager.component.SecondaryActionBar
import com.example.filemanager.extension.getAvailableMemoryBytes
import com.example.filemanager.extension.getTotalMemoryBytes
import com.example.filemanager.extension.getUsedMemoryBytes
import com.example.filemanager.model.FileItem
import com.example.filemanager.ui.theme.AppColors
import com.example.filemanager.util.FileUtils
import java.io.File

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun InternalStorageScreen() {
    // 存储空间信息
    var storageInfo by remember { mutableStateOf("Loading...") }
    // 当前目录路径
    var currentPath by remember { mutableStateOf("/") }
    val fileItems = remember { mutableStateListOf<FileItem>() }
    // 存储目录列表
    LaunchedEffect(Unit) {
        val used = Environment.getExternalStorageDirectory().getUsedMemoryBytes()
        val available = Environment.getExternalStorageDirectory().getAvailableMemoryBytes()
        storageInfo = FileUtils.getFormattedSize(used) +"/"+ FileUtils.getFormattedSize(available)
        currentPath = Environment.getExternalStorageDirectory().path
        Log.d("InternalStorageScreen", "used: $used, available: $available, path: ${Environment.getExternalStorageDirectory().path}")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        SecondaryActionBar(
            leftContent = {
                Text(
                    text = storageInfo,
                    color = AppColors.WhiteMedium,
                    fontSize = 16.sp,
                )
            },
            //右侧按钮
            rightContent = {
                val btnList1 = listOf(
                    Triple(R.drawable.ic_search, "搜索") { println("搜索按钮点击") },
                    Triple(R.drawable.ic_sort, "排序") { /* 排序功能实现 */ },
                    Triple(R.drawable.ic_add, "新建") { },
                    Triple(R.drawable.ic_edit, "多选") { }
                )
                val btn = @Composable {image: Int ,chick: () -> Unit ->
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = "action",
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
                    itemsIndexed(btnList1) { index, item ->
                        btn(item.first) {
                            item.third
                        }
                    }
                }
            }
        )
        //文件列表
        LazyColumn(modifier = Modifier.fillMaxHeight()) {

        }
    }
}