package ru.kudesnik.fastforex.model.repository

import android.util.Log
import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll
import ru.kudesnik.fastforex.model.entities.database.HistoryDatabase
import ru.kudesnik.fastforex.model.entities.database.HistoryEntity
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

    override fun getMultiCurrency(base: String, to: String): FetchAll {
        val dto =
            CurrencyRepo.apiMulti.getMultiCurrency(fromCurrency = base, toCurrency = to).execute()
                .body()

        return FetchAll(
            base = base,
            ms = dto?.ms ?: 0,
            results = dto?.results ?: mapOf("USD" to 0.0),
            updated = dto?.updated ?: ""
        )
    }

    override fun saveEntity(name: String) {
        HistoryDatabase.db.historyDao().insert(convertCurrencyToHistoryEntity(name))
    }

    override fun getAllHistory(): String {
        return convertHistoryCurrencyToString(HistoryDatabase.db.historyDao().all())

    }

    override fun deleteCurrency(name: String) {
        HistoryDatabase.db.historyDao().deleteByName(convertCurrencyToHistoryEntity(name).currency)
    }

    override fun updateCurrency(name: String) {
        TODO("Not yet implemented")
    }

    private fun convertCurrencyToHistoryEntity(name: String): HistoryEntity {
        return HistoryEntity(
            currency = name
        )
    }

    private fun convertHistoryCurrencyToString(currency: List<HistoryEntity>): String {
        val sb = StringBuilder()
        for (item in currency) {
            sb.append(item.currency).append(",")
        }
        Log.d("myTag", sb.toString())
        return sb.toString()
    }

}