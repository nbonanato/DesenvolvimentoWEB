package com.victor.app15_listatarefas

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.victor.app15_listatarefas.Tarefa

@Dao
interface TarefaDAO {
    @Query("SELECT * FROM TB_TAREFA")
    suspend fun getTarefas(): List<Tarefa>

    @Insert
    suspend fun addTarefas(t: Tarefa)

    @Delete
    suspend fun deleteTarefa(t: Tarefa)
}
