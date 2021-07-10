package com.jvhp.app17_listausuarios_rdb_sp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class UsuarioAdapter(
    var listaUsuario: List<Usuario>, var listener: MainActivity):
    RecyclerView.Adapter<UsuarioAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        /*val imgFoto: ImageView = view.findViewById(R.id.imgFoto)*/
        val txtNome: TextView = view.findViewById(R.id.txtNome)
        val txtEmail: TextView = view.findViewById(R.id.txtEmail)
        val txtStack: TextView = view.findViewById(R.id.txtStack)
        val txtSenioridade: TextView = view.findViewById(R.id.txtSenioridade)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario,
        parent, false)
        return ItemViewHolder(view)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int){

        /*listaUsuario[position].foto?.let{
            holder.imgFoto.setImageBitmap(it)
        }*/

        holder.txtNome.text = listaUsuario[position].nome
        holder.txtEmail.text = listaUsuario[position].email
        holder.txtStack.text = listaUsuario[position].stack.nome
        holder.txtSenioridade.text = listaUsuario[position].senioridade.nome

        holder.btnExcluir.setOnClickListener(){
            listener.excluirUsuario(listaUsuario[position])
        }


    }

    override fun getItemCount(): Int {

        return listaUsuario.size

    }

    fun refreshListUsuario(listaAtualizada: List<Usuario>){
        listaUsuario = listaAtualizada
        notifyDataSetChanged()
    }

}