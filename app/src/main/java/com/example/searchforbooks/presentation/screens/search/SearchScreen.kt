package com.example.searchforbooks.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import coil.compose.rememberImagePainter
import com.example.searchforbooks.domain.models.BookModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = viewModel()
) {
    val state = searchViewModel.searchScreenState
    val query = searchViewModel.searchQuery
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Строка поиска
        BasicTextField(
            value = TextFieldValue(query),
            onValueChange = { searchViewModel.onSearchQueryChanged(it.text) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .height(56.dp)
        )

        // Отображение состояний
        when (state) {
            is SearchScreenState.Empty -> {
                Text("Введите название книги, которую ищете", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is SearchScreenState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is SearchScreenState.Success -> {
                BookListView(books = state.books)
            }
            is SearchScreenState.EmptyResults -> {
                Text("По вашему запросу ничего не найдено", modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is SearchScreenState.Error -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(state.message)
                    Button(onClick = { searchViewModel.onRetrySearch() }) {
                        Text("Попробовать еще")
                    }
                }
            }
        }
    }
}

@Composable
fun BookListView(books: List<BookModel>) {
    LazyColumn {
        items(books) { book ->
            BookItemView(book = book)
        }
    }
}

@Composable
fun BookItemView(book: BookModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!book.thumbnail.isNullOrBlank()) {
                Image(
                    painter = rememberImagePainter(book.thumbnail),
                    contentDescription = "Book thumbnail",
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)
                )
            }
            Text(text = book.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = book.authors?.joinToString(", ") ?: "Неизвестный автор")
        }
    }
}