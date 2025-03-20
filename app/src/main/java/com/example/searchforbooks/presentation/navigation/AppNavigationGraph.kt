package com.example.searchforbooks.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.searchforbooks.presentation.components.BottomNavigationBar
import com.example.searchforbooks.presentation.screens.favorites.FavoritesScreen
import com.example.searchforbooks.presentation.screens.search.SearchScreen


@Composable
fun AppNavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier) {
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Search.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = BottomNavItem.Search.route) {
                SearchScreen()
            }
            composable(route = BottomNavItem.Favorites.route) {
                FavoritesScreen()
            }
        }
    }
}