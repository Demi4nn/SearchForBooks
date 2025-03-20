package com.example.searchforbooks.presentation.screens.search

import com.example.searchforbooks.domain.models.BookModel

sealed class SearchScreenState {
    object Empty : SearchScreenState()
    object Loading : SearchScreenState()
    data class Success(val books: List<BookModel>) : SearchScreenState()
    object EmptyResults : SearchScreenState()
    data class Error(val message: String) : SearchScreenState()
}