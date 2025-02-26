package com.example.filemanager.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.filemanager.R

@Composable
fun CustomCheckbox(
    text: String? = null,
    textColor: Color = Color.White,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    Row(
        modifier = modifier
            .clickable { onCheckedChange(!checked) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = if (checked) R.drawable.ic_checkbox_checked
                else R.drawable.ic_checkbox_default
            ),
            contentDescription = if (checked) "Checked" else "Unchecked",
            tint = color,
            modifier = Modifier.size(24.dp)
        )
        if (text != null) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun CustomCheckboxPreview() {
    CustomCheckbox(
        checked = true,
        onCheckedChange = {}
    )
}