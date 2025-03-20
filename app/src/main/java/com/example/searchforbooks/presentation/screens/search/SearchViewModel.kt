package com.example.searchforbooks.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchforbooks.data.utils.OperationResult
import com.example.searchforbooks.domain.models.BookModel
import com.example.searchforbooks.domain.usecase.GetAllSearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAllSearchBooksUseCase: GetAllSearchBooksUseCase
) : ViewModel() {

    var searchQuery by mutableStateOf("")
    var searchScreenState by mutableStateOf<SearchScreenState>(SearchScreenState.Empty)
        private set

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        searchQuery = query
        searchJob?.cancel()
        if (query.isNotEmpty()) {
            searchScreenState = SearchScreenState.Loading
            searchJob = viewModelScope.launch {
                delay(2000) // Ожидаем 2 секунды, чтобы пользователь не вводил
                performSearch(query)
            }
        } else {
            searchScreenState = SearchScreenState.Empty
        }
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            try {
                // Используем метод execute вместо getAllSearchBooksUseCase
                when (val result = getAllSearchBooksUseCase.execute(query)) {
                    is OperationResult.Success -> {
                        if (result.data.isEmpty()) {
                            searchScreenState = SearchScreenState.EmptyResults
                        } else {
                            searchScreenState = SearchScreenState.Success(result.data)
                        }
                    }
                    is OperationResult.Error -> {
                        searchScreenState = SearchScreenState.Error(result.message)
                    }
                    OperationResult.Loading -> {
                        // Это состояние можно игнорировать, так как оно контролируется внутри execute
                    }
                }
            } catch (e: Exception) {
                searchScreenState = SearchScreenState.Error("Ошибка выполнения запроса, попробуйте повторить")
            }
        }
    }

    var selectedBook by mutableStateOf<BookModel?>(null)
        private set

    fun onBookSelected(book: BookModel) {
        selectedBook = book
        Log.d("SearchViewModel", "sВыбрана книга: ${book.title}, ID: ${book.id}")
    }

    fun getBookById(bookId: String): BookModel? {
        Log.d("SearchViewModel", "Метод getBookById вызван с ID: $bookId")
        val books = if (searchScreenState is SearchScreenState.Success) {
            (searchScreenState as SearchScreenState.Success).books
        } else {
            emptyList()
        }

        val book = books.find { it.id == bookId }

        Log.d("SearchViewModel", "Поиск книги с ID: $bookId, найдено: ${book?.title ?: "НЕ НАЙДЕНО"}")
        return book
    }

    fun onRetrySearch() {
        searchQuery = ""
        searchScreenState = SearchScreenState.Empty
    }
}