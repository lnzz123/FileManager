package com.example.filemanager.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filemanager.R
import com.example.filemanager.component.AppTopBar
import com.example.filemanager.ui.theme.AppColors

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun HomeScreen() {
    var navigationIndex by remember { mutableStateOf(0) }
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
                        color = if (index == navigationIndex) AppColors.TextFocus else AppColors.WhiteMedium,
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
            else -> InternalStorageScreen() // 默认显示内置存储
        }
    }
}
