package com.gg.aireader.ktor

import com.gg.aireader.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject


class GroqApi @Inject constructor(val client: HttpClient) {

    private val apiKey = BuildConfig.GROQ_API_KEY
    private val baseUrl = "https://api.groq.com/openai/v1/chat/completions"

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
                ["Calm","Happy","Sad","Romantic","Tense","Dark","Inspirational"]
                """.trimIndent()
                ),
                ChatMessage("user", text)
            )
        )

        val response = client.post(baseUrl) {
            header("Authorization", "Bearer $apiKey")
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

}