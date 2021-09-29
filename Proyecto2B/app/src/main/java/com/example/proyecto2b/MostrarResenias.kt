package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MostrarResenias : AppCompatActivity() {

    var arregloResenias1 = ArrayList<Resenia>()
    var id_seleccionado = -1
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar_resenias)

        val clinica = intent.getParcelableExtra<Clinica>("CLINICA")

        val botonEscribirResenia = findViewById<Button>(R.id.btn_escribir_resenia)
        botonEscribirResenia.setOnClickListener {
            val intent = Intent(
                this,
                EscribirResenia::class.java
            )
            intent.putExtra("CLINICA",clinica)
            startActivityForResult(intent, CODIGO_RESPUESTA_INTENT_EXPLICITO)
        }

        val db = Firebase.firestore
        val referenciaResenia = db
            .collection("clinica")
            .whereEqualTo("nombre_clinica", clinica?.nombre_clinica)
            .whereEqualTo("latitud", clinica?.latitud)
            .whereEqualTo("longitud", clinica?.longitud)

        val arreglo_resenias = ArrayList<Resenia>()
        referenciaResenia
            .get()
            .addOnSuccessListener { result ->
                for (document in result){
                    val referencia_documento = db.collection("clinica").document(document.id)
                    referencia_documento.collection("resenias")
                        .get()
                        .addOnSuccessListener { resultado ->
                            for (documento in resultado){
                                val nombre_apellido = "${documento.data.get("nombre")}"
                                val calificacion = "${documento.data.get("calificacion")}"
                                val comentario = "${documento.data.get("comentario")}"
                                arreglo_resenias.add(Resenia(nombre_apellido,calificacion.toInt(),comentario))
                            }

                            arregloResenias1 = arreglo_resenias

                            val recyclerViewResenias = findViewById<RecyclerView>(
                                R.id.rv_resenias
                            )
                            val adaptador = ResumenReseniasAdapter(
                                this,
                                arregloResenias1,
                                recyclerViewResenias
                            )
                            recyclerViewResenias.adapter = adaptador
                            recyclerViewResenias.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
                            recyclerViewResenias.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
                            adaptador.notifyDataSetChanged()

                        }

                }

            }


    }











}