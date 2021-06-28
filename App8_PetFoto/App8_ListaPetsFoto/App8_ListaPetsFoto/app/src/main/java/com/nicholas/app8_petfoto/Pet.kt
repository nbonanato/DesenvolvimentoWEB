package com.jvhp.app8_listapetsfoto

import android.graphics.drawable.Drawable

data class Pet(
    var foto: Drawable?=null,
    var nome: String,
    var idade: String,
    var raca: Raca,
)
