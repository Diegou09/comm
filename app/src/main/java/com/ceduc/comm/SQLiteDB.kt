package com.ceduc.comm



import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class SQLiteDB(context: Context) : SQLiteOpenHelper(context, "productos", null, 2) {

    companion object {
        const val TABLE_NAME = "productos"
        const val COLUMN_ID = "id"
        const val COLUMN_CODIGO = "codigo"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_PRECIO = "precio"
        const val COLUMN_PAGADO = "pagado"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("SQLiteDB", "onCreate called")
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CODIGO TEXT, $COLUMN_DESCRIPCION TEXT, $COLUMN_PRECIO REAL, $COLUMN_PAGADO INTEGER DEFAULT 0)"
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("SQLiteDB", "onUpgrade called")
        if (oldVersion < 3) {
            db?.execSQL("ALTER TABLE $TABLE_NAME ADD COLUMN $COLUMN_PAGADO INTEGER DEFAULT 0")
            }
    }

    fun agregarDatos(codigo: String, descripcion: String, precio: Double) {
        writableDatabase.use { db ->
            val values = ContentValues()
            values.put(COLUMN_CODIGO, codigo)
            values.put(COLUMN_DESCRIPCION, descripcion)
            values.put(COLUMN_PRECIO, precio)
            db.insert(TABLE_NAME, null, values)
        }
    }


    fun borrarDatos(id: Long) {
        val db = writableDatabase
        "$COLUMN_ID = ?"
        arrayOf(id.toString())

        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
    }

    fun actualizarDatos(id: Long, nuevoCodigo: String, nuevaDescripcion: String, nuevoPrecio: Double) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CODIGO, nuevoCodigo)
        values.put(COLUMN_DESCRIPCION, nuevaDescripcion)
        values.put(COLUMN_PRECIO, nuevoPrecio)

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun agregarProducto(producto: producto, pagado: Boolean) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_CODIGO, producto.codigo)
        values.put(COLUMN_DESCRIPCION, producto.descripcion)
        values.put(COLUMN_PRECIO, producto.precio)
        values.put(COLUMN_PAGADO, if (pagado) 1 else 0)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun obtenerProductos(): MutableList<producto> {
            val productList = mutableListOf<producto>()
            val db = this.readableDatabase
            val selectQuery = "SELECT $COLUMN_CODIGO, $COLUMN_DESCRIPCION, $COLUMN_PRECIO FROM $TABLE_NAME WHERE $COLUMN_PAGADO = 1"

        val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val codigoIndex = cursor.getColumnIndex(COLUMN_CODIGO)
                    val descripcionIndex = cursor.getColumnIndex(COLUMN_DESCRIPCION)
                    val precioIndex = cursor.getColumnIndex(COLUMN_PRECIO)

                    if (codigoIndex != -1 && descripcionIndex != -1 && precioIndex != -1) {
                        val codigo = cursor.getString(codigoIndex)
                        val descripcion = cursor.getString(descripcionIndex)
                        val precio = cursor.getDouble(precioIndex)
                        val producto = producto(0, codigo, descripcion, precio, 0)
                        productList.add(producto)
                    }

                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()
            return productList.distinctBy { it.codigo }.toMutableList()
    }


    fun limpiarCarrito() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }

    fun marcarProductoComoPagado(idProducto: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PAGADO, 1)
        }

        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(idProducto.toString())

        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }


}
