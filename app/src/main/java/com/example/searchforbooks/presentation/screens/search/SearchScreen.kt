package com.example.searchforbooks.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.searchforbooks.R
import com.example.searchforbooks.domain.models.BookModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    navController: NavController  // передаем NavController
) {
    val state = searchViewModel.searchScreenState
    val query = searchViewModel.searchQuery
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Строка поиска с иконкой
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, start = 20.dp, bottom = 12.dp, end = 20.dp)
                .height(48.dp)
                .background(color = Color.Gray.copy(alpha = 0.1f), shape = RoundedCornerShape(12.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Иконка поиска
            Icon(
                painter = painterResource(id = R.drawable.search_icon), // Замените на свой ресурс
                contentDescription = "Search Icon",
                modifier = Modifier
                    .padding(start = 12.dp)
                    .size(24.dp),
                tint = Color.LightGray
            )

            // Текстовое поле
            BasicTextField(
                value = query,
                onValueChange = { searchViewModel.onSearchQueryChanged(it) },
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
                    .padding(top = 12.dp, start = 20.dp, bottom = 12.dp, end = 20.dp)
                    .height(48.dp),
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp, // Это важно для вертикального выравнивания
                    color = Color.Black
                )
            )
        }

        // Отображение состояний
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is SearchScreenState.Empty -> {
                    Text(
                        "Введите название книги, которую ищете",
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
                is SearchScreenState.Loading -> {
                    CircularProgressIndicator()
                }
                is SearchScreenState.Success -> {
                    BookListView(books = state.books, navController = navController)
                }
                is SearchScreenState.EmptyResults -> {
                    Text(
                        "По вашему запросу ничего не найдено",
                        textAlign = TextAlign.Center,
                        style = TextStyle(fontSize = 18.sp)
                    )
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
}
@Composable
fun BookListView(books: List<BookModel>, navController: NavController) {
    // Используем LazyVerticalGrid для отображения книг в 2 столбца
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Указываем, что 2 элемента в ряду
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(books) { book ->
            BookItemView(book, navController = navController)

        }
    }
}

@Composable
fun BookItemView(book: BookModel, navController: NavController) {



    Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
                .clickable {
                    // При клике переходим на экран с деталями книги
                    navController.navigate("book_detail_screen/${book.id}")
                },

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (book.thumbnail!!.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(book.thumbnail),
                    contentDescription = "Book thumbnail",
                    modifier = Modifier
                        .fillMaxWidth()    // Заполняет всю ширину
                        .height(200.dp)// Оставляет высоту как есть, без растяжения
                        .clip(RoundedCornerShape(16.dp)) // Закругленные углы
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.authors?.joinToString(", ") ?: "Неизвестный автор",
                style = TextStyle(
                    fontSize = 14.sp, // Размер шрифта 14px
                    color = Color(0xFF8F8F8F) // Цвет текста 8F8F8F
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = book.title,
                style = TextStyle(
                    fontSize = 14.sp, // Размер шрифта 14px
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left
            )


        }
    }



/*@Composable
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
                    painter = rememberAsyncImagePainter(book.thumbnail),
                    contentDescription = "Book thumbnail",
                    modifier = Modifier.fillMaxWidth().height(200.dp).padding(top = 8.dp)
                )
            }
            Text(text = book.title)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = book.authors?.joinToString(", ") ?: "Неизвестный автор")
        }
    }
}*/