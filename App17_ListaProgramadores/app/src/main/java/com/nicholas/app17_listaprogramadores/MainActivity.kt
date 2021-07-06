package com.victor.app17_listaprogramadores

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), ProgramadorAdapterListener {

    lateinit var programadorAdapter: ProgramadorAdapter
    lateinit var edtProgramador: EditText
    lateinit var btnIncluir: ImageButton
    lateinit var preferenciasProgramador: SharedPreferences
    lateinit var rv: RecyclerView

    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // #Preferencias
        // Criando o arquivo de preferências
        preferenciasProgramador = getSharedPreferences("programadorPreferences", Context.MODE_PRIVATE)

        rv = findViewById<RecyclerView>(R.id.rvProgramadores)

        btnIncluir = findViewById(R.id.btnIncluir)
        edtProgramador = findViewById(R.id.edtProgramador)

        btnIncluir.setOnClickListener() {
            if (edtProgramador.text.toString().isNotEmpty()) {
                adicionarProgramador(edtProgramador.text.toString())
                edtProgramador.text.clear()

                // Apagar a preferência gravada anteriormente
                val editor = preferenciasProgramador.edit()
                editor.remove("NOME")
                editor.commit()
            } else {
                edtProgramador.error = "Insira um texto válido!"
            }
        }
    }

    fun adicionarProgramador(nomeProgramador: String) {
        // Coroutine para Entrada/Saída de Dados
        CoroutineScope(Dispatchers.IO).launch {
            // Cria ou recupera o BD da aplicação
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            // A partir do DAO executa uma ação escolhida (INSERT)
            db?.programadorDao()?.addProgramadores(Programador(nome = nomeProgramador))

            // Recupera ações/métodos de acesso a dados da entidade (tabela)
            val programadorDAO = db?.programadorDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = programadorDAO?.getProgramadores()

            // Coroutine para UI
            withContext(Dispatchers.Main) {
                resposta?.let {
                    programadorAdapter.refreshListProgramador(resposta)
                }
            }
        }
    }

    fun recuperarLista(){
        // Coroutine para Entrada/Saída de Dados
        CoroutineScope(Dispatchers.IO).launch {
            // Cria ou recupera o BD da aplicação
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            // Recupera ações/métodos de acesso a dados da entidade (tabela)
            val programadorDAO = db?.programadorDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = programadorDAO?.getProgramadores()

            // Coroutine para UI
            withContext(Dispatchers.Main){
                resposta?.let{
                    programadorAdapter = ProgramadorAdapter(it, this@MainActivity)

                    // Vincula o Adapter na Recycler View
                    rv.adapter = programadorAdapter

                    // Exibe os itens em uma coluna única no padrão vertical
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    // #Preferencias
    override fun onPause() {
        super.onPause()

        if (edtProgramador.text.toString().isNotEmpty()){

            // Cria e edição na preferência
            val editor = preferenciasProgramador.edit()

            // Escreve um uma preferência
            editor.putString("NOME", edtProgramador.text.toString())

            // Salva a preferência
            editor.commit()
        }
    }

    // #Preferencias
    override fun onResume() {
        super.onResume()

        // Recupera (lê) uma preferência e utiliza ela populando um Edit Text
        edtProgramador.setText(preferenciasProgramador.getString("NOME", null))
    }

    override fun onStart() {
        super.onStart()

        recuperarLista()
    }

    override fun excluirProgramador(programador: Programador) {
        // Coroutine para Entrada/Saída de Dados
        CoroutineScope(Dispatchers.IO).launch {
            // Cria ou recupera o BD da aplicação
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            // A partir do DAO executa uma ação escolhida (DELETE)
            db?.programadorDao()?.deleteProgramador(programador)

            // Recupera ações/métodos de acesso a dados da entidade (tabela)
            val programadorDAO = db?.programadorDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = programadorDAO?.getProgramadores()

            // Coroutine para UI
            withContext(Dispatchers.Main) {
                resposta?.let {
                    programadorAdapter.refreshListProgramador(resposta)

                    Toast.makeText(this@MainActivity, "Programador excluído", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}