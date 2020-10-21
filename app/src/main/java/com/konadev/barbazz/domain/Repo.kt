package com.konadev.barbazz.domain

import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.vo.Resource

interface Repo {
    // Obtener trago desde Retrofit
    suspend fun getTragosList(tragoName: String) : Resource<List<Drink>>
    // Obtener la lista de tragos favoritos desde Room
    suspend fun getTragosFavoritos() : Resource<List<DrinkEntity>>
    // Guardar trago favorito
    suspend fun insertTrago(trago: DrinkEntity)
    // Borrar trago favorito
    suspend fun deleteTrago(trago: DrinkEntity)
}