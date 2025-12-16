package com.gg.aireader.ui.screens.common

import android.graphics.Bitmap
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.AsyncImage

@Composable
fun PdfPage(page: Bitmap, modifier: Modifier = Modifier ){
    AsyncImage(model = page,
        contentDescription = null,
        modifier = Modifier.fillMaxSize().aspectRatio(page.width.toFloat() / page.height.toFloat()))
}
