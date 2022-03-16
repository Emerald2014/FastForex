package ru.kudesnik.fastforex.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kudesnik.fastforex.model.AppState
import ru.kudesnik.fastforex.model.repository.Repository

class FavouriteViewModel(private val repository: Repository) : ViewModel() {
    private val liveData = MutableLiveData<AppState>()
    private val favouriteLiveData = MutableLiveData<Unit>()
    val favouriteLiveDataString = MutableLiveData<String>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getCurrencyList(base: String, to: String) {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(
                AppState.SuccessFavourites(
                    repository.getMultiCurrency(base, repository.getAllHistory()),
                    repository.getCurrenciesName(),

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

    fun getFavourites() {
        viewModelScope.launch(Dispatchers.IO) {
            favouriteLiveDataString.postValue(repository.getAllHistory())
        }
    }
}