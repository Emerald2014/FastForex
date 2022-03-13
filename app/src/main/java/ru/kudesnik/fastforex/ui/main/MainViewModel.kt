package ru.kudesnik.fastforex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kudesnik.fastforex.model.AppState
import ru.kudesnik.fastforex.model.repository.Repository
import ru.kudesnik.fastforex.model.repository.RepositoryImpl

class MainViewModel(private val repository: Repository) : ViewModel() {
   private val liveData = MutableLiveData<AppState>()

    fun getLiveData(): LiveData<AppState> = liveData

    fun getCurrencyList(base:String) {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue(AppState.Success(repository.getAllCurrency(base), repository.getCurrenciesName()))
        }.start()
    }

    fun getCurrenciesName() {
        liveData.value = AppState.Loading
        Thread {
            liveData.postValue((AppState.SuccessName(repository.getCurrenciesName())))
        }
    }
}