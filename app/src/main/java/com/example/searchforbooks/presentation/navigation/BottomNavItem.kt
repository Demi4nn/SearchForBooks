package com.example.searchforbooks.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.searchforbooks.R

sealed class BottomNavItem(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Search : BottomNavItem("search_screen", "Поиск", R.drawable.search)
    object Favorites : BottomNavItem("favorites_screen", "Избранное", R.drawable.favorites)
}