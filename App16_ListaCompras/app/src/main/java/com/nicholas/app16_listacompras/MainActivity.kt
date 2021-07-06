package com.victor.app16_listacompras

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

class MainActivity : AppCompatActivity(), CompraAdapterListener {

    lateinit var compraAdapter: CompraAdapter
    lateinit var edtCompra: EditText
    lateinit var btnIncluir: ImageButton
    lateinit var preferenciasCompra: SharedPreferences
    lateinit var rv: RecyclerView

    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // #Preferencias
        // Criando o arquivo de preferências
        preferenciasCompra = getSharedPreferences("compraPreferences", Context.MODE_PRIVATE)

        rv = findViewById<RecyclerView>(R.id.rvCompras)

        btnIncluir = findViewById(R.id.btnIncluir)
        edtCompra = findViewById(R.id.edtCompra)

        btnIncluir.setOnClickListener() {
            if (edtCompra.text.toString().isNotEmpty()) {
                adicionarCompra(edtCompra.text.toString())
                edtCompra.text.clear()

                // Apagar a preferência gravada anteriormente
                val editor = preferenciasCompra.edit()
                editor.remove("NOME")
                editor.commit()
            } else {
                edtCompra.error = "Insira um texto válido!"
            }
        }
    }

    fun adicionarCompra(nomeCompra: String) {
        // Coroutine para Entrada/Saída de Dados
        CoroutineScope(Dispatchers.IO).launch {
            // Cria ou recupera o BD da aplicação
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            // A partir do DAO executa uma ação escolhida (INSERT)
            db?.compraDao()?.addCompras(Compra(nome = nomeCompra))

            // Recupera ações/métodos de acesso a dados da entidade (tabela)
            val compraDAO = db?.compraDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = compraDAO?.getCompras()

            // Coroutine para UI
            withContext(Dispatchers.Main) {
                resposta?.let {
                    compraAdapter.refreshListCompra(resposta)
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
            val compraDAO = db?.compraDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = compraDAO?.getCompras()

            // Coroutine para UI
            withContext(Dispatchers.Main){
                resposta?.let{
                    compraAdapter = CompraAdapter(it, this@MainActivity)

                    // Vincula o Adapter na Recycler View
                    rv.adapter = compraAdapter

                    // Exibe os itens em uma coluna única no padrão vertical
                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    // #Preferencias
    override fun onPause() {
        super.onPause()

        if (edtCompra.text.toString().isNotEmpty()){

            // Cria e edição na preferência
            val editor = preferenciasCompra.edit()

            // Escreve um uma preferência
            editor.putString("NOME", edtCompra.text.toString())

            // Salva a preferência
            editor.commit()
        }
    }

    // #Preferencias
    override fun onResume() {
        super.onResume()

        // Recupera (lê) uma preferência e utiliza ela populando um Edit Text
        edtCompra.setText(preferenciasCompra.getString("NOME", null))
    }

    override fun onStart() {
        super.onStart()

        recuperarLista()
    }

    override fun excluirCompra(compra: Compra) {
        // Coroutine para Entrada/Saída de Dados
        CoroutineScope(Dispatchers.IO).launch {
            // Cria ou recupera o BD da aplicação
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            // A partir do DAO executa uma ação escolhida (DELETE)
            db?.compraDao()?.deleteCompra(compra)

            // Recupera ações/métodos de acesso a dados da entidade (tabela)
            val compraDAO = db?.compraDao()

            // A partir do DAO executa uma ação escolhida (SELECT)
            val resposta = compraDAO?.getCompras()

            // Coroutine para UI
            withContext(Dispatchers.Main) {
                resposta?.let {
                    compraAdapter.refreshListCompra(resposta)

                    Toast.makeText(this@MainActivity, "Compra excluída", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}