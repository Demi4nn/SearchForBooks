package com.example.searchforbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.searchforbooks.presentation.components.BottomNavigationBar
import com.example.searchforbooks.presentation.navigation.AppNavigationGraph
import com.example.searchforbooks.presentation.ui.theme.SearchForBooksTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { App () }
    }


    @Composable
    fun App () {
        val navController = rememberNavController()
        SearchForBooksTheme {
            Scaffold (
                modifier = Modifier.fillMaxSize(),
                bottomBar = { BottomNavigationBar(navController)}
            ) { innerPadding ->
                AppNavigationGraph(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}