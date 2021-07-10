package com.jvhp.app16_listacompras_rdb_sp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CompraAdapter(
    var listaCompra: List<Compra>, var listener:
    MainActivity
):RecyclerView.Adapter<CompraAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtCompra: TextView = view.findViewById(R.id.txtCompra)
        val txtQuantidade: TextView = view.findViewById(R.id.txtQuantidade)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra,
        parent, false)
        return ItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        holder.txtCompra.text = listaCompra[position].item
        holder.txtQuantidade.text = listaCompra[position].quantidade.toString()

        holder.btnExcluir.setOnClickListener(){
            listener.excluirTarefa(listaCompra[position])
        }

    }

    override fun getItemCount(): Int {

        return listaCompra.size

    }

    fun refreshListCompra(listaAtualizada: List<Compra>){
        listaCompra = listaAtualizada
        notifyDataSetChanged()
    }

}