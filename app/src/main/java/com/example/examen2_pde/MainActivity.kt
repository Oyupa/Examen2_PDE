package com.example.examen2_pde

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.examen2_pde.eventos.EventosActivity
import com.example.examen2_pde.farmacias.FarmaciasActivity
import com.example.examen2_pde.horario.HorarioActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonA: Button = findViewById(R.id.button_a)
        val buttonB: Button = findViewById(R.id.button_b)
        val buttonC: Button = findViewById(R.id.button_c)

        buttonA.setOnClickListener {
            startActivity(Intent(this, HorarioActivity::class.java))
        }

        buttonB.setOnClickListener {
            startActivity(Intent(this, EventosActivity::class.java))
        }

        buttonC.setOnClickListener {
            startActivity(Intent(this, FarmaciasActivity::class.java))
        }
    }

    private fun loadFarmaciasToFirestore() {
        // Inicializar Firebase
        FirebaseApp.initializeApp(this)
        val firestore = FirebaseFirestore.getInstance()

        // Leer el archivo JSON desde res/raw
        val inputStream = resources.openRawResource(R.raw.farmacias_equipamiento)
        val jsonData = BufferedReader(InputStreamReader(inputStream)).use { it.readText() }

        try {
            // Parsear el JSON
            val jsonObject = JSONObject(jsonData)
            val featuresArray = jsonObject.getJSONArray("features")

            // Subir cada elemento del JSON a Firestore
            for (i in 0 until featuresArray.length()) {
                val feature = featuresArray.getJSONObject(i)
                val properties = feature.getJSONObject("properties")
                val geometry = feature.getJSONObject("geometry")
                val coordinates = geometry.getJSONArray("coordinates")

                // Crear un mapa con los datos
                val data = mapOf(
                    "title" to properties.getString("title"),
                    "description" to properties.getString("description"),
                    "link" to properties.getString("link"),
                    "icon" to properties.getString("icon"),
                    "coordinates" to mapOf(
                        "latitude" to coordinates.getDouble(1),  // Latitud
                        "longitude" to coordinates.getDouble(0)  // Longitud
                    )
                )

                // Subir el documento a la colección "farmacias" en Firestore
                firestore.collection("farmacias")
                    .add(data)
                    .addOnSuccessListener { documentRef ->
                        println("Documento añadido con ID: ${documentRef.id}")
                    }
                    .addOnFailureListener { e ->
                        println("Error al añadir documento: $e")
                    }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
