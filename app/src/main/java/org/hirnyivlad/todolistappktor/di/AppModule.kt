package org.hirnyivlad.todolistappktor.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.json.json
import org.hirnyivlad.todolistappktor.data.remote.AppSocketService
import org.hirnyivlad.todolistappktor.data.remote.AppSocketServiceImpl
import org.hirnyivlad.todolistappktor.data.remote.TaskService
import org.hirnyivlad.todolistappktor.data.remote.TaskServiceImpl

import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(WebSockets)
            install(ContentNegotiation) {
                json()
            }
        }
    }

    @Provides
    @Singleton
    fun provideTaskService(client: HttpClient): TaskService {
        return TaskServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideAppSocketService(client: HttpClient): AppSocketService {
        return AppSocketServiceImpl(client)
    }
}