package com.example.apilist.model

data class CardList(
    val count: Int,
    val data: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int
)