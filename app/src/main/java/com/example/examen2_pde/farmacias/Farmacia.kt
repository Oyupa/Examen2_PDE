package com.example.examen2_pde.farmacias

data class Farmacia(
    val title: String = "",
    val description: String = "",
    val link: String = "",
    val coordinates: Map<String, Double> = emptyMap()
) {
    constructor(): this("", "", "", emptyMap())
}