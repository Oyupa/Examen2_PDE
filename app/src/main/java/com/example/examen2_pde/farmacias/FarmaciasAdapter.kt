package com.example.examen2_pde.farmacias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.examen2_pde.R

class FarmaciasAdapter(
    private val onItemClick: (Farmacia) -> Unit
) : RecyclerView.Adapter<FarmaciasAdapter.FarmaciaViewHolder>() {

    private val farmacias = mutableListOf<Farmacia>()

    fun submitList(list: List<Farmacia>) {
        farmacias.clear()
        farmacias.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmaciaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_farmacia, parent, false)
        return FarmaciaViewHolder(view)
    }

    override fun onBindViewHolder(holder: FarmaciaViewHolder, position: Int) {
        val farmacia = farmacias[position]
        holder.bind(farmacia)
    }

    override fun getItemCount(): Int = farmacias.size

    inner class FarmaciaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.textViewTitle)
        private val description: TextView = view.findViewById(R.id.textViewDescription)
        private val link: TextView = view.findViewById(R.id.textViewLink)
        private val icon: ImageView = view.findViewById(R.id.imageViewIcon)

        fun bind(farmacia: Farmacia) {
            title.text = farmacia.title
            description.text = farmacia.description
            link.text = farmacia.link

            icon.setImageResource(R.drawable.farmaciahorarioespecial)

            itemView.setOnClickListener {
                onItemClick(farmacia)
            }
        }
    }
}
