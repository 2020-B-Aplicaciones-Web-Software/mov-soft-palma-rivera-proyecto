package com.example.proyecto2b

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class ResumenReseniasAdapter (
    private val contexto: MostrarResenias,
    private val listaResenias: List<Resenia>,
    private val   recyclerView: RecyclerView,
) : RecyclerView.Adapter<ResumenReseniasAdapter.MyViewHolder>(){
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val nombre_apellidoTextView: TextView
        val calificacionTextView: TextView
        val comentarioTextView: TextView

        init {
            nombre_apellidoTextView = view.findViewById(R.id.tv_nombre_apellido)
            calificacionTextView = view.findViewById(R.id.tv_num_estrellas)
            comentarioTextView = view.findViewById(R.id.tv_comentario)
        }
    }
    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_view_resumen_resenias,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }

    //Setear los datos de cada iteracion del arreglo
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val resenia = listaResenias[position]
        holder.nombre_apellidoTextView.text = resenia.nombre
        holder.calificacionTextView.text = resenia.calificacion.toString()
        holder.comentarioTextView.text = resenia.descripcion
    }

    override fun getItemCount(): Int {
        return listaResenias.size
    }

}