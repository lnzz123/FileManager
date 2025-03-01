package com.example.filemanager.screen

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filemanager.R
import com.example.filemanager.component.AppTopBar
import com.example.filemanager.component.TipDialog
import com.example.filemanager.ui.theme.ThemeColors

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun HomeScreen() {
    var navigationIndex by remember { mutableStateOf(0) }

    // 管理弹窗的状态
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current // 获取当前的 Context
    val activity = context as? Activity // 将 Context 转换为 Activity

    // 捕获返回按钮事件，显示确认退出弹窗
    BackHandler {
        showDialog = true
    }
    if (showDialog) {
        TipDialog(
            showDialog = showDialog,
            onDismissRequest = { showDialog = false }, // 关闭弹窗
            title = "确认退出",
            message = "您确定要退出吗？",
            onConfirm = {
                // 退出操作
                activity?.finish()
                //compose导航返回上一页
                //composeNavigator.navigateUp()
            },
            onDismiss = {
                // 取消退出操作
                showDialog = false
            }
        )
    }
    // 在这里添加你的主屏幕内容
    Column {
        AppTopBar("文件管理")
        {
            val navigation: List<String> = listOf("内置存储", "USB", "硬盘", "网络SMB", "网络NFS", "CD ROM", "收藏")
            val TabItem = @Composable { index: Int, title: String ->
                Column (verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(
                        text = title,
                        fontSize = if (index == navigationIndex) 18.sp else 16.sp,
                        color = if (index == navigationIndex) ThemeColors.TextFocus else ThemeColors.WhiteMedium,
                        fontWeight = if (index == navigationIndex) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier.clickable { navigationIndex = index }
                    )
                    if (index == navigationIndex) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_tab),
                            contentDescription = "Tab",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .height(2.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            // 导航按钮
            LazyRow(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp), // 项目间隔
            ) {
                itemsIndexed(navigation) { index, item ->
                    TabItem(index, item)
                }
            }
        }
        // 显示不同的界面
        when (navigationIndex) {
            0 -> InternalStorageScreen()
            1 -> USBScreen()
            else -> InternalStorageScreen() // 默认显示内置存储
        }
    }
}
