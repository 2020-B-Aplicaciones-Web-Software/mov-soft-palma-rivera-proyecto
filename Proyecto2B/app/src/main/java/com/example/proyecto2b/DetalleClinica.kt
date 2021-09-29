package com.example.proyecto2b


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class DetalleClinica : AppCompatActivity() {
    var arregloResenias1 = ArrayList<Resenia>()
    var id_seleccionado = -1
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_clinica)

        val clinica = intent.getParcelableExtra<Clinica>("CLINICA")

        val botonVerResenias = findViewById<Button>(R.id.btn_mostrar_resenias)
        botonVerResenias.setOnClickListener {
            val intent = Intent(
                this,
                MostrarResenias::class.java
            )
            intent.putExtra("CLINICA",clinica)
            startActivityForResult(intent, CODIGO_RESPUESTA_INTENT_EXPLICITO)
        }

        if (clinica != null) {
            val storage = Firebase.storage
            val sr = storage.reference
            val path = clinica.foto_logo?.let { sr.child("images/$it") }

            val file = File.createTempFile("profile", "jpg")
            path?.getFile(file)?.addOnSuccessListener {

                val bitmap = Drawable.createFromPath(file.toString())
                findViewById<ImageView>(R.id.foto_detalle_clinica).setImageDrawable(bitmap)
            }
            findViewById<TextView>(R.id.tv_direccion).setText("Direccion: " + clinica.direccion_clinica)
            findViewById<TextView>(R.id.tv_telefono).setText("Teléfono: " + clinica.telefono_clinica)
            findViewById<TextView>(R.id.tv_costo_consulta).setText("Costo de la consulta: $" + clinica.costo_consulta.toString())
            findViewById<TextView>(R.id.tv_lunes).setText("Lunes      "+ clinica.horarios_atencion?.lunes)
            findViewById<TextView>(R.id.tv_martes).setText("Martes     "+clinica.horarios_atencion?.martes)
            findViewById<TextView>(R.id.tv_miercoles).setText("Miercoles  "+clinica.horarios_atencion?.miercoles)
            findViewById<TextView>(R.id.tv_jueves).setText("Jueves     "+clinica.horarios_atencion?.jueves)
            findViewById<TextView>(R.id.tv_viernes).setText("Viernes    "+clinica.horarios_atencion?.viernes)
            findViewById<TextView>(R.id.tv_sabado).setText("Sabado     "+clinica.horarios_atencion?.sabado)
            findViewById<TextView>(R.id.tv_domingo).setText("Domingo    "+clinica.horarios_atencion?.domingo)
            findViewById<TextView>(R.id.tv_novedades).setText(clinica.novedades)
            findViewById<RatingBar>(R.id.rating_detalle).rating= clinica.resenias?.promedio?.toFloat()!!
            findViewById<TextView>(R.id.promedio_detalle).text= clinica.resenias?.promedio.toString()
            findViewById<TextView>(R.id.num_resenias_detalle).text=clinica.resenias?.num_resenias.toString()+" reseña(s)"

            findViewById<ImageView>(R.id.llamar_detalle)
                .setOnClickListener {
                    clinica.telefono_clinica?.let { it1 -> call(it1) }
                }
            findViewById<ImageView>(R.id.web_detalle)
                .setOnClickListener {
                    clinica.web_clinica?.let { it1 -> irWeb(it1) }
                }
        }




        var servicios = ArrayList<Servicio>()
        if (clinica != null) {
            servicios = clinica.servicios!!
        }
        //Creacion del adaptador
        val adaptador = ArrayAdapter(
            this, //contexto
            android.R.layout.simple_list_item_1, //Se define el Layout
            servicios
        )
        //Se asigna el adaptador de la lista
        val listViewClinicas = findViewById<ListView>(R.id.lv_servicios)
        registerForContextMenu(listViewClinicas)
        listViewClinicas.adapter = adaptador

    }

    fun irWeb(web: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://${web}"))
        startActivity(browserIntent)
    }

    fun call(numero: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$numero")
        ContextCompat.startActivity(this, intent, null)
    }
}