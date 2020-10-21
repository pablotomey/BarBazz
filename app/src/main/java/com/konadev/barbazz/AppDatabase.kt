package com.konadev.barbazz

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.konadev.barbazz.data.model.DrinkEntity
import com.konadev.barbazz.domain.TragosDao

@Database(entities = arrayOf(DrinkEntity::class),version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun tragoDao() : TragosDao

    //  Singleton para asegurar la utilización de una sola instancia de appDatabase para no tener prpblemas de concurrencia o sobreescritura de datos
    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDataBase(context: Context) : AppDatabase {
            // elvis operator - si al instancia es nula '?:' realizar...
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"tabla_tragos").build()
            return INSTANCE!!
        }
        
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}