package com.jvhp.app9_tarefas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TarefaAdapter (var listaTarefas: MutableList<Tarefa>):
RecyclerView.Adapter<TarefaAdapter.ItemViewHolder>(){
        class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

            val txtTitulo: TextView = view.findViewById(R.id.txtTitulo)
            val txtDescricao: TextView = view.findViewById(R.id.txtDescricao)
            val txtImportancia: TextView = view.findViewById(R.id.txtImportancia)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefas, parent, false)
            return ItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

            holder.txtTitulo.text = listaTarefas[position].titulo
            holder.txtDescricao.text = listaTarefas[position].desc
            holder.txtImportancia.text = listaTarefas[position].importancia.grau
        }

        override fun getItemCount(): Int {
            return listaTarefas.size
        }

}