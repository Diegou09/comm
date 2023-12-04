package com.ceduc.comm

import android.widget.Toast
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class FormularioProductoActivity : AppCompatActivity() {

    private lateinit var producto: producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_producto)

        producto = intent.getParcelableExtra("producto") ?: producto()

        val btnAgregarAlCarrito: Button = findViewById(R.id.btnAgregarAlCarrito)

        mostrarProducto(producto)

        btnAgregarAlCarrito.setOnClickListener {
            agregarAlCarrito()
        }
    }

    companion object {
        const val EXTRA_PRODUCTO = "extra_producto"
    }

    private fun agregarAlCarrito() {
        val db = SQLiteDB(this)

        db.agregarProducto(producto, false)

        db.marcarProductoComoPagado(producto.id)

        Toast.makeText(this, "Producto agregado al carrito con éxito", Toast.LENGTH_SHORT).show()
        finish()
    }


    private fun mostrarProducto(producto: producto?) {
        if (producto != null) {
            findViewById<TextView>(R.id.etCodigo).text = producto.codigo
            findViewById<TextView>(R.id.etDescripcion).text = producto.descripcion
            findViewById<TextView>(R.id.etPrecio).text = producto.precio.toString()
        }
    }
}
