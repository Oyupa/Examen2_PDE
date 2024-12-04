package com.example.examen2_pde.eventos

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.examen2_pde.R

class EventoAdapter(
    private val context: Context,
    private val eventos: List<Evento>
) : ArrayAdapter<Evento>(context, 0, eventos) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val evento = getItem(position)
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_evento, parent, false)

        val tvTitulo = view.findViewById<TextView>(R.id.tvTitulo)
        val tvDescripcion = view.findViewById<TextView>(R.id.tvDescripcion)
        val tvPrecio = view.findViewById<TextView>(R.id.tvPrecio)

        evento?.let {
            tvTitulo.text = it.titulo
            tvDescripcion.text = it.descripcion
            tvPrecio.text = context.getString(R.string.price_format, it.precio)
        }

        return view
    }
}
