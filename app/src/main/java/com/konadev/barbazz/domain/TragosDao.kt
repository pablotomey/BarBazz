package com.konadev.barbazz.domain

import androidx.room.*
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity

@Dao
interface TragosDao {

    @Query("SELECT * FROM tragoEntity")
    suspend fun getAllFavoriteDrinks(): List<DrinkEntity>

    // Si insertamos el mismo objeto - mismo ID, creamos onConflictStrategy y reemplazámos en véz de duplicar
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(trago: DrinkEntity)

    @Delete
    suspend fun deleteTrago(trago: DrinkEntity)
}