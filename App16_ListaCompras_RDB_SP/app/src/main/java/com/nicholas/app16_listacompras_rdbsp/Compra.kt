package com.jvhp.app16_listacompras_rdb_sp

import androidx.room.*

@Entity(tableName = "TB_COMPRAS")
data class Compra(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "Item")
    var item: String,
    @ColumnInfo(name = "Quantidade")
    var quantidade: Int,
)