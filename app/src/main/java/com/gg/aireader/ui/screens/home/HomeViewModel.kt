package com.gg.aireader.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.gg.aireader.ui.screens.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        loadBooks()
    }

    private fun loadBooks() {
        _books.value = listOf(
            Book("1", "The Calm Book"),
            Book("2", "Energy Flow"),
            Book("3", "Focus Mode")
        )
    }

    fun onBookSelected(bookId: String) {
        // navigation handled in UI layer
    }
}
