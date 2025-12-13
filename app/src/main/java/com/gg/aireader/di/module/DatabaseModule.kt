package com.gg.aireader.di.module

import android.content.Context
import androidx.room.Room
import com.gg.aireader.room.AppDatabase
import com.gg.aireader.room.dao.RecentBookDao
import com.gg.aireader.room.dao.SettingsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "ai_reader.db"
        )
            // validate later for depricated function
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideRecentBookDao(db: AppDatabase): RecentBookDao {
        return db.recentBookDao()
    }

    @Provides
    fun provideSettingsDao(db: AppDatabase): SettingsDao {
        return db.settingsDao()
    }
}