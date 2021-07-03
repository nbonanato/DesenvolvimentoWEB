package com.jvhp.app10_listausuariosfotocadastro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter (var listaUsuario: MutableList<Usuario>, var context: Context):
    RecyclerView.Adapter<UsuarioAdapter.ItemViewHolder>(){

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imgFoto: ImageView = view.findViewById(R.id.imgFoto)
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtEmail: TextView = view.findViewById(R.id.txtEmail)
        val txtStack: TextView = view.findViewById(R.id.txtStack)
        val txtSenioridade: TextView = view.findViewById(R.id.txtSenioridade)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        listaUsuario[position].foto?.let{
            holder.imgFoto.setImageBitmap(it)
        }

        holder.txtNome.text = listaUsuario[position].nome
        holder.txtEmail.text = listaUsuario[position].email
        holder.txtStack.text = listaUsuario[position].stack.nome
        holder.txtSenioridade.text = listaUsuario[position].senioridade.nome

        holder.itemView.setOnClickListener(){
            val texto = if (listaUsuario[position].empregado){
                "Dev na ativa!"
            }
            else
            {
                "Dev procurando job!"
            }
            Toast.makeText(context, texto, Toast.LENGTH_SHORT).show()
        }

    }

    override fun getItemCount(): Int {
        return listaUsuario.size
    }

}