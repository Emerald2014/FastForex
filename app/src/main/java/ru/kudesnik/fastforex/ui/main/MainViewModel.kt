package ru.kudesnik.fastforex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudesnik.fastforex.model.AppState
import ru.kudesnik.fastforex.model.repository.Repository
import ru.kudesnik.fastforex.model.repository.RepositoryImpl

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()
    private val favouriteLiveData = MutableLiveData<Unit>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getCurrencyList(base: String) {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(
                AppState.Success(
                    repository.getAllCurrency(base),
                    repository.getCurrenciesName(),
                    repository.getAllHistory()
                )
            )
        }.start()
    }

    fun getCurrenciesName() {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue((AppState.SuccessName(repository.getCurrenciesName())))
        }
    }

    fun setFavourites(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteLiveData.postValue(repository.saveEntity(name))
        }
    }
    fun deleteFavourites(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteLiveData.postValue(repository.deleteCurrency(name))
        }
    }
}