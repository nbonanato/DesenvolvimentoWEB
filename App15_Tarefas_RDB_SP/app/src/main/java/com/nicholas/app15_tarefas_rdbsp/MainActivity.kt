package com.jvhp.app15_tarefas_rbd_sp

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var tarefaAdapter: TarefaAdapter
    lateinit var edtTarefa: EditText
    lateinit var btnIncluir: ImageButton
    lateinit var prefenciasTarefa: SharedPreferences
    lateinit var rv: RecyclerView

    var db: AppDatabase? = null

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
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            db?.tarefaDao()?.addTarefas(Tarefa(nome = nomeTarefa))

            val tarefaDAO = db?.tarefaDao()

            val resposta = tarefaDAO?.getTarefas()

            withContext(Dispatchers.Main) {
                resposta?.let{
                    tarefaAdapter.refreshListTarefa(resposta)
                }
            }

        }
    }

    fun recuperarLista(){
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            val tarefaDAO = db?.tarefaDao()

            val resposta = tarefaDAO?.getTarefas()

            withContext(Dispatchers.Main){
                resposta?.let{
                    tarefaAdapter = TarefaAdapter(it, this@MainActivity)

                    rv.adapter = tarefaAdapter

                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
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
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            db?.tarefaDao()?.deleteTarefa(tarefa)

            val tarefaDAO = db?.tarefaDao()

            val resposta = tarefaDAO?.getTarefas()

            withContext(Dispatchers.Main){
                resposta?.let{
                    tarefaAdapter.refreshListTarefa(resposta)

                    Toast.makeText(this@MainActivity, "Tarefa excluída",
                    Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}