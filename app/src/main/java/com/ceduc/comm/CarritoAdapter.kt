package com.ceduc.comm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private val items: List<ItemCarrito>) : RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCodigo: TextView = itemView.findViewById(R.id.txtCodigo)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_carrito, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.txtCodigo.text = "Código: ${item.codigo}"
        holder.txtDescripcion.text = "Descripción: ${item.descripcion}"
        holder.txtPrecio.text = "Precio: ${item.precio}"
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
