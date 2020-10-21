package com.konadev.barbazz.domain

import com.konadev.barbazz.data.model.DrinkList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    // search.php?s=
    @GET("search.php")
    suspend fun getTragoByName(@Query("s") tragoName: String) : DrinkList
}