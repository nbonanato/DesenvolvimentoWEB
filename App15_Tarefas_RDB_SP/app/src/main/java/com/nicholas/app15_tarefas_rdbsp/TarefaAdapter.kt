package com.jvhp.app15_tarefas_rbd_sp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import java.net.URI

class TarefaAdapter(
    var listaTarefa: List<Tarefa>, var listener:
    MainActivity
):RecyclerView.Adapter<TarefaAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtTarefa: TextView = view.findViewById(R.id.txtTarefa)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tarefa,
        parent, false)
        return ItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        holder.txtTarefa.text = listaTarefa[position].nome

        holder.btnExcluir.setOnClickListener(){
            listener.excluirTarefa(listaTarefa[position])
        }

    }

    override fun getItemCount(): Int {

        return listaTarefa.size

    }

    fun refreshListTarefa(listaAtualizada: List<Tarefa>){
        listaTarefa = listaAtualizada
        notifyDataSetChanged()
    }

}