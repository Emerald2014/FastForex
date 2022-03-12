package ru.kudesnik.fastforex.model

import ru.kudesnik.fastforex.model.entities.FetchAll

sealed class AppState {
    data class Success(val currencyData: FetchAll) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
