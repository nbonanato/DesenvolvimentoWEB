package com.jvhp.app16_listacompras_rdb_sp

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var instancia: AppDatabase ?= null

    fun getAppDatabase(context: Context): AppDatabase?{
        if(instancia == null){
            synchronized(AppDatabase::class){
                instancia =
                    Room.databaseBuilder(context.applicationContext,
                    AppDatabase::class.java, "AppDb")
                        .fallbackToDestructiveMigration()
                        .build()
            }
        }
        return instancia
    }
}