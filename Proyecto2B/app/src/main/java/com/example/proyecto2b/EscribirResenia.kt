package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EscribirResenia : AppCompatActivity() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    var calificacion = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_escribir_resenia)

        val clinica = intent.getParcelableExtra<Clinica>("CLINICA")

        val botonCancelarResenia = findViewById<Button>(
            R.id.btn_cancelar
        )
        botonCancelarResenia.setOnClickListener {
            val intent = Intent(
                this,
                DetalleClinica::class.java
            )
            intent.putExtra("CLINICA",clinica)
            startActivityForResult(intent, CODIGO_RESPUESTA_INTENT_EXPLICITO)
        }

        val botonPublicarResenia = findViewById<Button>(
            R.id.btn_publicar
        )
        botonPublicarResenia.setOnClickListener {
            if (clinica != null) {
                crearResenia(clinica)
            }
        }

        val barra_estrellas = findViewById<RatingBar>(R.id.barra_estrellas)
        barra_estrellas.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            calificacion = rating.toDouble()
        }


    }

    fun crearResenia(clinica: Clinica) {

        val nombre_apellido = findViewById<TextInputEditText>(R.id.input_nombre).text
        val comentario = findViewById<EditText>(R.id.input_comentario).text
        val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
        if ((nombre_apellido.toString() == "") or(comentario.toString() == "")
            or (calificacion.toString() == "") ){
            toast.setText("Ingrese todos los campos")
            toast.show()
        } else{
            val nuevaResenia = hashMapOf<String, Any>(
                "nombre" to nombre_apellido.toString(),
                "calificacion" to calificacion.toInt(),
                "comentario" to comentario.toString()
            )

            val db = Firebase.firestore
            val referenciaResenia = db
                .collection("clinica")
                .whereEqualTo("nombre_clinica", clinica.nombre_clinica)
                .whereEqualTo("latitud", clinica.latitud)
                .whereEqualTo("longitud", clinica.longitud)

            referenciaResenia
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        val referencia_documento = db.collection("clinica").document(document.id)
                        referencia_documento.collection("resenias")
                            .add(nuevaResenia)
                            .addOnSuccessListener {
                                nombre_apellido?.clear()
                                comentario?.clear()
                                toast.setText("Resenia creada exitosamente")
                                toast.show()
                            }
                    }
                }

        }

    }
}