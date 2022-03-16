package ru.kudesnik.fastforex.model.repository

import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll

interface Repository {
    //    fun getOneCurrency(base: Currencies): FetchAll
    fun getAllCurrency(base: String): FetchAll
    fun getMultiCurrency(base: String, to: String): FetchAll
    fun getCurrenciesName(): Currencies

    fun saveEntity(name: String)

    fun getAllHistory(): String
    fun deleteCurrency(name: String)
    fun updateCurrency(name: String)
}