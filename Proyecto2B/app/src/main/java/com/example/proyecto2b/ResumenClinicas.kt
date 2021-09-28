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
                    val nombre_clinica = document.get("nombre").toString()
                    val telefono_clinia = document.get("telefono").toString()
                    val calificacion = document.get("calificacion").toString().toDouble()
                    val costo_consulta = document.get("costo_consulta").toString().toDouble()
                    val direccion = document.get("direccion").toString()
                    val foto_logo = document.get("foto_logo").toString()
                    val novedades = document.get("novedades").toString()
                    val num_resenas = document.get("num_resenas").toString().toInt()
                    val web = document.get("web").toString()
                    lista.add(
                        Clinica(
                            nombre_clinica,
                            foto_logo,
                            direccion,
                            telefono_clinia,
                            web,
                            costo_consulta,
                            novedades,
                            0.001,
                            0.002,
                            ReseniaEvaluacion(
                                0,
                                0,
                                0,
                                0,
                                0,
                                1,
                                3
                            ), HorariosAtencion(
                                "Cerrado",
                                "Cerrado",
                                "Cerrado",
                                "Cerrado",
                                "Cerrado",
                                "Cerrado",
                                "Cerrado",
                            ),ArrayList<Servicio>()
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
/*
        lista.add(
            Clinica("La casa del perro","/","Cerquita","0999999","www.facebook.com",10.00,"Nada",10)
        )

 */


    private fun iniciarRecyclerView(
        list: List<Clinica>,
        resumenClinicas: ResumenClinicas,
        rvClinica: RecyclerView?
    ) {
        val adapter = rvClinica?.let {
            ResumenClinicaAdapter(
                resumenClinicas::class.java, list,
                it
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