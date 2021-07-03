package com.jvhp.app10_listausuariosfotocadastro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    lateinit var usuarioAdapter: UsuarioAdapter
    lateinit var btnIncluir: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.titulo_lista)

        val rv = findViewById<RecyclerView>(R.id.rvUsuarios)
        usuarioAdapter = UsuarioAdapter(lista, this)
        btnIncluir = findViewById(R.id.btnIncluir)

        btnIncluir.setOnClickListener(){
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        rv.adapter = usuarioAdapter

        rv.layoutManager = LinearLayoutManager(this)
    }

    companion object{
        val lista = mutableListOf<Usuario>()
    }

    override fun onResume() {
        super.onResume()
        usuarioAdapter.notifyDataSetChanged()
    }

}