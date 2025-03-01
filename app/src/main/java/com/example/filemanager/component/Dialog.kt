package com.example.filemanager.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.filemanager.ui.theme.ThemeColors

/**
 * 自定义对话框
 * @param showDialog 控制对话框是否显示的布尔值，true 显示，false 隐藏
 * @param onDismissRequest 当对话框关闭时触发的回调函数，通常用于更新显示状态
 * @param title 对话框显示的标题，字符串类型
 * @param message 对话框显示的具体信息内容，字符串类型
 * @param onConfirmText 确认按钮显示的文本，默认值为 "确认"
 * @param onDismissText 取消按钮显示的文本，默认值为 "取消"
 * @param onConfirm 点击确认按钮时触发的回调函数，默认不执行任何操作
 * @param onDismiss 点击取消按钮时触发的回调函数，默认不执行任何操作
 */
@Composable
fun TipDialog(
  showDialog: Boolean,
  onDismissRequest: () -> Unit,
  title: String,
  message: String,
  onConfirmText: String = "确认",
  onDismissText: String = "取消",
  onConfirm: () -> Unit = {},
  onDismiss: () -> Unit = {},
) {
  // 如果 showDialog 为 true，才显示弹窗
  if (showDialog) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
      // 自定义弹窗的布局
      Box(
        modifier =
        Modifier
          .fillMaxSize(),
        contentAlignment = Alignment.Center,
      ) {
        Card(
          modifier =
          Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
          shape = RoundedCornerShape(12.dp),
        ) {
          Box(modifier = Modifier.background(ThemeColors.Gradients.GlobalBackground)){
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally,
            ) {
              Text(title, style = MaterialTheme.typography.bodyLarge, color = Color.White) // 显示标题
              Spacer(modifier = Modifier.height(16.dp))
              Text(message, color = Color.White) // 显示消息内容
              Spacer(modifier = Modifier.height(16.dp))
              Box(
                modifier = Modifier.fillMaxWidth(),
              ) {
                // 取消按钮
                AppButton(
                  text = onDismissText,
                  shape = RoundedCornerShape(8.dp),
                  state = ButtonState.NORMAL.ordinal,
                  onClick = {
                    onDismiss()
                    onDismissRequest()  // 关闭弹窗
                  },
                  modifier =
                  Modifier
                    .width(120.dp)
                    .align(Alignment.TopStart))

                Spacer(modifier = Modifier.width(8.dp))

                // 确认按钮
                AppButton(
                  text = onConfirmText,
                  shape = RoundedCornerShape(8.dp),
                  state = ButtonState.FOCUSED.ordinal,
                  onClick = {
                    onConfirm()
                    onDismissRequest() // 关闭弹窗
                  },
                  modifier =
                  Modifier
                    .width(120.dp)
                    .align(Alignment.BottomEnd))
              }
            }
          }
        }
      }
    }
  }
}
/**
 * 自定义对话框组件
 *
 * @param showDialog 是否显示对话框
 * @param onDismissRequest 关闭对话框的回调函数
 * @param title 对话框的标题
 * @param content 对话框的自定义内容部分，使用 @Composable 函数传入
 * @param onConfirmText 确认按钮的文本，默认为 "确认"
 * @param onDismissText 取消按钮的文本，默认为 "取消"
 * @param onConfirm 点击确认按钮的回调函数，默认为空操作
 * @param onDismiss 点击取消按钮的回调函数，默认为空操作
 */
@Composable
fun CustomDialog(
  showDialog: Boolean,
  onDismissRequest: () -> Unit,
  title: String,
  content: @Composable () -> Unit,
  onConfirmText: String = "确认",
  onDismissText: String = "取消",
  onConfirm: () -> Unit = {},
  onDismiss: () -> Unit = {},
) {
  if (showDialog) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
      Box(
        modifier = Modifier
          .fillMaxSize(),
        contentAlignment = Alignment.Center
      ) {
        Card(
          modifier =
          Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(ThemeColors.Gradients.GlobalBackground),
          shape = RoundedCornerShape(12.dp)
        ) {
          Box(modifier = Modifier.background(ThemeColors.Gradients.GlobalBackground)){
            Column(
              modifier = Modifier.padding(16.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              Text(title, style = MaterialTheme.typography.bodyLarge, color = Color.White)
              Spacer(modifier = Modifier.height(16.dp))

              // 自定义内容部分
              Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
              ) {
                content() // 这里插入自定义的内容组件
              }

              Spacer(modifier = Modifier.height(16.dp))

              // 按钮布局
              Box(
                modifier = Modifier.fillMaxWidth(),
              ) {
                // 取消按钮
                AppButton(
                  text = onDismissText,
                  shape = RoundedCornerShape(8.dp),
                  state = ButtonState.NORMAL.ordinal,
                  onClick = {
                    onDismiss()
                    onDismissRequest()  // 关闭弹窗
                  },
                  modifier =
                  Modifier
                    .width(120.dp)
                    .align(Alignment.TopStart))

                Spacer(modifier = Modifier.width(8.dp))

                // 确认按钮
                AppButton(
                  text = onConfirmText,
                  shape = RoundedCornerShape(8.dp),
                  state = ButtonState.FOCUSED.ordinal,
                  onClick = {
                    onConfirm()
                    onDismissRequest() // 关闭弹窗
                  },
                  modifier =
                  Modifier
                    .width(120.dp)
                    .align(Alignment.BottomEnd))
              }
            }
          }
        }
      }
    }
  }
}


@Preview
@Composable
fun CustomDialogPreview() {
  var showDialog by remember { mutableStateOf(true) }
  TipDialog(
    showDialog = true,
    onDismissRequest = { showDialog = false },
    title = "提示",
    message = "你确定要继续吗？",
    onConfirm = {
      // 处理确认按钮点击逻辑
      println("确认点击")
    },
    onDismiss = {
      // 处理取消按钮点击逻辑
      println("取消点击")
    },
  )
}