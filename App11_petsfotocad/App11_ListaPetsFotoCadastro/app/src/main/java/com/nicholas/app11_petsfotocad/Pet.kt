package com.jvhp.app11_listapetsfotocadastro

import android.graphics.Bitmap
import android.graphics.drawable.Drawable

data class Pet(
    var foto: Bitmap?=null,
    var nome: String,
    var idade: String,
    var raca: Raca,
    var castrado: Boolean
)
