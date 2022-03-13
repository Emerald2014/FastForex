package ru.kudesnik.fastforex.model.entities.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kudesnik.fastforex.API_KEY
import ru.kudesnik.fastforex.model.entities.rest.rest_entities.CurrenciesNameDTO
import ru.kudesnik.fastforex.model.entities.rest.rest_entities.CurrencyDTO

interface CurrencyAllAPI {
    @GET("fetch-all")
    fun getAllCurrency(
        @Query("from") fromCurrency: String = "USD",
        @Query("api_key") apiKey: String = API_KEY
    ): Call<CurrencyDTO>
}

interface CurrencyAPI {
    @GET("currencies")
    fun getCurrenciesName(
        @Query("api_key") apiKey: String = API_KEY
    ): Call<CurrenciesNameDTO>
}