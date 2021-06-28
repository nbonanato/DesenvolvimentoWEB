package com.nicholas.app07_listagemusuarios

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class UsuarioAdapter(var listaUsuario: MutableList<Usuario>):RecyclerView.Adapter<UsuarioAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgFoto: ImageView = view.findViewById(R.id.imgFoto)
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtStack: TextView = view.findViewById(R.id.txtStack)
        val txtSenioridade: TextView = view.findViewById(R.id.txtSenioridade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        listaUsuario[position].foto?.let{
            holder.imgFoto.setImageDrawable(it)
        }
        holder.txtNome.text = listaUsuario[position].nome
        holder.txtStack.text = listaUsuario[position].stack.nome
        holder.txtSenioridade.text = listaUsuario[position].senioridade.nome

    }
    override fun getItemCount(): Int {
        return listaUsuario.size
    }
}
