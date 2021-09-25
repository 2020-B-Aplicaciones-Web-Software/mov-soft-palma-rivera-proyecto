package com.example.proyecto2b

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ResumenClinicas : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumen_clinicas)
        val rvClinica = findViewById<RecyclerView>(R.id.rv_lista_clinicas)
        val lista = ArrayList<Clinica>()
        iniciarRecyclerView(
            lista,
            this,
            rvClinica
        )
    }

    private fun iniciarRecyclerView(
        list: List<Clinica>,
        resumenClinicas: ResumenClinicas,
        rvClinica: RecyclerView?
    ) {
        val adapter = rvClinica?.let {
            ResumenClinicaAdapter(
                resumenClinicas::class.java, list,
                it
            )
        }
        if (rvClinica != null) {
            rvClinica.adapter = adapter
            rvClinica.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
            rvClinica.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(resumenClinicas)
        }


    }


}