package com.example.examen2_pde.horario

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_pde.R
import com.google.firebase.firestore.FirebaseFirestore

class DailyScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_schedule)

        val selectedDate = intent.getStringExtra("selected_date")
        val textDate = findViewById<TextView>(R.id.text_date)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        textDate.text = "Horario para: $selectedDate"
        recyclerView.layoutManager = LinearLayoutManager(this)

        val db = FirebaseFirestore.getInstance()
        db.collection("horario")
            .whereEqualTo("fecha", selectedDate)
            .get()
            .addOnSuccessListener { documents ->
                val scheduleList = documents.map { doc ->
                    ScheduleItem(
                        doc.getString("asignatura") ?: "",
                        doc.getString("hora") ?: ""
                    )
                }
                recyclerView.adapter = ScheduleAdapter(scheduleList)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar el horario", Toast.LENGTH_SHORT).show()
            }
    }
}
