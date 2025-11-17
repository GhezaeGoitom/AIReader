package com.gg.aireader.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PdfBitmapConverter(private val context: Context){

    var renderer: PdfRenderer? = null

    suspend fun pdfToBitmap(contentUris: Uri): List<Bitmap>{
        return withContext(Dispatchers.IO) {
            // clean up
            renderer?.close()

            val fd = context.contentResolver.openFileDescriptor(
                contentUris,
                "r"
            ) ?: return@withContext emptyList()

            val pdfRenderer = PdfRenderer(fd)
            renderer = pdfRenderer

            val pages = mutableListOf<Bitmap>()

            for(i in 0 until pdfRenderer.pageCount){
                 pdfRenderer.openPage(i).use { page ->
                    val bitmap = Bitmap.createBitmap(
                        page.width,
                        page.height,
                        Bitmap.Config.ARGB_8888
                    )

                    // Fill it with white background
                    val canvas = Canvas(bitmap)
                    canvas.drawColor(android.graphics.Color.WHITE)

                    // Render PDF page into bitmap
                    page.render(
                        bitmap,
                        null,
                        null,
                        PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY
                    )
                    pages.add(bitmap)
                }
            }
            pages
        }}}