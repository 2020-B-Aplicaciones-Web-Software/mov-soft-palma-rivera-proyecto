package com.example.proyecto2b

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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

        val barra5 = findViewById<ProgressBar>(R.id.barra5)
        val barra4 = findViewById<ProgressBar>(R.id.barra4)
        val barra3 = findViewById<ProgressBar>(R.id.barra3)
        val barra2 = findViewById<ProgressBar>(R.id.barra2)
        val barra1 = findViewById<ProgressBar>(R.id.barra1)
        val num_resenias=findViewById<TextView>(R.id.num_resenias)
        val promedio=findViewById<TextView>(R.id.promedio_resenia)
        val calificacion=findViewById<RatingBar>(R.id.ratingBar)

        if (clinica != null) {
            num_resenias.text= clinica.resenias!!.num_resenias.toString()
            calificacion.rating= clinica.resenias!!.promedio.toFloat()
            promedio.text=clinica.resenias!!.promedio.toString()
            barra5.progress =
                (clinica.resenias?.num_5?.toDouble()?.div(clinica.resenias!!.num_resenias)?.times(100))?.toInt()!!
            barra4.progress =
                (clinica.resenias?.num_4?.toDouble()?.div(clinica.resenias!!.num_resenias)?.times(100))?.toInt()!!
            barra3.progress =
                (clinica.resenias?.num_3?.toDouble()?.div(clinica.resenias!!.num_resenias)?.times(100))?.toInt()!!
            barra2.progress =
                (clinica.resenias?.num_2?.toDouble()?.div(clinica.resenias!!.num_resenias)?.times(100))?.toInt()!!
            barra1.progress =
                (clinica.resenias?.num_1?.toDouble()?.div(clinica.resenias!!.num_resenias)?.times(100))?.toInt()!!
        }


        val botonEscribirResenia = findViewById<Button>(R.id.btn_escribir_resenia)
        botonEscribirResenia.setOnClickListener {
            val intent = Intent(
                this,
                EscribirResenia::class.java
            )
            intent.putExtra("CLINICA", clinica)
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
                for (document in result) {
                    val referencia_documento = db.collection("clinica").document(document.id)
                    referencia_documento.collection("resenias")
                        .get()
                        .addOnSuccessListener { resultado ->
                            for (documento in resultado) {
                                val nombre_apellido = "${documento.data.get("nombre")}"
                                val calificacion = "${documento.data.get("calificacion")}"
                                val comentario = "${documento.data.get("comentario")}"
                                arreglo_resenias.add(
                                    Resenia(
                                        nombre_apellido,
                                        calificacion.toInt(),
                                        comentario
                                    )
                                )
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
                            recyclerViewResenias.itemAnimator =
                                androidx.recyclerview.widget.DefaultItemAnimator()
                            recyclerViewResenias.layoutManager =
                                androidx.recyclerview.widget.LinearLayoutManager(this)
                            adaptador.notifyDataSetChanged()

                        }

                }

            }


    }


}