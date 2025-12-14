package com.gg.aireader.data.repo

import com.gg.aireader.room.dao.SettingsDao
import com.gg.aireader.room.model.UserSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingRepo @Inject constructor(val settingsDao: SettingsDao){

    fun getSettings(): Flow<UserSettings?>{
       return settingsDao.getSettings()
    }

    suspend fun setSetting(userSettings: UserSettings){
        settingsDao.saveSettings(userSettings)
    }

}