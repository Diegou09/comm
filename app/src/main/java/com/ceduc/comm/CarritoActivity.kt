package com.ceduc.comm

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CarritoActivity : AppCompatActivity() {

    private lateinit var listaProductos: MutableList<producto>
    private val db: SQLiteDB by lazy { SQLiteDB(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        listaProductos = db.obtenerProductos()

        mostrarProductosEnCarrito()

        val btnPagar: Button = findViewById(R.id.btnPagar)

        btnPagar.setOnClickListener {
            pagarProductos()
        }
    }

    private fun mostrarProductosEnCarrito() {
        try {
            val listaProductosTexto = listaProductos.joinToString("\n") {
                "${it.descripcion} - ${it.precio}"
            }
            val txtListaProductos: TextView = findViewById(R.id.txtListaProductos)
            txtListaProductos.text = listaProductosTexto

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(
                this,
                "Error al mostrar productos en el carrito: ${e.message}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun pagarProductos() {
        try {
            Toast.makeText(this, "¡Se ha pagado con éxito!", Toast.LENGTH_SHORT).show()

            for (producto in listaProductos) {
                db.marcarProductoComoPagado(producto.id)
            }

            limpiarCarrito()

            finish()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error al realizar el pago: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun limpiarCarrito() {
        db.limpiarCarrito()
        listaProductos.clear()

        val txtListaProductos: TextView = findViewById(R.id.txtListaProductos)
        txtListaProductos.text = ""
    }
}
