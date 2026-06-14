package com.example.a209816_azmil_nazatul_project02

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.a209816_azmil_nazatul_project02.API.Article

@Composable
fun NewsScreen(navController: NavController) {

    val newsViewModel: NewsViewModel = viewModel()
    val uiState by newsViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            PeoplesVoiceTopBar(
                title = "Berita Terkini",
                navController = navController,
                showBack = true
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { newsViewModel.fetchNews() }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Refresh News"
                )
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            when (uiState) {

                is NewsUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                is NewsUiState.Error -> {

                    val errorMessage =
                        (uiState as NewsUiState.Error).message

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Gagal memuatkan berita.",
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = errorMessage,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { newsViewModel.fetchNews() }
                        ) {
                            Text("Cuba Lagi")
                        }
                    }
                }

                is NewsUiState.Success -> {

                    val articles =
                        (uiState as NewsUiState.Success).articles

                    if (articles.isEmpty()) {

                        Text(
                            text = "Tiada berita dijumpai.",
                            modifier = Modifier.align(Alignment.Center)
                        )

                    } else {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            items(articles) { article ->
                                NewsCard(article = article)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsCard(article: Article) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {

                article.url?.let { url ->

                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(url)
                    )

                    context.startActivity(intent)
                }
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            article.source.name?.let { sourceName ->

                Text(
                    text = sourceName,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(4.dp))
            }

            Text(
                text = article.title ?: "Tiada Tajuk",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            article.description?.let { description ->

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            article.publishedAt?.let { date ->

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = date.take(10),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}