package ru.kudesnik.fastforex.model.entities.rest.rest_entities

data class CurrencyDTO(
    val base: String,
    val results: Map<String, Double>,
    val updated: String,
    val ms: Int
)