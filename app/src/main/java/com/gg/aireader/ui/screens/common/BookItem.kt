package com.gg.aireader.ui.screens.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gg.aireader.room.model.RecentBook
import com.gg.aireader.ui.screens.model.Book
import com.gg.aireader.utils.assetImage

@Composable
fun BookItem(book: RecentBook, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable(onClick = onClick)
//    ) {
//        Row(modifier = Modifier.padding(16.dp)) {
//            Text(book.title)
//        }
//    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = assetImage("pdf.png"),
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(book.title, style = MaterialTheme.typography.titleMedium)
                Text("PDF • ${book.pageCount} pages", style = MaterialTheme.typography.bodySmall)
            }
        }
    }

}
