package com.example.searchforbooks.presentation.screens.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.searchforbooks.data.utils.OperationResult
import com.example.searchforbooks.domain.usecase.GetAllSearchBooksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    fun onRetrySearch() {
        searchQuery = ""
        searchScreenState = SearchScreenState.Empty
    }
}