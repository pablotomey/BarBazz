package com.konadev.barbazz.data

import com.konadev.barbazz.AppDatabase
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.vo.Resource
import com.konadev.barbazz.vo.RetrofitClient

class DataSource(private val roomDatabase: AppDatabase) {

    suspend fun getTragoByName(tragoName: String) : Resource<List<Drink>> {
        return Resource.Success(RetrofitClient.webService.getTragoByName(tragoName).drinkList)
    }
    // Guardar en Room
    suspend fun saveTragoIntoRoom(trago: DrinkEntity) {
        roomDatabase.tragoDao().insertFavorite(trago)
    }
    // Obtener lista de tragos favoritos desde Room
    suspend fun getTragosFavoritos() : Resource<List<DrinkEntity>> {
        return Resource.Success(roomDatabase.tragoDao().getAllFavoriteDrinks())
    }
    // Eliminar trago de lista favoritos
    suspend fun deleteTragoFavorito(trago: DrinkEntity) {
        roomDatabase.tragoDao().deleteTrago(trago)
    }
}