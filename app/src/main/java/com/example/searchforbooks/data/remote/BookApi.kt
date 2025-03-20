package com.example.searchforbooks.data.remote

import com.example.searchforbooks.data.models.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {
    // Эндпоинт для поиска книг
    @GET("volumes") // Используем базовый URL из NetworkModule
    suspend fun searchBooks(
        @Query("q") query: String, // Запрос с названием книги
        @Query("maxResults") maxResults: Int = 10 // Количество книг в ответе (по умолчанию 10)
    ): Response<BookResponse>
}