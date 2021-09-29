package com.example.proyecto2b

import android.os.Parcel
import android.os.Parcelable

class ReseniaEvaluacion (
    var num_5: Int,
    var num_4: Int,
    var num_3: Int,
    var num_2: Int,
    var num_1: Int,
    var promedio: Double,
    var num_resenias: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(num_5)
        parcel.writeInt(num_4)
        parcel.writeInt(num_3)
        parcel.writeInt(num_2)
        parcel.writeInt(num_1)
        parcel.writeDouble(promedio)
        parcel.writeInt(num_resenias)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ReseniaEvaluacion> {
        override fun createFromParcel(parcel: Parcel): ReseniaEvaluacion {
            return ReseniaEvaluacion(parcel)
        }

        override fun newArray(size: Int): Array<ReseniaEvaluacion?> {
            return arrayOfNulls(size)
        }
    }
}