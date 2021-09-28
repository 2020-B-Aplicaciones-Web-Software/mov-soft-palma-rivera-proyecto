package com.example.proyecto2b

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.Toast

import android.widget.EditText

import android.widget.LinearLayout




class RegistroClinica : AppCompatActivity() {
    var uri = ""
    lateinit var auth: FirebaseAuth
    var servicios=ArrayList<Servicio>()
    // Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_clinica)
        auth = Firebase.auth
        //logeo anonimo
        auth.signInAnonymously()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        //switches para cada dia
        findViewById<Switch>(R.id.cerrado_lunes)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_lunes).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_lunes).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_martes)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_martes).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_martes).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_miercoles)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_miercoles).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_miercoles).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_jueves)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_jueves).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_jueves).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_viernes)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_viernes).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_viernes).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_sabado)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_sabado).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_sabado).isEnabled = !button.isChecked
            }

        findViewById<Switch>(R.id.cerrado_domingo)
            .setOnCheckedChangeListener { button, bool ->
                findViewById<EditText>(R.id.hora_apertura_domingo).isEnabled = !button.isChecked
                findViewById<EditText>(R.id.hora_cierre_domingo).isEnabled = !button.isChecked
            }

        val chipServicios = findViewById<ChipGroup>(R.id.chip_servicios)
        chipServicios
            .setOnClickListener {
                showdialog()
            }


        val btn = findViewById<Button>(R.id.btn_subir_foto)
        btn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        val btnRegistrar = findViewById<Button>(R.id.registrar)
        btnRegistrar.setOnClickListener {
            val horariosAtencion = getHorarios()
            val servicios = getServicios()
            val etiquetas = getEtiquetas()

            /* val storage = Firebase.storage
             val db = Firebase.firestore
             val storageRef = storage.reference

             // Create a reference to 'images/mountains.jpg'
             val mountainImagesRef =
                 storageRef.child("images/${findViewById<TextView>(R.id.nombre_clinica).text}.jpg")
             mountainImagesRef.putFile(uri.toUri())*/
        }
    }

    private fun getEtiquetas() {

    }

    private fun getServicios() {

    }

    fun showdialog(){
        val valores=ArrayList<String>()

        val builder: AlertDialog.Builder= AlertDialog.Builder(this)
        builder.setTitle("Registrar un servicio")


        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val nombreEditText = EditText(this)
        nombreEditText.hint="Ingrese el nombre del servicio"
        layout.addView(nombreEditText)

        val valorEditText = EditText(this)
        valorEditText.hint="Ingrese el valor del servicio"
        layout.addView(valorEditText)

        builder.setView(layout)

        builder.setPositiveButton(
            "Guardar"
        ) { dialog, whichButton ->
            valores.add(nombreEditText.text.toString())
            valores.add(valorEditText.text.toString())
            Toast.makeText(this, "Saved Sucessfully", Toast.LENGTH_LONG).show()
            servicios
                .add(
                    Servicio(
                        nombreEditText.text.toString(),
                        valorEditText.text.toString().toDouble()
                    )
                )

            Log.d("Registro", servicios[0].toString())
        }

        builder.setNegativeButton(
            "Cancel"
        ) { dialog, whichButton ->
            // what ever you want to do with No option.
        }

        builder.show()
    }

    private fun getHorarios(): HorariosAtencion {
        val switchLunes = findViewById<Switch>(R.id.cerrado_lunes)
        val switchMartes = findViewById<Switch>(R.id.cerrado_martes)
        val switchMiercoles = findViewById<Switch>(R.id.cerrado_miercoles)
        val switchJueves = findViewById<Switch>(R.id.cerrado_jueves)
        val switchViernes = findViewById<Switch>(R.id.cerrado_viernes)
        val switchSabado = findViewById<Switch>(R.id.cerrado_sabado)
        val switchDomingo = findViewById<Switch>(R.id.cerrado_domingo)
        var lunes: String
        var martes: String
        var miercoles: String
        var jueves: String
        var viernes: String
        var sabado: String
        var domingo: String

        if (switchLunes.isChecked) {
            lunes = "Cerrado"
        } else {
            lunes =
                findViewById<EditText>(R.id.hora_apertura_lunes).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_lunes).text.toString()
        }
        if (switchMartes.isChecked) {
            martes = "Cerrado"
        } else {
            martes =
                findViewById<EditText>(R.id.hora_apertura_martes).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_martes).text.toString()
        }
        if (switchMiercoles.isChecked) {
            miercoles = "Cerrado"
        } else {
            miercoles =
                findViewById<EditText>(R.id.hora_apertura_miercoles).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_miercoles).text.toString()
        }
        if (switchJueves.isChecked) {
            jueves = "Cerrado"
        } else {
            jueves =
                findViewById<EditText>(R.id.hora_apertura_jueves).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_jueves).text.toString()
        }
        if (switchViernes.isChecked) {
            viernes = "Cerrado"
        } else {
            viernes =
                findViewById<EditText>(R.id.hora_apertura_viernes).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_viernes).text.toString()
        }
        if (switchSabado.isChecked) {
            sabado = "Cerrado"
        } else {
            sabado =
                findViewById<EditText>(R.id.hora_apertura_sabado).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_sabado).text.toString()
        }
        if (switchDomingo.isChecked) {
            domingo = "Cerrado"
        } else {
            domingo =
                findViewById<EditText>(R.id.hora_apertura_domingo).text.toString() + " - " +
                        findViewById<EditText>(R.id.hora_cierre_domingo).text.toString()
        }
        return HorariosAtencion(
            lunes, martes, miercoles, jueves, viernes, sabado, domingo
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            uri = data.data.toString()
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.data)
            val drawable = BitmapDrawable(bitmap)
            findViewById<ImageView>(R.id.img_logo).setImageDrawable(drawable)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

}