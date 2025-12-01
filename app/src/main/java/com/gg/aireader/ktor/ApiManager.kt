package com.gg.aireader.ktor

import android.util.Log
import androidx.compose.ui.text.toLowerCase
import com.gg.aireader.BuildConfig
import com.gg.aireader.ui.screens.model.Mood
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject


class ApiManager @Inject constructor(val client: HttpClient) {

    private val groqApiKey = BuildConfig.GROQ_API_KEY
    private val jamendoClientId = BuildConfig.JAMENDO_CLIENT_ID
    private val groqBaseUrl = "https://api.groq.com/openai/v1/chat/completions"
    private val jamendoBaseUrl = "https://api.jamendo.com/v3.0/tracks"

    suspend fun classifyMood(text: String): String {
        val request = GroqRequest(
            model = "llama-3.3-70b-versatile",
//            model = "llama-3.2-3b-preview",
            messages = listOf(
                ChatMessage(
                    "system",
                    """
                You classify emotions from book text.
                Return ONLY a single mood from:
                [${Mood.entries.map { it.name.lowercase() }}]
                """.trimIndent()
                ),
                ChatMessage("user", text)
            )
        )
        val response = client.post(groqBaseUrl) {
            header("Authorization", "Bearer $groqApiKey")
            contentType(ContentType.Application.Json)
            setBody(request)
        }

        val json = response.body<JsonObject>()

        val output = json["choices"]!!
            .jsonArray[0]
            .jsonObject["message"]!!
            .jsonObject["content"]!!
            .jsonPrimitive.content

        return output.trim()
    }



    suspend fun getTracksByMood(mood: Mood): List<JamendoTrack> {
        return try {
            val response: JamendoTracksResponse = client.get(jamendoBaseUrl) {
                parameter("client_id", jamendoClientId)
                parameter("format", "json")
                parameter("tags", mood.name)
                parameter("limit", 5)
                parameter("include", "musicinfo")
            }.body()
            Log.d("gg_jam2_err", response.toString())
            response.results

        } catch (e: Exception) {
            Log.d("gg_jam_err", e.toString())
            emptyList()
        }
    }

}