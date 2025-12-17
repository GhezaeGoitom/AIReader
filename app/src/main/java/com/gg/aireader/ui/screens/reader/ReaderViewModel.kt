package com.gg.aireader.ui.screens.reader

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.gg.aireader.data.repo.BooksRepo
import com.gg.aireader.data.repo.MusicRepo
import com.gg.aireader.data.repo.PdfRepo
import com.gg.aireader.ktor.JamendoTrack
import com.gg.aireader.room.model.RecentBook
import com.gg.aireader.ui.screens.model.Mood
import com.gg.aireader.utils.PdfBitmapConverter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderViewModel @Inject constructor(private val pdfRepo: PdfRepo,
                                          private val player: ExoPlayer,
    private val musicRepo: MusicRepo, private val booksRepo: BooksRepo): ViewModel() {
    private val _pageText = MutableStateFlow("")
    val pageText = _pageText.asStateFlow()

    private val _renderedPages = MutableStateFlow<List<Bitmap>>(emptyList())
    val renderedPages = _renderedPages.asStateFlow()

    private val _isRendered = MutableStateFlow(false)
    val isRendered = _isRendered.asStateFlow()

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()


    private val _isMoodAvailable = MutableStateFlow(false)
    val isMoodAvailable = _isMoodAvailable.asStateFlow()

    private val _mood = MutableStateFlow("")
    val mood = _mood.asStateFlow()

    private val _currentPage = MutableStateFlow(0)
    val currentPage = _currentPage.asStateFlow()


    // exoplayer
    fun play(uri: String){
        val mediaItem = MediaItem.fromUri(uri)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()
    }


    fun pause() = player.pause()
    fun resume() = player.play()
    fun stop() = player.stop()

    fun togglePlay(){
        _isPlaying.value = !_isPlaying.value
    }



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
                _isMoodAvailable.value = false
                val page = _renderedPages.value[pageIndex]
                val text = pdfRepo.extractTextFromBitmap(page)
                _pageText.value = text
                val moodFromOCR = pdfRepo.analyzeMood(text)
                _mood.value = moodFromOCR
                _isMoodAvailable.value = true

            }catch (e: Exception){
                Log.e("gg err", e.message.toString())
                _pageText.value = "error ${e.message}"
            }
        }
    }

    suspend fun getMusicUrlByMood(m: String): List<JamendoTrack>{
         var moodResponse: List<JamendoTrack> = emptyList()
        try {
       moodResponse = musicRepo.getMusicByMood(Mood.fromTag(m)?: Mood.CALM)
        }catch (e: Exception){
            Log.d("gg_mood_err", e.toString())
        }
        return moodResponse
    }


    fun saveNewBook(uri: Uri?, fileName: String) {

        viewModelScope.launch {
            booksRepo.upsertBooks(RecentBook(
                path = uri.toString(),
                title = fileName,
                pageCount = _renderedPages.value.size,
                lastOpened = null,
                currentPage = null,
                cover = null,
                mood = null,
                progress = null,
            ))
        }

    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

}