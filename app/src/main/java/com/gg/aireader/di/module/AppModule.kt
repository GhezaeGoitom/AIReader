package com.gg.aireader.di.module

import com.gg.aireader.ktor.GroqApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideKtorClient(): HttpClient{
        return HttpClient(OkHttp) {
            install(ContentNegotiation){
                json()
            }
        }
    }


    @Provides
    @Singleton
    fun provideGroqApi(client: HttpClient): GroqApi{
        return GroqApi(client)
    }

}