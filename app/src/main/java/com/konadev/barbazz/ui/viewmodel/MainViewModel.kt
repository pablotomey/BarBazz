package com.konadev.barbazz.ui.viewmodel

import androidx.lifecycle.*
import com.konadev.barbazz.data.model.Drink
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.domain.Repo
import com.konadev.barbazz.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repo: Repo) : ViewModel() {

    private val tragosData = MutableLiveData<String>()

    fun setTrago(tragoName: String) {
        tragosData.value = tragoName
    }

    // al iniciar la app se cargarán los tragos de margarita por defecto
    init {
        setTrago("margarita")
    }

    val fetchTragosList = tragosData.distinctUntilChanged().switchMap { tragoName ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(repo.getTragosList(tragoName))
            } catch (e: Exception){
                emit(Resource.Failure(e))
            }
        }
    }

    fun guardarTrago(trago: DrinkEntity) {
        // permite limpiar procesos cuando muere el fragment o activity
        viewModelScope.launch {
            repo.insertTrago(trago)
        }
    }

    fun deleteTrago(trago: DrinkEntity){
        viewModelScope.launch {
            repo.deleteTrago(trago)
        }
    }

    fun getTragosFavoritos() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getTragosFavoritos())
        } catch (e: Exception){
            emit(Resource.Failure(e))
        }
    }

}