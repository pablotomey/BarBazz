package com.konadev.barbazz.domain

import com.konadev.barbazz.data.DataSource
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.vo.Resource

class RepoImpl(private val dataSource: DataSource) : Repo {
    override suspend fun getTragosList(tragoName: String): Resource<List<Drink>> {
        return dataSource.getTragoByName(tragoName)
    }

    override suspend fun getTragosFavoritos(): Resource<List<DrinkEntity>> {
        return dataSource.getTragosFavoritos()
    }

    override suspend fun insertTrago(trago: DrinkEntity) {
        dataSource.saveTragoIntoRoom(trago)
    }

    override suspend fun deleteTrago(trago: DrinkEntity) {
        dataSource.deleteTragoFavorito(trago)
    }
}