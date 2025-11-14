package com.gg.aireader.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel: ViewModel() {

    private val _message = MutableStateFlow("hello gg")
    val message: MutableStateFlow<String> = _message



}