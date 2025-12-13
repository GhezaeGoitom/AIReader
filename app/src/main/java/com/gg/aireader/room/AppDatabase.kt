package com.gg.aireader.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gg.aireader.room.dao.RecentBookDao
import com.gg.aireader.room.dao.SettingsDao
import com.gg.aireader.room.model.RecentBook
import com.gg.aireader.room.model.UserSettings

@Database(
    entities = [
        RecentBook::class,
        UserSettings::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentBookDao(): RecentBookDao
    abstract fun settingsDao(): SettingsDao
}
