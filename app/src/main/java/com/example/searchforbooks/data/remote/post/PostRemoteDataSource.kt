package com.example.searchforbooks.data.remote.post

import android.util.Log
import com.example.searchforbooks.data.models.BookItem
import com.example.searchforbooks.data.models.BookResponse
import com.example.searchforbooks.data.models.VolumeInfo
import com.example.searchforbooks.data.remote.BookApi
import com.example.searchforbooks.data.utils.BaseRemoteDataSource
import com.example.searchforbooks.data.utils.OperationResult
import retrofit2.Response
import javax.inject.Inject

class PostRemoteDataSource @Inject constructor(
    private val bookApi: BookApi
) : BaseRemoteDataSource() {

    /** Метод для поиска книг по запросу */
    suspend fun searchBooks(query: String): OperationResult<List<BookItem>> {
        // Получаем OperationResult<BookResponse> через safeApiCall
        val result = safeApiCall { bookApi.searchBooks(query) }

        // Преобразуем OperationResult<BookResponse> в OperationResult<List<BookItem>>
        return when (result) {
            is OperationResult.Success -> {
                // Если список книг не null, возвращаем его, иначе ошибку
                Log.d("PostRemoteDataSource", "API Response: ${result.data}")
                result.data.books?.let {
                    OperationResult.Success(it)  // Возвращаем список BookItem
                } ?: OperationResult.Error("No books found.")
            }

            is OperationResult.Error -> {
                Log.e("PostRemoteDataSource", "Error: ${result.message}")
                return result
            }

            is OperationResult.Loading -> {
                Log.d("PostRemoteDataSource", "Loading...")
                return result
            }
        }
    }
}
