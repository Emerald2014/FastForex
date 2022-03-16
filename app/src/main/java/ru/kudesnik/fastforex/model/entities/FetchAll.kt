package ru.kudesnik.fastforex.model.entities

data class FetchAll(
    val base: String,
    val ms: Int,
    val results: Map<String, Double>,
    val updated: String,
    var isFavourites: Boolean = false
)