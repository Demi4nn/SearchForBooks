package com.example.searchforbooks.domain.repository

import com.example.searchforbooks.data.remote.post.PostRemoteDataSource
import com.example.searchforbooks.data.utils.OperationResult
import com.example.searchforbooks.domain.mappers.BookResponseToBookModelMapper
import com.example.searchforbooks.domain.models.BookModel
import com.example.searchforbooks.domain.utils.flatMapIfSuccess
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val remoteDataSource: PostRemoteDataSource,
    private val bookResponseToBookModelMapper: BookResponseToBookModelMapper,
) {
    /** Получить список книг по запросу */
    suspend fun searchBooks(query: String): OperationResult<List<BookModel>> =
        remoteDataSource.searchBooks(query).flatMapIfSuccess { bookItemList ->
            val books = bookItemList.map { bookItem ->
                bookResponseToBookModelMapper(bookItem) // Маппинг BookItem в BookModel
            }
            OperationResult.Success(books) // Возвращаем успешный результат с преобразованными книгами
        }
}
/*interface BookRepository {
    suspend fun searchBooks(query: String): OperationResult<List<BookModel>>
}*/