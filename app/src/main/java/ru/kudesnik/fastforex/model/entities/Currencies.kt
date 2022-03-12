package ru.kudesnik.fastforex.model.entities

data class Currencies(
    val currencies: Map<String, String>,
    val ms: Int
)