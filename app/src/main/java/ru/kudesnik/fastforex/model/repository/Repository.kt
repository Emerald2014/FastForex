package ru.kudesnik.fastforex.model.repository

import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll

interface Repository {
//    fun getOneCurrency(base: Currencies): FetchAll
    fun getAllCurrency(base: String): FetchAll
}