package com.jvhp.app8_listapetsfoto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PetAdapter (var listaPets: MutableList<Pet>):
    RecyclerView.Adapter<PetAdapter.ItemViewHolder>(){

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgFoto: ImageView = view.findViewById(R.id.imgFoto)
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtIdade: TextView = view.findViewById(R.id.txtIdade)
        val txtRaca: TextView = view.findViewById(R.id.txtRaca)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        listaPets[position].foto?.let{
            holder.imgFoto.setImageDrawable(it)
        }
        holder.txtNome.text = listaPets[position].nome
        holder.txtIdade.text = listaPets[position].idade
        holder.txtRaca.text = listaPets[position].raca.nome
    }

    override fun getItemCount(): Int {
        return listaPets.size
    }

}