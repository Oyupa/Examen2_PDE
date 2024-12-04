package com.example.examen2_pde.eventos

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.examen2_pde.R
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class EventosActivity : AppCompatActivity() {

    private lateinit var listViewEventos: ListView
    private lateinit var btnAddEvento: Button
    private lateinit var btnCambiarIdioma: Button
    private val db = FirebaseFirestore.getInstance()
    private val eventosCollection = db.collection("eventos")
    private val eventosList = mutableListOf<Evento>()
    private lateinit var adapter: EventoAdapter
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        setAppLocale(getSavedLocale())

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)

        listViewEventos = findViewById(R.id.listViewEventos)
        btnAddEvento = findViewById(R.id.btnAddEvento)
        btnCambiarIdioma = findViewById(R.id.btnCambiarIdioma)

        adapter = EventoAdapter(this, eventosList)
        listViewEventos.adapter = adapter

        loadEventos()

        btnAddEvento.setOnClickListener {
            showAddEventoDialog()
        }

        btnCambiarIdioma.setOnClickListener {
            toggleLanguage()
        }

        listViewEventos.setOnItemClickListener { _, _, position, _ ->
            val evento = eventosList[position]
            showEventDetails(evento)
        }

        listViewEventos.setOnItemLongClickListener { _, _, position, _ ->
            val evento = eventosList[position]
            confirmDeleteEvento(evento)
            true
        }
    }

    private fun loadEventos() {
        eventosCollection.get().addOnSuccessListener { result ->
            eventosList.clear()
            for (document in result) {
                val evento = document.toObject(Evento::class.java).copy(id = document.id)
                eventosList.add(evento)
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun showEventDetails(evento: Evento) {
        val details = """
            ${getString(R.string.event_title)}: ${evento.titulo}
            ${getString(R.string.event_description)}: ${evento.descripcion}
            ${getString(R.string.event_address)}: ${evento.direccion}
            ${getString(R.string.event_price)}: ${evento.precio}
            ${getString(R.string.event_capacity)}: ${evento.aforo}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.event_details))
            .setMessage(details)
            .setPositiveButton(getString(R.string.ok), null)
            .show()
    }

    private fun confirmDeleteEvento(evento: Evento) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_event))
            .setMessage(getString(R.string.confirm_delete))
            .setPositiveButton(getString(R.string.delete_event)) { _, _ ->
                eventosCollection.document(evento.id).delete().addOnSuccessListener {
                    loadEventos()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun toggleLanguage() {
        val newLocale = if (getSavedLocale() == "en") "es" else "en"
        saveLocale(newLocale)
        setAppLocale(newLocale)
        recreate()
    }

    private fun setAppLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun getSavedLocale(): String {
        return sharedPreferences.getString("language", "es") ?: "es"
    }

    private fun saveLocale(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }

    private fun showAddEventoDialog() {
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val inputs = arrayOf(
            EditText(this).apply { hint = getString(R.string.event_title) },
            EditText(this).apply { hint = getString(R.string.event_description) },
            EditText(this).apply { hint = getString(R.string.event_address) },
            EditText(this).apply {
                hint = getString(R.string.event_price)
                inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            },
            EditText(this).apply {
                hint = getString(R.string.event_capacity)
                inputType = android.text.InputType.TYPE_CLASS_NUMBER
            }
        )
        inputs.forEach { layout.addView(it) }

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.anadirEvento))
            .setView(layout)
            .setPositiveButton(getString(R.string.anadirEvento)) { _, _ ->
                val titulo = inputs[0].text.toString()
                val descripcion = inputs[1].text.toString()
                val direccion = inputs[2].text.toString()
                val precio = inputs[3].text.toString().toDoubleOrNull() ?: 0.0
                val aforo = inputs[4].text.toString().toIntOrNull() ?: 0

                if (titulo.isNotBlank() && descripcion.isNotBlank()) {
                    val nuevoEvento = mapOf(
                        "titulo" to titulo,
                        "descripcion" to descripcion,
                        "direccion" to direccion,
                        "precio" to precio,
                        "aforo" to aforo
                    )
                    eventosCollection.add(nuevoEvento).addOnSuccessListener {
                        loadEventos()
                    }.addOnFailureListener {
                        Toast.makeText(this, getString(R.string.error_saving_event), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, getString(R.string.invalid_input), Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

}

