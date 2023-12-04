package com.ceduc.comm

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sqliteDB: SQLiteDB
    private lateinit var txtProd: TextView

    private fun obtenerProductos(): MutableList<producto> {
        return sqliteDB.obtenerProductos()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sqliteDB = SQLiteDB(this)

        val button1: ImageButton = findViewById(R.id.button1)
        val button2: ImageButton = findViewById(R.id.button2)
        val button3: ImageButton = findViewById(R.id.button3)
        val button4: ImageButton = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        txtProd = findViewById(R.id.txtProd)

        button1.setOnClickListener {
            abrirProducto(1, "D841", "Dron Super Épico", 519990.0, 2)
        }

        button2.setOnClickListener {
            abrirProducto(2, "M932", "Macbook Pro", 1299999.0, 3)
        }

        button3.setOnClickListener {
            abrirProducto(3, "AU456", "Audífonos Pro Gamer RGB", 29990.0, 4)
        }

        button4.setOnClickListener {
            abrirProducto(4, "V374", "Visor VR Playstation 5 Anashe", 32999.0, 1)
        }

        button5.setOnClickListener {
            abrirCarrito()
        }

        button6.setOnClickListener {
            mostrarProductos()
        }
    }

    private fun abrirProducto(id: Int, codigo: String, descripcion: String, precio: Double, cantidad: Int) {
        val producto = producto(id, codigo, descripcion, precio, cantidad)
        val intent = Intent(this, FormularioProductoActivity::class.java)
        intent.putExtra(FormularioProductoActivity.EXTRA_PRODUCTO, producto)
        startActivity(intent)
    }

    private fun abrirCarrito() {
        try {
            val intent = Intent(this, CarritoActivity::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mostrarProductos() {
        val listaProductos = obtenerProductos()
        val listaProductosTexto = listaProductos.joinToString("\n") {
            "${it.descripcion} - ${it.precio}"
        }
        txtProd.text = listaProductosTexto
    }
}
