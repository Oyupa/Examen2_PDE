package com.example.examen2_pde.farmacias

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2_pde.R

class FarmaciaDetalleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_farmacia_detalle)

        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val link = intent.getStringExtra("link")
        val latitude = intent.getDoubleExtra("latitude", 0.0)
        val longitude = intent.getDoubleExtra("longitude", 0.0)

        findViewById<TextView>(R.id.textViewDetalleTitle).text = title
        findViewById<TextView>(R.id.textViewDetalleDescription).text = description
        findViewById<TextView>(R.id.textViewDetalleLink).text = link
        findViewById<TextView>(R.id.textViewDetalleCoordinates).text =
            "Coordinates: $latitude, $longitude"

        val buttonOpenMaps = findViewById<Button>(R.id.buttonOpenMaps)
        buttonOpenMaps.setOnClickListener {
            openMaps(latitude, longitude)
        }
    }
    private fun openMaps(latitude: Double, longitude: Double) {
        // Crear el Intent implícito para abrir Google Maps
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(Farmacia)")

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        startActivity(mapIntent)
        /* Verificar que hay una aplicación que pueda manejar el Intent
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Mostrar mensaje si no hay ninguna app instalada para manejar el Intent
            showError("No tienes ninguna aplicación de mapas instalada.")
        }
         */
    }

    private fun showError(message: String) {
        // Muestra un mensaje de error con un AlertDialog o un Toast
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
    }
}
