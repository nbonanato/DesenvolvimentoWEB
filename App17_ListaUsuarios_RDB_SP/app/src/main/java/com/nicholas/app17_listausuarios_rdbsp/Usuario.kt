package com.jvhp.app17_listausuarios_rdb_sp

import android.graphics.Bitmap
import androidx.room.*
import com.jvhp.app17_listausuarios_rdb_sp.Senioridade
import com.jvhp.app17_listausuarios_rdb_sp.Stack

@Entity(tableName = "TB_USUARIOS")
data class Usuario(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    /*@ColumnInfo(name = "Foto")
    var foto: Bitmap? = null,*/
    @ColumnInfo(name = "Nome")
    var nome: String,
    @ColumnInfo(name = "Email")
    var email: String,
    @ColumnInfo(name = "Stack")
    var stack: Stack,
    @ColumnInfo(name = "Senioridade")
    var senioridade: Senioridade,
    @ColumnInfo(name = "Empregado")
    var empregado: Boolean
)