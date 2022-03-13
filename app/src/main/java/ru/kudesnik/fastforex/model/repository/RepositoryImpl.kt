package ru.kudesnik.fastforex.model.repository

import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll
import ru.kudesnik.fastforex.model.entities.rest.CurrencyRepo

class RepositoryImpl : Repository {
    override fun getCurrenciesName(): Currencies {
        val dto = CurrencyRepo.currenciesName.getCurrenciesName().execute().body()
        return Currencies(
            ms = dto?.ms ?: 0,
            currencies = dto?.currencies ?: mapOf("USD" to "Dollar")
        )
    }

    override fun getAllCurrency(base: String): FetchAll {
        val dto = CurrencyRepo.api.getAllCurrency(fromCurrency = base).execute().body()

        return FetchAll(
            base = base,
            ms = dto?.ms ?: 0,
            results = dto?.results ?: mapOf("USD" to 0.0),
            updated = dto?.updated ?: ""


        )

    }


}