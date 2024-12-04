package com.example.examen2_pde.farmacias

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_pde.R
import com.google.firebase.firestore.FirebaseFirestore



class FarmaciasActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FarmaciasAdapter
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmacias)

        recyclerView = findViewById(R.id.recyclerViewFarmacias)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FarmaciasAdapter { farmacia ->
            val intent = Intent(this, FarmaciaDetalleActivity::class.java).apply {
                putExtra("title", farmacia.title)
                putExtra("description", farmacia.description)
                putExtra("link", farmacia.link)
                putExtra("latitude", farmacia.coordinates["latitude"])
                putExtra("longitude", farmacia.coordinates["longitude"])
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        loadFarmacias()
    }

    private fun loadFarmacias() {
        firestore.collection("farmacias")
            .get()
            .addOnSuccessListener { result ->
                val farmacias = result.map { doc ->
                    doc.toObject(Farmacia::class.java)
                }
                adapter.submitList(farmacias)
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
    }
}
