package com.gg.aireader.di.module

import android.content.Context
import androidx.media3.common.AudioAttributes
import androidx.media3.exoplayer.ExoPlayer
import com.gg.aireader.ktor.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient{
        return HttpClient(OkHttp) {
            install(ContentNegotiation){
                json(
                    Json { ignoreUnknownKeys = true }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideApiManager(client: HttpClient): ApiManager{
        return ApiManager(client)
    }

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext context: Context): ExoPlayer{
        val audioAttributes = AudioAttributes.Builder()
    .setContentType(androidx.media3.common.C.AUDIO_CONTENT_TYPE_MUSIC)
    .setUsage(androidx.media3.common.C.USAGE_MEDIA)
    .build()


        return ExoPlayer.Builder(context)
            .setAudioAttributes(audioAttributes, true)
            .build()
    }




}