package com.victor.app16_listacompras

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class CompraAdapter(var listaCompra: List<Compra>, var listener: CompraAdapterListener):RecyclerView.Adapter<CompraAdapter.ItemViewHolder>() {

    // Responsável por encontrar os componentes dentro do layout (item_usuario) e indicar que o
    //  layout será replicado na Recycler View
    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view){
        val txtCompra: TextView = view.findViewById(R.id.txtCompra)
        val btnExcluir: ImageButton = view.findViewById(R.id.btnExcluir)
    }

    // Responsável por inflar (fazer aparecer) o layout por exemplo na activity ou fragment ou dialog (tudo que extends View Group)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return ItemViewHolder(view)
    }

    // Vincula os componentes do layout (item_usuario) a um dado/propriedade/atributo da lista
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.txtCompra.text = listaCompra[position].nome

        // Implementando o "click" do CardView para retornar um Toast como mensagem
        holder.btnExcluir.setOnClickListener(){
            listener.excluirCompra(listaCompra[position])
        }
    }

    // Indica quantos registros/itens a lista possui
    override fun getItemCount(): Int {
        return listaCompra.size
    }

    fun refreshListCompra(listaAtualizada: List<Compra>){
        listaCompra = listaAtualizada
        notifyDataSetChanged()
    }
}
