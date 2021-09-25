package com.example.proyecto2b

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

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
        val clinicaResumen=listaClinicas[position]
        holder.nombreClinica
        holder.direccionClinica
        holder.telefonoClinica
        holder.horarioClinica
        holder.calificacionClinica
        holder.numResenas

    }

    override fun getItemCount(): Int {
        return listaClinicas.size
    }
}
