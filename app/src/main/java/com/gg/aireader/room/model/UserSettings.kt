package com.gg.aireader.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_settings")
data class UserSettings(
    @PrimaryKey val id: Int = 0,
    val autoPlayMusic: Boolean = true,
    val darkMode: Boolean = false,
    val preferredMood: String? = null
)