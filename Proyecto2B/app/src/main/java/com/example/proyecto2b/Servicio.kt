package com.example.proyecto2b

import android.os.Parcel
import android.os.Parcelable

class Servicio (
    var nombre_servicio: String?,
    var costo_servicio: Double
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre_servicio)
        parcel.writeDouble(costo_servicio)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$nombre_servicio: $$costo_servicio"
    }

    companion object CREATOR : Parcelable.Creator<Servicio> {
        override fun createFromParcel(parcel: Parcel): Servicio {
            return Servicio(parcel)
        }

        override fun newArray(size: Int): Array<Servicio?> {
            return arrayOfNulls(size)
        }
    }

}