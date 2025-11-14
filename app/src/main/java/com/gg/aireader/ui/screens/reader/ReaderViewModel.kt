package com.gg.aireader.ui.screens.reader

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ReaderViewModel: ViewModel(){

    private val _pdfPath = MutableStateFlow<String?>("/sdcard/test/motg.pdf")
    val pdfPath = _pdfPath.asStateFlow()


    fun loadPdf(path: String){
        _pdfPath.value
    }

}