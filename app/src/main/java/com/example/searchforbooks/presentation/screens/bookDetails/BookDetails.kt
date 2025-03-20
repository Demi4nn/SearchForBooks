package com.example.searchforbooks.presentation.screens.bookDetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.searchforbooks.domain.models.BookModel
import coil.compose.rememberAsyncImagePainter
import com.example.searchforbooks.presentation.screens.search.SearchViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailScreen(
    bookId: String,
    searchViewModel: SearchViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val book = searchViewModel.getBookById(bookId) // Получаем книгу по ID
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(book?.title ?: "Детали книги") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { innerPadding ->
        book?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.thumbnail),
                    contentDescription = "Book thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)

                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = it.title, style = MaterialTheme.typography.headlineSmall)

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Автор(ы): ${it.authors?.joinToString(", ") ?: "Неизвестный автор"}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = it.description ?: "Описание отсутствует", style = MaterialTheme.typography.bodyLarge)
            }
        } ?: run {
            Text(
                text = "Книга не найдена",
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Red
            )
        }
    }
}

/*@Composable
fun BookDetailScreen(book: List<BookModel>, navController: NavController) {
    // Здесь можно получить данные о книге по bookId, например, через ViewModel
    // Для примера просто создадим заглушку
    val book = BookModel(
        id = ,
        title = "Название книги",
        authors = listOf("Автор 1", "Автор 2"),
        thumbnail = "URL_картинки",
        description = "Описание книги",
        publishedDate = "Неизвестно"
    )


    // Кнопка возврата назад
    IconButton(
        onClick = { navController.popBackStack() },
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        // Показываем информацию о книге
        Image(
            painter = rememberAsyncImagePainter(book.thumbnail),
            contentDescription = "Book thumbnail",
            modifier = Modifier.height(200.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = book.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Автор(ы): ${book.authors?.joinToString(", ")}")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Описание: ${book.description}")

    }
}*/