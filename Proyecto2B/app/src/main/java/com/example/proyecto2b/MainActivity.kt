package com.example.proyecto2b

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.android.gms.maps.model.Marker




class MainActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    val hash: HashMap<Marker, Clinica> = HashMap()
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    private lateinit var clinicaSeleccionada: Clinica
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lista = ArrayList<Clinica>()
        val db = Firebase.firestore
        val ref = db.collection("clinica")
        ref.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val nombre_clinica = document.get("nombre_clinica").toString()
                    val telefono_clinia = document.get("telefono_clinica").toString()
                    val costo_consulta = document.get("costo_consulta").toString().toDouble()

                    val direccion = document.get("direccion_clinica").toString()
                    val foto_logo = document.get("foto_logo").toString()
                    val novedades = document.get("novedades").toString()
                    val reseniaEvaluacionMapa = document.get("resenias") as Map<*, *>
                    val reseniaEvaluacion = ReseniaEvaluacion(
                        reseniaEvaluacionMapa.get("num_5").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_4").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_3").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_2").toString().toInt(),
                        reseniaEvaluacionMapa.get("num_1").toString().toInt(),
                        reseniaEvaluacionMapa.get("promedio").toString().toDouble(),
                        reseniaEvaluacionMapa.get("num_resenias").toString().toInt()
                    )
                    val web = document.get("web_clinica").toString()
                    val latitud = document.get("latitud").toString().toDouble()
                    val longitud = document.get("longitud").toString().toDouble()
                    val horariosAtencionMapa = document.get("horarios_atencion") as Map<*, *>
                    val horarios = HorariosAtencion(
                        horariosAtencionMapa.get("lunes").toString(),
                        horariosAtencionMapa.get("martes").toString(),
                        horariosAtencionMapa.get("miercoles").toString(),
                        horariosAtencionMapa.get("jueves").toString(),
                        horariosAtencionMapa.get("viernes").toString(),
                        horariosAtencionMapa.get("sabado").toString(),
                        horariosAtencionMapa.get("domingo").toString(),
                        )
                    lista.add(
                        Clinica(
                            nombre_clinica,
                            foto_logo,
                            direccion,
                            telefono_clinia,
                            web,
                            costo_consulta,
                            novedades,
                            latitud,
                            longitud,
                            reseniaEvaluacion, horarios, ArrayList<Servicio>()
                        )
                    )
                }
                
                cargarMapa(lista)
            }

        
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

    private fun cargarMapa(lista: ArrayList<Clinica>) {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            mapa = googleMap
            for (clinica in lista){
                anadirMarcador(clinica)
                escucharListeners()
            }

        }


    }

    private fun escucharListeners() {
        mapa
            .setOnMarkerClickListener {
                val card=findViewById<CardView>(R.id.card_clinica)
                val textoCard=findViewById<TextView>(R.id.nombre_clinica_mapa)
                val clinica: Clinica? =hash[it]
                if (clinica != null) {
                    textoCard.text= clinica.nombre_clinica
                }
                card.visibility = View.VISIBLE
                val btn_ver=findViewById<Button>(R.id.btn_ver)
                btn_ver
                    .setOnClickListener {
                        val detalleClinica = Intent(
                            this,
                            DetalleClinica::class.java
                        )
                        detalleClinica.putExtra("CLINICA", clinica)
                        startActivityForResult(detalleClinica,CODIGO_RESPUESTA_INTENT_EXPLICITO)
                    }

                return@setOnMarkerClickListener true
            }

    }

    private fun anadirMarcador(clinica:Clinica) {
        val marker=mapa.addMarker(
            MarkerOptions()
            .position(LatLng(clinica.latitud, clinica.longitud))
            .title(clinica.nombre_clinica)
        )
        hash.put(marker,clinica)


    }

    fun moverCamara(LatLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(LatLng, zoom)
        )
    }

    private fun solicitarPermisos() {
        val contexto = this.applicationContext
        val permisoFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisoFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }

    private fun configurarMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val permisoFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisoFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isMyLocationButtonEnabled = true
        }
    }


}