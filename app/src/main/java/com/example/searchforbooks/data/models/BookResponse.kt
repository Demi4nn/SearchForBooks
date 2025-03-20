package com.example.searchforbooks.data.models

import com.google.gson.annotations.SerializedName

data class BookResponse(
    @SerializedName("items")
    val books: List<BookItem>?
)