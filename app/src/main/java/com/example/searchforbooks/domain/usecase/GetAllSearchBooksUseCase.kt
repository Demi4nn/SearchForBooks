package com.example.searchforbooks.domain.usecase

import com.example.searchforbooks.data.utils.OperationResult
import com.example.searchforbooks.domain.models.BookModel
import com.example.searchforbooks.domain.repository.BookRepository
import com.example.searchforbooks.domain.utils.flatMapIfSuccess
import javax.inject.Inject

class GetAllSearchBooksUseCase @Inject constructor(
    private val bookRepository: BookRepository
) {
    suspend fun execute(query: String): OperationResult<List<BookModel>> {
        return bookRepository.searchBooks(query)
            .flatMapIfSuccess { bookResponse ->
                // Преобразуем результат из модели данных в модель домена
                val books = bookResponse.map { bookItem ->
                    // Маппинг каждой книги на BookModel
                    BookModel(
                        id = bookItem.id,
                        title = bookItem.title,
                        authors = bookItem.authors,
                        publishedDate = bookItem.publishedDate,
                        description = bookItem.description,
                        thumbnail = bookItem.thumbnail
                    )
                }
                // Возвращаем результат с преобразованными данными
                OperationResult.Success(books)
            }
    }
}