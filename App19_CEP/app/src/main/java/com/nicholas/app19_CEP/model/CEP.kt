package com.jvhp.app19_cep_r_sp.data.model

import com.google.gson.annotations.SerializedName

class CEP(@SerializedName("logradouro") val logradouro: String,
          @SerializedName("bairro") val bairro: String,
          @SerializedName("localidade") val cidade: String,
          @SerializedName("uf") val uf: String) {

    override fun toString(): String {
        return "Rua: $logradouro\nBairro: $bairro\nQue fica em: $cidade / $uf"
    }

}