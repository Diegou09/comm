package com.ceduc.comm

import android.os.Parcel
import android.os.Parcelable

data class producto(
    var id: Int = 0,
    var codigo: String = "",
    var descripcion: String = "",
    var precio: Double = 0.0,
    var cantidad: Int = 0
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(codigo)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeInt(cantidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<producto> {
        override fun createFromParcel(parcel: Parcel): producto {
            return producto(parcel)
        }

        override fun newArray(size: Int): Array<producto?> {
            return arrayOfNulls(size)
        }
    }
}
