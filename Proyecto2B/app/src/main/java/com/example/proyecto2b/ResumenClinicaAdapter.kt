package com.example.proyecto2b

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File
import java.util.*

class ResumenClinicaAdapter(
    private val context: Context,
    private val listaClinicas: List<Clinica>,
    private val recyclerView: RecyclerView,
    private val activity: AppCompatActivity,
) : RecyclerView.Adapter<ResumenClinicaAdapter.MyViewHolder>() {
    val CODIGO_RESPUESTA_INTENT_EXPLICITO = 401
    lateinit var clinica: Clinica

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreClinica = view.findViewById<TextView>(R.id.nombre_clinica_resumen)
        val direccionClinica = view.findViewById<TextView>(R.id.direccion_clinica_resumen)
        val telefonoClinica = view.findViewById<TextView>(R.id.telefono_clinica_resumen)
        val horarioClinica = view.findViewById<TextView>(R.id.horario_clinica_resumen)
        val calificacionClinica = view.findViewById<RatingBar>(R.id.rating_resumen)
        val numResenas = view.findViewById<TextView>(R.id.num_resena_resumen)
        val foto = view.findViewById<ImageView>(R.id.foto_clinica_resumen)
        val irDetalle = view.findViewById<Button>(R.id.btn_mas_info_clinica)

        init {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_resumen_clinica,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storage = Firebase.storage
        val sr = storage.reference

        val clinicaResumen = listaClinicas[position]
        val path = clinicaResumen.foto_logo?.let { sr.child("images/$it") }

        val file = File.createTempFile("profile", "jpg")
        path?.getFile(file)?.addOnSuccessListener {

            val bitmap = Drawable.createFromPath(file.toString())
            holder.foto.setImageDrawable(bitmap)
        }
        holder.nombreClinica.text = clinicaResumen.nombre_clinica
        holder.direccionClinica.text = "Dirección: ${clinicaResumen.direccion_clinica}"
        holder.telefonoClinica.text = "Teléfono: ${clinicaResumen.telefono_clinica}"
        holder.horarioClinica.text = getHorarioDelDia(clinicaResumen)
        holder.calificacionClinica.numStars = 4
        holder.numResenas.text = clinicaResumen.resenias?.num_resenias.toString()
        holder.irDetalle.setOnClickListener {
            clinica = clinicaResumen
            val detalleClinica = Intent(
                context,
                DetalleClinica::class.java
            )
            Log.d("ResumenClinica",clinica.toString())
            detalleClinica.putExtra("CLINICA", clinica)
            activity.startActivityForResult(detalleClinica,CODIGO_RESPUESTA_INTENT_EXPLICITO)

        }


    }

    private fun getHorarioDelDia(clinicaResumen: Clinica): CharSequence? {
        var horario = ""
        val calendar: Calendar = Calendar.getInstance()
        val day: Int = calendar.get(Calendar.DAY_OF_WEEK)

        when (day) {
            Calendar.SUNDAY -> {
                horario = clinicaResumen.horarios_atencion?.domingo.toString()
            }
            Calendar.MONDAY -> {
                horario = clinicaResumen.horarios_atencion?.lunes.toString()
            }
            Calendar.TUESDAY -> {
                horario = clinicaResumen.horarios_atencion?.martes.toString()
            }
            Calendar.WEDNESDAY -> {
                horario = clinicaResumen.horarios_atencion?.miercoles.toString()
            }
            Calendar.THURSDAY -> {
                horario = clinicaResumen.horarios_atencion?.jueves.toString()
            }
            Calendar.FRIDAY -> {
                horario = clinicaResumen.horarios_atencion?.viernes.toString()
            }
            Calendar.SATURDAY -> {
                horario = clinicaResumen.horarios_atencion?.sabado.toString()
            }

        }
        return horario

    }

    override fun getItemCount(): Int {
        return listaClinicas.size
    }
}
