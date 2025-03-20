package com.example.searchforbooks.data.models

import com.google.gson.annotations.SerializedName

data class BookItem(
    @SerializedName("id")
    val id: String,

    @SerializedName("volumeInfo")
    val volumeInfo: VolumeInfo?
)
