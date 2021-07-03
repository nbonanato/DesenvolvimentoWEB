package com.jvhp.app11_listapetsfotocadastro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class PetAdapter (var listaPet: MutableList<Pet>, var context: Context):
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
        listaPet[position].foto?.let{
            holder.imgFoto.setImageBitmap(it)
        }

        holder.txtNome.text = listaPet[position].nome
        holder.txtIdade.text = listaPet[position].idade
        holder.txtRaca.text = listaPet[position].raca.nome

        holder.itemView.setOnClickListener(){
            val texto = if (listaPet[position].castrado){
                "Pet castrado!"
            }
            else
            {
                "Pet não castrado!"
            }
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return listaPet.size
    }

}