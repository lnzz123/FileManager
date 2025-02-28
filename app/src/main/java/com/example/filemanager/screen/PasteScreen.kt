package com.example.filemanager.screen

import android.os.Environment
import android.util.Log
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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.filemanager.component.AppButton
import com.example.filemanager.component.AppTopBar
import com.example.filemanager.component.ButtonState
import com.example.filemanager.component.CustomCheckbox
import com.example.filemanager.component.FileItem
import com.example.filemanager.component.SecondaryActionBar
import com.example.filemanager.extension.getFileDetails
import com.example.filemanager.extension.getUsedMemoryBytes
import com.example.filemanager.extension.openFileWith
import com.example.filemanager.model.FileList
import com.example.filemanager.ui.theme.AppColors
import com.example.filemanager.util.FileUtils
import java.io.File

@Preview(device = "spec:parent=pixel_5,orientation=landscape")
@Composable
fun PasteScreen() {
    var pasteTo by remember { mutableStateOf(null) }
    Column(modifier = Modifier.fillMaxSize()) {
        // 顶部栏
        AppTopBar("选择粘贴位置")




        when (pasteTo) {
            PasteScreenState.BUILT_IN_STORAGE -> {
            }
            PasteScreenState.USB -> {
            }
            PasteScreenState.HARD_DISK -> {
            }
        }
    }
}
enum class PasteScreenState {
    BUILT_IN_STORAGE,
    USB,
    HARD_DISK,
}