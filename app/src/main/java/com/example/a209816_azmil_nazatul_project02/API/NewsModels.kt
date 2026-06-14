package com.example.a209816_azmil_nazatul_project02.API

data class NewsResponse(
    val totalArticles: Int,
    val articles: List<Article>
)

data class Article(
    val source: Source,
    val title: String?,
    val description: String?,
    val url: String?,
    val publishedAt: String?
)
data class Source(
    val name: String?,
    val url: String?
)