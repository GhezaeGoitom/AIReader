package com.gg.aireader.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 0,
    val autoPlayMusic: Boolean = true,
    val autoDetectMood: Boolean = true,
    val darkMode: Boolean = false,
    val ocrEnabled: Boolean = true,
    val language: String = "en",
    val preferredMood: String? = null
)
