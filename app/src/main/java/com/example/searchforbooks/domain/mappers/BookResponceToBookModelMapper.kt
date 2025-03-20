package com.example.searchforbooks.domain.mappers

import com.example.searchforbooks.data.models.BookItem
import com.example.searchforbooks.data.models.BookResponse
import com.example.searchforbooks.domain.models.BookModel
import javax.inject.Inject


class BookResponseToBookModelMapper @Inject constructor() {

    // Теперь метод ожидает BookItem, а не BookResponse
    operator fun invoke(bookItem: BookItem): BookModel {
        val volumeInfo = bookItem.volumeInfo ?: throw IllegalArgumentException("Volume info is null")  // Защита от null

        return BookModel(
            id = bookItem.id ?: "Unknown ID", // защита от null
            title = volumeInfo.title ?: "Неизвестно", // защита от null
            authors = volumeInfo.authors ?: emptyList(),
            publishedDate = volumeInfo.publishedDate ?: "Неизвестно",
            description = volumeInfo.description ?: "Неизвестно",
            thumbnail = volumeInfo.imageLinks?.thumbnail ?: ""
        )
    }
}

/*class BookResponseToBookModelMapper @Inject constructor() {

    operator fun invoke(bookResponse: BookResponse): List<BookModel> {
        return bookResponse.books?.map { bookItem ->

            val volumeInfo = bookItem.volumeInfo // Теперь безопасно работаем с этим объектом

            BookModel(
                id = bookItem.id,
                title = volumeInfo?.title ?: "Неизвестно",
                authors = volumeInfo?.authors ?: emptyList(),
                publishedDate = volumeInfo?.publishedDate ?: "Неизвестно",
                description = volumeInfo?.description ?: "Неизвестно",
                thumbnail = volumeInfo?.imageLinks?.thumbnail ?: ""
            )
        } ?: emptyList()
    }
}*/