package com.gg.aireader.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_books")
data class RecentBook(
    @PrimaryKey val path: String,
    val title: String,
    val lastOpened: Long?,
    val currentPage: Int?,
    val pageCount: Int,
    val cover: String?,
    val mood: String?,
    val progress: Float?
)
