package org.example.rickandmortyapp.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.example.rickandmortyapp.model.*

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
    }
}
suspend fun getCharacterResponse(page: Int = 1): CharacterResponse {
    return httpClient.get("https://rickandmortyapi.com/api/character/?page=$page").body()
}




