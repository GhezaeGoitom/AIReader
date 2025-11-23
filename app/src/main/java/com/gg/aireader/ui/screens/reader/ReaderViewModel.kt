package com.gg.aireader.ui.screens.reader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gg.aireader.data.repo.PdfRepo
import com.gg.aireader.utils.PdfBitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(private val pdfRepo: PdfRepo): ViewModel() {
    private val _pageText = MutableStateFlow("")
    val pageText = _pageText.asStateFlow()

    private val _renderedPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val renderedPages = _renderedPages.asStateFlow()

    private val _isRendered = MutableStateFlow(false)
    val isRendered = _isRendered.asStateFlow()

    fun loadPdf(uri: Uri, context: Context){
        viewModelScope.launch {
            val pdfConverter = PdfBitmapConverter(context)
            val pages = pdfConverter.pdfToBitmap(uri)
            _renderedPages.value = pages
            _isRendered.value = true
        }
    }

    fun extractTextFromPage(pageIndex: Int, context: Context){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val page = _renderedPages.value[pageIndex]
                val text = pdfRepo.extractTextFromBitmap(page)
                Log.d("GG_RENDER", "Bitmap size: ${page.width}x${page.height}")
                Log.d("GG_RENDER2", "Bitmap text: ${text.substring(0,30)}")
                _pageText.value = text.substring(5, 10)
                val result = pdfRepo.analyzeMood(text)
                Log.d("gg_render_2220", result.toString())
            }catch (e: Exception){
                Log.e("gg err", e.message.toString())
                _pageText.value = "error ${e.message}"
            }
        }
    }


}