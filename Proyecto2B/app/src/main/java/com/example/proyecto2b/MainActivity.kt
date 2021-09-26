package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnRegistro=findViewById<Button>(R.id.button2)
        btnRegistro
            .setOnClickListener {
                startActivity(Intent(this,RegistroClinica::class.java))
            }
    }

}