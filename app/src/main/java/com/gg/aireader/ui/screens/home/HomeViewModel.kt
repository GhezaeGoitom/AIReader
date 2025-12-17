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




}