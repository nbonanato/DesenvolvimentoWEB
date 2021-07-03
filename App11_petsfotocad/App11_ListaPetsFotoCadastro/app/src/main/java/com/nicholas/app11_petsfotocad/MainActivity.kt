package com.jvhp.app11_listapetsfotocadastro

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var petAdapter: PetAdapter
    lateinit var btnIncluir: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setTitle(R.string.titulo_lista)

        val rv = findViewById<RecyclerView>(R.id.rvPets)
        petAdapter = PetAdapter(lista, this)
        btnIncluir = findViewById(R.id.btnIncluir)

        btnIncluir.setOnClickListener(){
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }

        rv.adapter = petAdapter

        rv.layoutManager = LinearLayoutManager(this)
    }

    companion object{
        val lista = mutableListOf<Pet>()
    }

    override fun onResume() {
        super.onResume()
        petAdapter.notifyDataSetChanged()
    }
}