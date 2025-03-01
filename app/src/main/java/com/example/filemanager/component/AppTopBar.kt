package com.example.filemanager.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.filemanager.R
import com.example.filemanager.ui.theme.ThemeColors

@Preview
@Composable
fun AppTopBar(title:String = "TopBarTitle", content: @Composable () -> Unit = {}) {
    Row (
        modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(R.drawable.ic_left),
            tint = Color.Unspecified,
            contentDescription = "Back"
        )
        Text(
            text = title,
            color = ThemeColors.WhiteHigh,
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        content()
    }
}

@Preview
@Composable
fun SecondaryActionBar(
    modifier: Modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 16.dp),
    leftContent: @Composable () -> Unit = {},
    rightContent: @Composable () -> Unit = {}
) {
    Box(modifier = modifier)
    {
        Box(modifier = Modifier.align(Alignment.CenterStart))
        {
            leftContent()
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd))
        {
            rightContent()
        }
    }
}