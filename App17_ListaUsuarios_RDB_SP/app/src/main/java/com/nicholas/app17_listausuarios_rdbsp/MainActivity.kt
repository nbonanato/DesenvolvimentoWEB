package com.jvhp.app17_listausuarios_rdb_sp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var rv: RecyclerView
    lateinit var prefenciasUsuario: SharedPreferences
    var db: AppDatabase? = null
    lateinit var usuarioAdapter: UsuarioAdapter
    lateinit var btnIncluir: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById<RecyclerView>(R.id.rvUsuarios)

        btnIncluir = findViewById(R.id.btnIncluir)

        btnIncluir.setOnClickListener(){
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }


        }

    fun recuperarLista(){
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            val compraDAO = db?.usuarioDao()

            val resposta = compraDAO?.getUsuario()

            withContext(Dispatchers.Main){
                resposta?.let{
                    usuarioAdapter = UsuarioAdapter(it, this@MainActivity)

                    rv.adapter = usuarioAdapter

                    rv.layoutManager = LinearLayoutManager(this@MainActivity)
                }
            }
        }
    }

    fun excluirUsuario(usuario: Usuario){
        CoroutineScope(Dispatchers.IO).launch {
            db = DatabaseBuilder.getAppDatabase(this@MainActivity)

            db?.usuarioDao()?.deleteUsuario(usuario)

            val usuarioDAO = db?.usuarioDao()

            val resposta = usuarioDAO?.getUsuario()

            withContext(Dispatchers.Main){
                resposta?.let{
                    usuarioAdapter.refreshListUsuario(resposta)

                    Toast.makeText(this@MainActivity, "Item excluído",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        recuperarLista()
    }



}