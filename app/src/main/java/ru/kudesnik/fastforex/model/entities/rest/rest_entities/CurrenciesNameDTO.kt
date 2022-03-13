package ru.kudesnik.fastforex.model.entities.rest.rest_entities

data class CurrenciesNameDTO(
   val currencies: Map<String, String>,
   val ms: Int

)
