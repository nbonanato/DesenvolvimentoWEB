package com.jvhp.app8_listapetsfoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvPets)

        val lista = mutableListOf<Pet>(
            Pet(nome = "Mel", idade = "9", raca = Raca.CACHORRO, foto = resources.getDrawable(R.drawable.mel)),
            Pet(nome="Jabota", idade = "459", raca= Raca.JABUTI, foto = resources.getDrawable(R.drawable.ic_launcher_background))
        )

        rv.adapter = PetAdapter(lista)

        rv.layoutManager = LinearLayoutManager(this)


    }

}