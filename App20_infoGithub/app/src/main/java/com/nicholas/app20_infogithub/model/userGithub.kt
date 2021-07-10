package com.pdm.app19_infousuariogithubwithretrofit.data.model
import com.google.gson.annotations.SerializedName

class userGithub(
    @SerializedName("name") val userName: String,
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("created_at") val created: String,
    @SerializedName("updated_at") val updated: String,
    @SerializedName("repos_url") val reposUrl: String) {

    //Sobrecarga de método
    override fun toString() : String {
        return "Nome: $userName\nLogin:$login\nRepositórios: $reposUrl\nCriado em: $created\nÚltima atualização: $updated"
    }
}