package com.example.proyecto2b

import android.os.Parcel
import android.os.Parcelable

class Clinica(
    var nombre_clinica: String?,
    var foto_logo: String?,
    var direccion_clinica: String?,
    var telefono_clinica: String?,
    var web_clinica: String?,
    var costo_consulta: Double,
    var novedades: String?,
    var latitud: Double,
    var longitud: Double,
    var resenias: ReseniaEvaluacion?,
    var horarios_atencion: HorariosAtencion?,
    var servicios: ArrayList<Servicio>?

): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(ReseniaEvaluacion::class.java.classLoader),
        parcel.readParcelable(HorariosAtencion::class.java.classLoader),
        arrayListOf<Servicio>().apply {
            parcel.readList(this,Servicio::class.java.classLoader)
        }
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre_clinica)
        parcel.writeString(foto_logo)
        parcel.writeString(direccion_clinica)
        parcel.writeString(telefono_clinica)
        parcel.writeString(web_clinica)
        parcel.writeDouble(costo_consulta)
        parcel.writeString(novedades)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
        parcel.writeParcelable(resenias, flags)
        parcel.writeParcelable(horarios_atencion, flags)
        parcel.writeList(servicios)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clinica> {
        override fun createFromParcel(parcel: Parcel): Clinica {
            return Clinica(parcel)
        }

        override fun newArray(size: Int): Array<Clinica?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Nombre clinica: ${nombre_clinica} \n" +
                "Direccion: ${direccion_clinica}\n" +
                "Telefono: ${telefono_clinica}\n" +
                "Costo consulta: ${costo_consulta}\n" +
                "Novedades: ${novedades}"
    }


}