package com.gg.aireader.data.repo

import com.gg.aireader.ktor.ApiManager
import com.gg.aireader.ktor.JamendoTrack
import com.gg.aireader.ui.screens.model.Mood
import javax.inject.Inject

class MusicRepo @Inject constructor(val apiManager: ApiManager) {

    suspend fun getMusicByMood(mood: Mood): List<JamendoTrack>{
        return apiManager.getTracksByMood(mood)
    }

}