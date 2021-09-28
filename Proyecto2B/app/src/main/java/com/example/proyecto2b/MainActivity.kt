package com.example.proyecto2b

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnRegistro = findViewById<Button>(R.id.btn_registrar)
        btnRegistro
            .setOnClickListener {
                startActivity(Intent(this, RegistroClinica::class.java))
            }
        val btn_ver = findViewById<Button>(R.id.btn_ver)
        btn_ver
            .setOnClickListener {
                startActivity(Intent(this, DetalleClinica::class.java))
            }

        val btn_explorar = findViewById<Button>(R.id.btn_explorar)
        btn_explorar
            .setOnClickListener {
                startActivity(Intent(this, ResumenClinicas::class.java))
            }

    }

}