package com.gg.aireader.ui.screens.setting

import android.util.Log
import androidx.lifecycle.ViewModel
import com.gg.aireader.data.repo.SettingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(settingsRepo: SettingRepo) : ViewModel() {

    val _isSmartMusicEnabled = MutableStateFlow(false)
    val isSmartMusicEnabled = _isSmartMusicEnabled.asStateFlow()


    fun smartMusicToggle(){
        _isSmartMusicEnabled.value = !_isSmartMusicEnabled.value
    }


}