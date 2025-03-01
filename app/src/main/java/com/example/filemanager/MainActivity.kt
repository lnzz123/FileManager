package com.example.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.filemanager.screen.HomeScreen
import com.example.filemanager.ui.theme.ThemeColors
import com.example.filemanager.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissions()
        enableEdgeToEdge()
        setContent {
            AppTheme {
                HomeScreen()
            }
        }
    }

    fun init() {

    }

            /**
     * 检查存储权限的方法，如果权限已授予，则调用 init 方法进行初始化。
     */
    protected fun checkPermissions() {
        if (grantStoragePermissions()) {
            init()
        }
    }

    /**
     * 请求存储权限的方法，根据不同的 Android 版本采取不同的权限请求方式。
     * @return 如果权限已授予，返回 true；否则返回 false。
     */
    private fun grantStoragePermissions(): Boolean {
        // 对于 Android R 及以上版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 检查是否拥有外部存储管理权限
            if (!Environment.isExternalStorageManager()) {
                // 创建一个 Intent 用于请求所有文件访问权限
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.fromParts("package", packageName, null)
                // 启动权限请求 Activity 并等待结果
                startActivityForResult(intent, 121121)
                return false
            }
        } else {
            // 对于 Android R 以下版本
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
                || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // 请求读取和写入外部存储的权限
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    9011
                )
                return false
            }
        }
        return true
    }

    /**
     * 处理权限请求结果的方法。
     * @param requestCode 请求权限时的请求码。
     * @param permissions 请求的权限数组。
     * @param grantResults 权限授予结果数组。
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        // 调用父类的方法处理权限请求结果
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 如果请求码为 9011，说明是存储权限请求
        if (requestCode == 9011) {
            init()
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier.background(ThemeColors.Gradients.IconGrayscale)
) {
    Text(
        text = "Hello $name!",
        modifier = modifier,
        color = ThemeColors.TextFocus
    )
}



