package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MostrarResenias : AppCompatActivity() {
    var arregloClinicas1 = ArrayList<Clinica>()
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

    }

    override fun onResume() {
        super.onResume()
    }

}