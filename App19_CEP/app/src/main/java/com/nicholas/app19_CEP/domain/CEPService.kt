package com.jvhp.app19_cep_r_sp.domain

import com.jvhp.app19_cep_r_sp.data.model.CEP
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CEPService {
    @GET("{cepInserido}/json/")
    suspend fun buscarCEP(
        @Path("cepInserido") cep : String
    ) : Response<CEP>
}