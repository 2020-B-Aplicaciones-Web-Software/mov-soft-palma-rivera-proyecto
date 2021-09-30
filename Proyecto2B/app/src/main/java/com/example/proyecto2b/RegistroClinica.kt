package com.example.proyecto2b

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage


class RegistroClinica : AppCompatActivity() {
    var uri = ""
    lateinit var auth: FirebaseAuth
    var servicios = ArrayList<Servicio>()
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

            val nombre_clinica = findViewById<EditText>(R.id.nombre_clinica).text.toString()
            val foto_logo = "${findViewById<TextView>(R.id.nombre_clinica).text}.jpg"
            val direccion_clinica = findViewById<EditText>(R.id.direccion_clinica).text.toString()
            val telefono_clinica = findViewById<EditText>(R.id.telefono_clinica).text.toString()
            val web_clinica = findViewById<EditText>(R.id.sitio_web).text.toString()
            val costo_consulta = findViewById<EditText>(R.id.tarifa_base).text.toString()
            val novedades = findViewById<EditText>(R.id.novedades).text.toString()
            val latitud = findViewById<EditText>(R.id.latitud).text.toString().toDouble()
            val longitud = findViewById<EditText>(R.id.longitud).text.toString().toDouble()
            val resenias = ReseniaEvaluacion(5, 5, 3, 0, 0, 4.1, 13)
            val horarios_atencion = getHorarios()


            val horarios = hashMapOf<String, Any?>(
                "lunes" to horarios_atencion.lunes,
                "martes" to horarios_atencion.martes,
                "miercoles" to horarios_atencion.miercoles,
                "jueves" to horarios_atencion.jueves,
                "viernes" to horarios_atencion.viernes,
                "sabado" to horarios_atencion.sabado,
                "domingo" to horarios_atencion.domingo
            )

            val reseniasEvaluacion = hashMapOf<String, Any>(
                "num_5" to resenias.num_5,
                "num_4" to resenias.num_4,
                "num_3" to resenias.num_3,
                "num_2" to resenias.num_2,
                "num_1" to resenias.num_1,
                "promedio" to resenias.promedio,
                "num_resenias" to resenias.num_resenias
            )

            val nuevaClinica = hashMapOf<String, Any>(
                "nombre_clinica" to nombre_clinica,
                "foto_logo" to foto_logo,
                "direccion_clinica" to direccion_clinica,
                "telefono_clinica" to telefono_clinica,
                "web_clinica" to web_clinica,
                "costo_consulta" to costo_consulta,
                "novedades" to novedades,
                "latitud" to latitud,
                "longitud" to longitud,
                "resenias" to reseniasEvaluacion,
                "horarios_atencion" to horarios
            )



            val storage = Firebase.storage
            val db = Firebase.firestore
            val referencia = db.collection("clinica")

            referencia
                .add(nuevaClinica)
                .addOnSuccessListener {
                    val toast = Toast.makeText(applicationContext, "", Toast.LENGTH_SHORT)
                    toast.setText("Clínica Registrada")
                    toast.show()
                }

            referencia
                .whereEqualTo("nombre_clinica", nombre_clinica)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val referencia_documento = db.collection("clinica").document(document.id)
                        for (servicio in servicios){
                            referencia_documento.collection("servicios")
                                .add(servicio)
                                .addOnSuccessListener {  }
                        }

                    }
                }



            val storageRef = storage.reference

            // Create a reference to 'images/mountains.jpg'
            val mountainImagesRef =
                storageRef.child("images/${foto_logo}")
            mountainImagesRef.putFile(uri.toUri())
        }
    }


    fun showdialog() {
        val valores = ArrayList<String>()

        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Registrar un servicio")


        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val nombreEditText = EditText(this)
        nombreEditText.hint = "Ingrese el nombre del servicio"
        layout.addView(nombreEditText)

        val valorEditText = EditText(this)
        valorEditText.hint = "Ingrese el valor del servicio"
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
            updateChips()
        }

        builder.setNegativeButton(
            "Cancelar"
        ) { dialog, whichButton ->
            // what ever you want to do with No option.
        }

        builder.show()
    }

    private fun updateChips() {
        val chipServicios = findViewById<ChipGroup>(R.id.chip_servicios)
        val chip = Chip(this)
        chip.text = servicios[servicios.size - 1].toString()
        chipServicios.addView(chip)
    }

    private fun getHorarios(): HorariosAtencion {
        val switchLunes = findViewById<Switch>(R.id.cerrado_lunes)
        val switchMartes = findViewById<Switch>(R.id.cerrado_martes)
        val switchMiercoles = findViewById<Switch>(R.id.cerrado_miercoles)
        val switchJueves = findViewById<Switch>(R.id.cerrado_jueves)
        val switchViernes = findViewById<Switch>(R.id.cerrado_viernes)
        val switchSabado = findViewById<Switch>(R.id.cerrado_sabado)
        val switchDomingo = findViewById<Switch>(R.id.cerrado_domingo)
        val lunes: String
        val martes: String
        val miercoles: String
        val jueves: String
        val viernes: String
        val sabado: String
        val domingo: String

        lunes = if (switchLunes.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_lunes).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_lunes).text.toString()
        }
        martes = if (switchMartes.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_martes).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_martes).text.toString()
        }
        miercoles = if (switchMiercoles.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_miercoles).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_miercoles).text.toString()
        }
        jueves = if (switchJueves.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_jueves).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_jueves).text.toString()
        }
        viernes = if (switchViernes.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_viernes).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_viernes).text.toString()
        }
        sabado = if (switchSabado.isChecked) {
            "Cerrado"
        } else {
            findViewById<EditText>(R.id.hora_apertura_sabado).text.toString() + " - " +
                    findViewById<EditText>(R.id.hora_cierre_sabado).text.toString()
        }
        domingo = if (switchDomingo.isChecked) {
            "Cerrado"
        } else {
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