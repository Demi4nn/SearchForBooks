package com.example.searchforbooks.domain.models

data class BookModel(
    val id: String,
    val title: String,
    val authors: List<String>?,
    val publishedDate: String?,
    val description: String?,
    val thumbnail: String?
)
