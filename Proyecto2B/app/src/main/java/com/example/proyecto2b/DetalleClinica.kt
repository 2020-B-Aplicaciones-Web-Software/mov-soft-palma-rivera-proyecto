package com.example.proyecto2b

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextClock
import android.widget.TextView
import java.util.*

class DetalleClinica : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val clinica = intent.getParcelableExtra<Clinica>("Clinica")
        Log.d("DetalleClinica", clinica.toString())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_clinica)
        val barra5=findViewById<ProgressBar>(R.id.barra5)
        val barra4=findViewById<ProgressBar>(R.id.barra4)
        val barra3=findViewById<ProgressBar>(R.id.barra3)
        val barra2=findViewById<ProgressBar>(R.id.barra2)
        val barra1=findViewById<ProgressBar>(R.id.barra1)
        val tx=findViewById<TextView>(R.id.textView9)
        if (clinica != null) {
            tx.text=clinica.nombre_clinica
        }
        else{
            tx.text="Triste :c"
        }
        barra5.progress=90
        barra4.progress=54
        barra3.progress=21
        barra2.progress=50
        barra1.progress=10

    }
}