package com.example.proyecto2b

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
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
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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
    var permisos: Boolean =false
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
        val btnLlamar = view.findViewById<ImageView>(R.id.btn_llamar_resumen)
        val btnMensaje = view.findViewById<ImageView>(R.id.btn_mensaje_resumen)

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
        holder.calificacionClinica.rating=clinicaResumen.resenias?.promedio!!.toFloat()
        holder.numResenas.text = clinicaResumen.resenias?.num_resenias.toString() + " reseña(s)"
        holder.irDetalle.setOnClickListener {
            clinica = clinicaResumen
            val detalleClinica = Intent(
                context,
                DetalleClinica::class.java
            )
            detalleClinica.putExtra("CLINICA", clinica)
            activity.startActivityForResult(detalleClinica, CODIGO_RESPUESTA_INTENT_EXPLICITO)

        }
        holder.btnMensaje
            .setOnClickListener {
                clinicaResumen.telefono_clinica?.let { sendSMS(clinicaResumen.telefono_clinica!!) }
            }
        holder.btnLlamar
            .setOnClickListener {
                clinicaResumen.telefono_clinica?.let { call(clinicaResumen.telefono_clinica!!) }
            }


    }

    fun sendSMS(numero: String) {
        val uri: Uri = Uri.parse("smsto:${numero}")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(
            "sms_body",
            "Buenos días, deseo obtener mas informacion sobre sus servicios," +
                    " me enteré de esta cllínica por medio de VetApp"
        )
        startActivity(context, intent, null)
    }

    fun call(numero: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$numero")
        startActivity(context, intent, null)
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
