package com.example.proyecto2b

import android.os.Parcel
import android.os.Parcelable

class Resenia (
    var nombre: String?,
    var calificacion: Int,
    var descripcion: String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeInt(calificacion)
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Resenia> {
        override fun createFromParcel(parcel: Parcel): Resenia {
            return Resenia(parcel)
        }

        override fun newArray(size: Int): Array<Resenia?> {
            return arrayOfNulls(size)
        }
    }
}