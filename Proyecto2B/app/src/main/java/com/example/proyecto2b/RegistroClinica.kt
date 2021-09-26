package com.example.proyecto2b

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RegistroClinica : AppCompatActivity() {
    var uri = ""
    lateinit var auth: FirebaseAuth
    // Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_clinica)
        auth = Firebase.auth
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }


        val btn = findViewById<Button>(R.id.button3)
        btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnSubir = findViewById<Button>(R.id.button4)
        btnSubir.setOnClickListener {
            val storage = Firebase.storage
            var storageRef = storage.reference
            val mountainsRef = storageRef.child("mountains.jpg")

            // Create a reference to 'images/mountains.jpg'
            val mountainImagesRef = storageRef.child("images/mountains.jpg")
            mountainImagesRef.putFile(uri.toUri())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data.toString()
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
            val drawable = BitmapDrawable(bitmap)
            findViewById<ImageView>(R.id.imageView).setImageDrawable(drawable)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

}