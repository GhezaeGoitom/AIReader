package com.gg.aireader.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gg.aireader.room.model.RecentBook
import kotlinx.coroutines.flow.Flow

@Dao
    interface RecentBookDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun upsert(book: RecentBook)

        @Query("SELECT * FROM recent_books ORDER BY lastOpened DESC LIMIT :limit")
        fun getRecentBooks(limit: Int = 10): Flow<List<RecentBook>>
    }