package com.example.examen2_pde.horario

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2_pde.R
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class HorarioActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horario)

        db = FirebaseFirestore.getInstance()

        val buttonCreateSchedule: Button = findViewById(R.id.button_create_schedule)
        val buttonViewSchedule: Button = findViewById(R.id.button_view_schedule)
        val buttonCurrentSubject: Button = findViewById(R.id.button_current_subject)

        buttonCreateSchedule.setOnClickListener {
            openCreateScheduleDialog()
        }

        buttonViewSchedule.setOnClickListener {
            openDayScheduleDialog()
        }

        buttonCurrentSubject.setOnClickListener {
            fetchCurrentSubject()
        }
    }

    private fun openCreateScheduleDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_create_schedule, null)
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Crear Asignatura")
            .setView(dialogView)
            .setPositiveButton("Guardar") { _, _ ->
                val subject = dialogView.findViewById<EditText>(R.id.edit_subject).text.toString()
                val selectedDate = dialogView.findViewById<TextView>(R.id.text_selected_date).text.toString()
                val selectedHour = dialogView.findViewById<Spinner>(R.id.spinner_hour).selectedItem.toString()
                val selectedMinute = dialogView.findViewById<Spinner>(R.id.spinner_minute).selectedItem.toString()

                if (subject.isNotBlank() && selectedDate.isNotBlank()) {
                    saveScheduleToFirebase(subject, selectedDate, "$selectedHour:$selectedMinute")
                } else {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancelar", null)
            .create()

        val datePicker = dialogView.findViewById<Button>(R.id.button_select_date)
        datePicker.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, year, month, day ->
                    dialogView.findViewById<TextView>(R.id.text_selected_date).text = "$day/${month + 1}/$year"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        alertDialog.show()
    }

    private fun saveScheduleToFirebase(subject: String, date: String, time: String) {
        val schedule = hashMapOf(
            "asignatura" to subject,
            "fecha" to date,
            "hora" to time
        )

        db.collection("horario")
            .add(schedule)
            .addOnSuccessListener {
                Toast.makeText(this, "Horario guardado correctamente", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar el horario", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openDayScheduleDialog() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(
            this,
            { _, year, month, day ->
                val selectedDate = "$day/${month + 1}/$year"
                val intent = Intent(this, DailyScheduleActivity::class.java)
                intent.putExtra("selected_date", selectedDate)
                startActivity(intent)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private fun fetchCurrentSubject() {
        val now = SimpleDateFormat("d/MM/yyyy", Locale.getDefault()).format(Date()) // Solo la fecha
        val currentDate = now

        db.collection("horario")
            .whereEqualTo("fecha", currentDate)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    AlertDialog.Builder(this)
                        .setTitle("Horario del día")
                        .setMessage("No hay clases programadas para hoy.")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    val subjects = StringBuilder() // Usamos un StringBuilder para mostrar todas las clases
                    for (document in documents) {
                        val subject = document.getString("asignatura") ?: "Desconocida"
                        val time = document.getString("hora") ?: "Hora no disponible"
                        subjects.append("Asignatura: $subject, Hora: $time\n")
                    }
                    AlertDialog.Builder(this)
                        .setTitle("Horario del día")
                        .setMessage(subjects.toString())
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al consultar la base de datos", Toast.LENGTH_SHORT).show()
            }
    }

}
