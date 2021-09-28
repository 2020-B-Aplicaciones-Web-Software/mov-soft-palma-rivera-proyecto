package com.example.proyecto2b

import android.os.Parcel
import android.os.Parcelable

class HorariosAtencion (
    var lunes: String?,
    var martes: String?,
    var miercoles: String?,
    var jueves: String?,
    var viernes: String?,
    var sabado: String?,
    var domingo: String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(lunes)
        parcel.writeString(martes)
        parcel.writeString(miercoles)
        parcel.writeString(jueves)
        parcel.writeString(viernes)
        parcel.writeString(sabado)
        parcel.writeString(domingo)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "HorariosAtencion(lunes=$lunes, martes=$martes, miercoles=$miercoles, jueves=$jueves, viernes=$viernes, sabado=$sabado, domingo=$domingo)"
    }

    companion object CREATOR : Parcelable.Creator<HorariosAtencion> {
        override fun createFromParcel(parcel: Parcel): HorariosAtencion {
            return HorariosAtencion(parcel)
        }

        override fun newArray(size: Int): Array<HorariosAtencion?> {
            return arrayOfNulls(size)
        }
    }
}