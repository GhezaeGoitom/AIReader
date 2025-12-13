package com.gg.aireader.ui.screens.home

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gg.aireader.data.repo.BooksRepo
import com.gg.aireader.room.model.RecentBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(val booksRepo: BooksRepo): ViewModel() {

    val books = booksRepo.getAllBooks().stateIn(viewModelScope,
        SharingStarted.Lazily,
        emptyList())



    fun saveNewBook(uri: Uri, context: Context) {

        viewModelScope.launch {
            booksRepo.upsertBooks(RecentBook(
                path = uri.toString(),
                title = getFileName(context = context, uri = uri),
                pageCount = 100, //placeholder for now
                lastOpened = null,
                currentPage = null,
                cover = null,
                mood = null,
                progress = null,
            ))
        }

    }

    fun getFileName(context: Context, uri: Uri): String {
        var name = "Unknown.pdf"
        val cursor = context.contentResolver.query(uri, null, null, null, null) ?: return name

        cursor.use { c ->
            val nameIndex = c.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (c.moveToFirst() && nameIndex != -1) {
                name = c.getString(nameIndex)
            }
        }
        return name
    }
}