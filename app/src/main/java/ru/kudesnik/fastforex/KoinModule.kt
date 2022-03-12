package ru.kudesnik.fastforex

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.kudesnik.fastforex.model.repository.Repository
import ru.kudesnik.fastforex.model.repository.RepositoryImpl
import ru.kudesnik.fastforex.ui.main.MainViewModel

val appModule = module {
    single<Repository> { RepositoryImpl() }
    viewModel { MainViewModel(get()) }
}