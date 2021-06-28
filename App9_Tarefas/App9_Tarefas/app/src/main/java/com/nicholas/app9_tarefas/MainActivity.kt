package com.jvhp.app9_tarefas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvTarefas)

        val lista = mutableListOf<Tarefa>(
            Tarefa(titulo = "Carregar celular", desc = "Pouca bateria, mas ainda aguenta um tempinho", importancia = Importancia.TRANQUILO),
            Tarefa(titulo = "Lavar Roupa", desc = "Tem muuuuuuuuuuuuuita roupa para lavar", importancia = Importancia.URGENTE),
            Tarefa(titulo = "Pegar café", desc = "Que sooooooono", importancia = Importancia.POUCO)
        )

        rv.adapter = TarefaAdapter(lista)

        rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


    }
}