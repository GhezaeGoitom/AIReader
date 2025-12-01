package com.gg.aireader.ktor

import kotlinx.serialization.Serializable

// groq api model
@Serializable
data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
data class GroqRequest(
    val model: String,
    val messages: List<ChatMessage>
)

// jamendo api models

@kotlinx.serialization.Serializable
data class JamendoTracksResponse(
    val results: List<JamendoTrack>
)

@kotlinx.serialization.Serializable
data class JamendoTrack(
    val id: String,
    val name: String,
    val artist_name: String,
    val audio: String,           // MP3 URL
    val audio_download: String? = null,
    val duration: Int
)
