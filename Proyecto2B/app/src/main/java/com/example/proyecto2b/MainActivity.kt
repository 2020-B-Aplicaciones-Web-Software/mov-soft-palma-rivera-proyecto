package com.example.proyecto2b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnRegistro=findViewById<Button>(R.id.button2)
        btnRegistro
            .setOnClickListener {
                startActivity(Intent(this,RegistroClinica::class.java))
            }
        val btn7=findViewById<Button>(R.id.button7)
        btn7
            .setOnClickListener {
                startActivity(Intent(this,ResumenClinicas::class.java))
            }

    }

}