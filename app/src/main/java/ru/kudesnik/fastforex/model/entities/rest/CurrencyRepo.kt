package ru.kudesnik.fastforex.model.entities.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyRepo {
    val api : CurrencyAllAPI by lazy{
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()
        adapter.create(CurrencyAllAPI::class.java)
    }
    val currenciesName: CurrencyAPI by lazy {
        val adapter = Retrofit.Builder()
            .baseUrl(ApiUtils.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(ApiUtils.getOkHTTPBuilder())
            .build()
        adapter.create(CurrencyAPI::class.java)
    }
}