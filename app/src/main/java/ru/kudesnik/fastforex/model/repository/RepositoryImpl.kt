package ru.kudesnik.fastforex.model.repository

import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll
import ru.kudesnik.fastforex.model.entities.rest.CurrencyRepo

class RepositoryImpl : Repository {


    override fun getAllCurrency(base: String): FetchAll {
        val dto = CurrencyRepo.api.getAllCurrency(fromCurrency = "RUB").execute().body()

        return FetchAll(
            base = base,
            ms = dto?.ms ?: 0,
            results = dto?.results ?: mapOf("USD" to 0.0),
            updated = dto?.updated ?: ""


        )

    }
}