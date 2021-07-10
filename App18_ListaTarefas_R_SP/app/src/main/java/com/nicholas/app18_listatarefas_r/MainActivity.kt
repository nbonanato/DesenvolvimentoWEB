package com.jvhp.app18_listatarefas_r_sp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    lateinit var tarefaAdapter: TarefaAdapter
    lateinit var edtTarefa: EditText
    lateinit var btnIncluir: ImageButton
    lateinit var prefenciasTarefa: SharedPreferences
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefenciasTarefa = getSharedPreferences("tarefaPreferences",
        Context.MODE_PRIVATE)

        rv = findViewById<RecyclerView>(R.id.rvTarefas)

        btnIncluir = findViewById(R.id.btnIncluir)
        edtTarefa = findViewById(R.id.edtTarefa)

        btnIncluir.setOnClickListener(){
            if (edtTarefa.text.toString().isNotEmpty()) {
                adicionarTarefa(edtTarefa.text.toString())
                edtTarefa.text.clear()

                val editor = prefenciasTarefa.edit()
                editor.remove("NOME")
                editor.commit()
            } else {
                edtTarefa.error = "Insira um texto válido"
            }
        }
    }

    fun adicionarTarefa(nomeTarefa: String) {
        val retrofitClient = Network.retrofitConfig("https://backend-tarefa.herokuapp.com/")
        val servico = retrofitClient.create(TarefaService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                servico.adicionarTarefa(Tarefa(nome = nomeTarefa))
                val response = servico.buscarTarefas()

                withContext(Main){
                    if (response.isSuccessful){
                        response.body()?.let{
                            tarefaAdapter.refreshListTarefa(it)
                        }
                        Toast.makeText(this@MainActivity, "Tarefa adicionada", Toast.LENGTH_LONG).show()
                    }
                }
            }catch(e: Exception){
                Log.e("ADD_TAREFA", e.toString(), e)
                withContext(Main){
                    Toast.makeText(this@MainActivity, "Não foi possível processar a sua solicitação!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    fun recuperarLista(){
        val retrofitClient = Network.retrofitConfig("https://backend-tarefa.herokuapp.com/")
        val servico = retrofitClient.create(TarefaService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respose = servico.buscarTarefas()

                    withContext(Main) {
                        if (respose.isSuccessful){
                            respose.body()?.let{
                                tarefaAdapter = TarefaAdapter(it, this@MainActivity)

                                rv.adapter = tarefaAdapter

                                rv.layoutManager = LinearLayoutManager(this@MainActivity)

                            }
                        }

                    }
            } catch (e: Exception) {
                Log.e("SELECT_TAREFA", e.toString(), e)
                withContext(Main){
                    Toast.makeText(this@MainActivity, "Não foi possível processar a sua solicitação!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if (edtTarefa.text.toString().isNotEmpty()){

            val editor = prefenciasTarefa.edit()

            editor.putString("NOME", edtTarefa.text.toString())

            editor.commit()

        }
    }

    override fun onResume() {
        super.onResume()

        edtTarefa.setText(prefenciasTarefa.getString("NOME", null))
    }

    override fun onStart() {
        super.onStart()

        recuperarLista()
    }

    fun excluirTarefa(tarefa: Tarefa){
        val retrofitClient = Network.retrofitConfig("https://backend-tarefa.herokuapp.com/")
        val servico = retrofitClient.create(TarefaService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                servico.excluirTarefa(tarefa.id)
                val response = servico.buscarTarefas()

                withContext(Main){
                    if (response.isSuccessful){
                        response.body()?.let{
                            tarefaAdapter.refreshListTarefa(it)
                        }
                        Toast.makeText(this@MainActivity, "Tarefa excluída", Toast.LENGTH_LONG).show()
                    }
                }
            }catch(e: Exception){
                Log.e("DELEE_TAREFA", e.toString(), e)
                withContext(Main){
                    Toast.makeText(this@MainActivity, "Não foi possível processar a sua solicitação!", Toast.LENGTH_LONG).show()
                }
            }

        }
    }

}