package com.pdm.app19_infousuariogithubwithretrofit.domain
import com.pdm.app19_infousuariogithubwithretrofit.data.model.userGithub
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("{login}")
    suspend fun buscarUsuarioGithub(
        @Path("login") login: String
    ) : Response<userGithub>
}