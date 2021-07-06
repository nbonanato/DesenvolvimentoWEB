package com.victor.app16_listacompras

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Compra::class), version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun compraDao():CompraDAO

    // Implementar aqui todos os DAOs que o App tiver daqui pra baixo...
}
