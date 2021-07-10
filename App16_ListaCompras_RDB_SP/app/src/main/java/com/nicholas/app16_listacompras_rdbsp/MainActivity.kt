package com.jvhp.app16_listacompras_rdb_sp

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
    lateinit var compraAdapter: CompraAdapter
    lateinit var edtCompra: EditText
    lateinit var edtQuantidade: EditText
    lateinit var btnIncluir: ImageButton
    lateinit var prefenciasCompra: SharedPreferences
    lateinit var rv: RecyclerView

    var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefenciasCompra = getSharedPreferences("compraPreferences",
        Context.MODE_PRIVATE)

        rv = findViewById<RecyclerView>(R.id.rvCompras)

        btnIncluir = findViewById(R.id.btnIncluir)
        edtCompra = findViewById(R.id.edtCompra)
        edtQuantidade = findViewById(R.id.edtQuantidade)

        btnIncluir.setOnClickListener(){
            if (edtCompra.text.toString().isNotEmpty()) {
                if (edtQuantidade.text.toString().isNotEmpty()) {

                    adicionarTarefa(edtCompra.text.toString(), edtQuantidade.text.toString().toInt())
                    edtCompra.text.clear()
                    edtQuantidade.text.clear()

                    val editor = prefenciasCompra.edit()
                    editor.remove("NOME")
                    editor.commit()
                }else{

                    edtQuantidade.error = "Digite uma quantidade"

                }
            } else {
                edtCompra.error = "Insira um Item"
            }
        }
    }

    fun adicionarTarefa(nomeCompra: String, quantidade: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            db?.compraDao()?.addItens(Compra(item = nomeCompra, quantidade = quantidade))

            val compraDAO = db?.compraDao()

            val resposta = compraDAO?.getItens()

            withContext(Dispatchers.Main) {
                resposta?.let{
                    compraAdapter.refreshListCompra(resposta)
                }
            }

        }
    }

    fun recuperarLista(){
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            val compraDAO = db?.compraDao()

            val resposta = compraDAO?.getItens()

            withContext(Dispatchers.Main){
                resposta?.let{
                    compraAdapter = CompraAdapter(it, this@MainActivity)

                    rv.adapter = compraAdapter

                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        if (edtCompra.text.toString().isNotEmpty()){

            val editor = prefenciasCompra.edit()

            editor.putString("NOME", edtCompra.text.toString())

            editor.commit()

        }
    }

    override fun onResume() {
        super.onResume()

        edtCompra.setText(prefenciasCompra.getString("NOME", null))
    }

    override fun onStart() {
        super.onStart()

        recuperarLista()
    }

    fun excluirTarefa(compra: Compra){
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            db?.compraDao()?.deleteItens(compra)

            val compraDAO = db?.compraDao()

            val resposta = compraDAO?.getItens()

            withContext(Dispatchers.Main){
                resposta?.let{
                    compraAdapter.refreshListCompra(resposta)

                    Toast.makeText(this@MainActivity, "Item excluído",
                    Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}