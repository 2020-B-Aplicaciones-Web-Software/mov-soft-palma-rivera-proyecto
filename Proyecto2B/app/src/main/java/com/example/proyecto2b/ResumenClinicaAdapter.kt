package com.example.proyecto2b

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.io.File

class ResumenClinicaAdapter(
    private val context:Class<*>,
    private val listaClinicas:List<Clinica>,
    private val recyclerView:RecyclerView
)  : RecyclerView.Adapter<ResumenClinicaAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nombreClinica=view.findViewById<TextView>(R.id.nombre_clinica_resumen)
        val direccionClinica=view.findViewById<TextView>(R.id.direccion_clinica_resumen)
        val telefonoClinica=view.findViewById<TextView>(R.id.telefono_clinica_resumen)
        val horarioClinica=view.findViewById<TextView>(R.id.horario_clinica_resumen)
        val calificacionClinica=view.findViewById<ImageView>(R.id.rating_resumen)
        val numResenas=view.findViewById<TextView>(R.id.num_resena_resumen)
        val btnMasInfo=view.findViewById<Button>(R.id.btn_mas_info_clinica)
        val foto=view.findViewById<ImageView>(R.id.foto_clinica_resumen)
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
        val storage=Firebase.storage
        val sr=storage.reference

        val clinicaResumen=listaClinicas[position]
        val path= clinicaResumen.foto_logo?.let { sr.child(it) }
        val file= File.createTempFile("profile","jpg")
        if (path != null) {
            path.getFile(file)
                .addOnSuccessListener {
                    val bitmap=Drawable.createFromPath(file.toString())
                    holder.foto.setImageDrawable(bitmap)
                }
        }
        holder.nombreClinica.text=clinicaResumen.nombre_clinica
        holder.direccionClinica.text=clinicaResumen.direccion_clinica
        holder.telefonoClinica.text=clinicaResumen.telefono_clinica
        holder.horarioClinica.text="10:00"
        holder.calificacionClinica.setImageResource(R.drawable.ic_baseline_message_24)
        holder.numResenas.text= clinicaResumen.resenias?.num_resenias.toString()

    }

    override fun getItemCount(): Int {
        return listaClinicas.size
    }
}
