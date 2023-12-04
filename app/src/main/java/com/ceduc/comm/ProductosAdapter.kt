package com.ceduc.comm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosAdapter(private val productos: List<producto>) :
    RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtCodigo: TextView = itemView.findViewById(R.id.txtCodigo)
        val txtDescripcion: TextView = itemView.findViewById(R.id.txtDescripcion)
        val txtPrecio: TextView = itemView.findViewById(R.id.txtPrecio)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]

        holder.txtCodigo.text = producto.codigo
        holder.txtDescripcion.text = producto.descripcion
        holder.txtPrecio.text = producto.precio.toString()
    }

    override fun getItemCount(): Int {
        return productos.size
    }
}
