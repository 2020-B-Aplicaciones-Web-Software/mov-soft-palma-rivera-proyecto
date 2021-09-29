package com.example.proyecto2b

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ResumenClinicas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_clinicas)
        val rvClinica = findViewById<RecyclerView>(R.id.rv_lista_clinicas)
        val lista = ArrayList<Clinica>()
        val db = Firebase.firestore
        val ref = db.collection("clinica")
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombre_clinica = document.get("nombre_clinica").toString()
                    val telefono_clinia = document.get("telefono_clinica").toString()
                    val costo_consulta = document.get("costo_consulta").toString().toDouble()

                    val direccion = document.get("direccion_clinica").toString()
                    val foto_logo = document.get("foto_logo").toString()
                    val novedades = document.get("novedades").toString()
                    val reseniaEvaluacionMapa = document.get("resenias") as Map<*, *>
                    val reseniaEvaluacion = ReseniaEvaluacion(
                        reseniaEvaluacionMapa.get("num_5").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_4").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_3").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_2").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_1").toString().toInt(),
                        reseniaEvaluacionMapa.get("promedio").toString().toDouble(),
                        reseniaEvaluacionMapa.get("num_resenias").toString().toInt()
                    )
                    val web = document.get("web_clinica").toString()
                    val latitud = document.get("latitud").toString().toDouble()
                    val longitud = document.get("longitud").toString().toDouble()
                    val horariosAtencionMapa = document.get("horarios_atencion") as Map<*, *>
                    val horarios = HorariosAtencion(
                        horariosAtencionMapa.get("lunes").toString(),
                        horariosAtencionMapa.get("martes").toString(),
                        horariosAtencionMapa.get("miercoles").toString(),
                        horariosAtencionMapa.get("jueves").toString(),
                        horariosAtencionMapa.get("viernes").toString(),
                        horariosAtencionMapa.get("sabado").toString(),
                        horariosAtencionMapa.get("domingo").toString(),

                        )
                    lista.add(
                        Clinica(
                            nombre_clinica,
                            foto_logo,
                            direccion,
                            telefono_clinia,
                            web,
                            costo_consulta,
                            novedades,
                            latitud,
                            longitud,
                            reseniaEvaluacion, horarios, ArrayList<Servicio>()
                        )
                    )
                    Log.d("Storage", lista[0].toString())
                }
                iniciarRecyclerView(
                    lista,
                    this,
                    rvClinica
                )
            }
    }


    private fun iniciarRecyclerView(
        list: List<Clinica>,
        resumenClinicas: ResumenClinicas,
        rvClinica: RecyclerView?
    ) {
        val adapter = rvClinica?.let {
            ResumenClinicaAdapter(
                resumenClinicas, list,
                it,
                this
            )
        }
        if (rvClinica != null) {
            rvClinica.adapter = adapter
            rvClinica.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            rvClinica.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(resumenClinicas)
        }


    }


}