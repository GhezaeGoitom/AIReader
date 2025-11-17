package com.gg.aireader.ui.screens.reader

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gg.aireader.data.repo.PdfRepo
import com.gg.aireader.utils.PdfBitmapConverter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReaderViewModel(): ViewModel(){
    private val _pageText = MutableStateFlow<String>("")
    val pageText = _pageText.asStateFlow()

    private val _renderedPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val renderedPages = _renderedPages.asStateFlow()

    private val _isRendered = MutableStateFlow<Boolean>(false)
    val isRendered = _isRendered.asStateFlow()

    val pdfRepo = PdfRepo()

    fun loadPdf(uri: Uri, context: Context){
        viewModelScope.launch {
            val pdfConverter = PdfBitmapConverter(context)
            val pages = pdfConverter.pdfToBitmap(uri)
            _renderedPages.value = pages
            _isRendered.value = true
        }
    }

    fun extractTextFromPage(pageIndex: Int){
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val page = _renderedPages.value[pageIndex]
                val text = pdfRepo.extractTextFromBitmap(page)
                Log.d("GG_RENDER", "Bitmap size: ${page.width}x${page.height}")
                Log.d("GG_RENDER2", "Bitmap text: ${text}")
                _pageText.value = text
            }catch (e: Exception){
                Log.e("gg err", e.message.toString())
                _pageText.value = "error ${e.message}"
            }
        }
    }

}