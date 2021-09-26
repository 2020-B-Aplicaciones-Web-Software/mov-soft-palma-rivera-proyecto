package com.example.proyecto2b

class Clinica(
    var nombre_clinica: String,
    var foto_logo:String,
    var direccion_clinica: String,
    var telefono_clinica:String,
    var web_clinica:String,
    var costo_consulta: Double,
    var novedades:String,
    var num_resenas:Int,
    var calificacion:Double
) {
    override fun toString(): String {
        return "Clinica(nombre_clinica='$nombre_clinica', foto_logo='$foto_logo', direccion_clinica='$direccion_clinica', telefono_clinica='$telefono_clinica', web_clinica='$web_clinica', costo_consulta=$costo_consulta, novedades='$novedades', num_resenas=$num_resenas, calificacion=$calificacion)"
    }
}