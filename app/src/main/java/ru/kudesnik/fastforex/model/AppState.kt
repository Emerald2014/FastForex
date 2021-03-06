package ru.kudesnik.fastforex.model

import ru.kudesnik.fastforex.model.entities.Currencies
import ru.kudesnik.fastforex.model.entities.FetchAll

sealed class AppState {
    data class Success(val currencyData: FetchAll, val currenciesName: Currencies, val dataFavor: String) : AppState()
    data class SuccessName(val currenciesName: Currencies) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
