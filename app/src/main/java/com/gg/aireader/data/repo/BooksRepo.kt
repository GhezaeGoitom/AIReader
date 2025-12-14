package com.gg.aireader.data.repo

import com.gg.aireader.room.dao.RecentBooksDao
import com.gg.aireader.room.model.RecentBook
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksRepo @Inject constructor(val recentBookDao: RecentBooksDao) {

    suspend fun upsertBooks(book: RecentBook){
        recentBookDao.upsert(book)
    }

    fun getAllBooks(): Flow<List<RecentBook>>{
        return recentBookDao.getRecentBooks()
    }

}