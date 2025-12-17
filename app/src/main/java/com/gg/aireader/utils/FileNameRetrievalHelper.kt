package com.gg.aireader.utils

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns

fun Uri.getFileNameForDisplay(
    contentResolver: ContentResolver
): String {
    var name = "Unknown"

    contentResolver.query(this, null, null, null, null)?.use { cursor ->
        val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        if (cursor.moveToFirst() && index != -1) {
            name = cursor.getString(index)
        }
    }

    return name.removeSuffix(".pdf")
}
