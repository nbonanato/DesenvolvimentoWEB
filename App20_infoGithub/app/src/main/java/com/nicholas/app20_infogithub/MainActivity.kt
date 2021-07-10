package com.pdm.app19_infousuariogithubwithretrofit

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.pdm.app19_infousuariogithubwithretrofit.domain.GithubService
import com.pdm.app19_infousuariogithubwithretrofit.util.Network
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import retrofit2.http.Url
import java.net.URL
import java.net.URLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var campoUsuario: EditText
    private lateinit var botaoUsuario: Button
    private lateinit var respostaUsuario: TextView
    private lateinit var carregamentoUsuario : ProgressBar
    private lateinit var userImg : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        campoUsuario = findViewById(R.id.edtUsuario)
        botaoUsuario = findViewById(R.id.btnBuscarUsuario)
        respostaUsuario = findViewById(R.id.txtUsuarioResponse)
        carregamentoUsuario = findViewById(R.id.prgLoading)

        userImg = findViewById(R.id.userImg)

        botaoUsuario.setOnClickListener {
            val userLogin = campoUsuario.text.toString()
            if(userLogin.isNotEmpty()){
                buscarUsuario(userLogin)
            } else {
                campoUsuario.error = "Digite um usuário válido"
            }
        }
    }

    fun buscarUsuario(user: String){
        val retrofitClient = Network.retrofitConfig("https://api.github.com/users/")
        val servico = retrofitClient.create(GithubService::class.java)

        CoroutineScope(IO).launch {
            try {
                val response = servico.buscarUsuarioGithub(user)

                withContext(Main){
                    changeLoadingVisibility(isVisibile = true)
                    delay(2000L)
                    if(response.isSuccessful){
                        changeLoadingVisibility(isVisibile = false)
                        respostaUsuario.text = response.body().toString()

                        Picasso.with(baseContext).load(Uri.parse(response.body()?.avatar)).into(userImg)

                    }
                }

            } catch (e: Exception){
                withContext(Main){
                    changeLoadingVisibility(isVisibile = false)
                    respostaUsuario.text = "Não foi possível processar a sua solicitação."
                }
            }
        }
    }

    fun changeLoadingVisibility(isVisibile: Boolean){
        if(isVisibile){
            respostaUsuario.text = ""
            carregamentoUsuario.visibility = View.VISIBLE
            botaoUsuario.visibility = View.INVISIBLE
        } else {
            carregamentoUsuario.visibility = View.INVISIBLE
            botaoUsuario.visibility = View.VISIBLE
        }
    }
}