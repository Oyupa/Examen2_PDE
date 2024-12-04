package com.example.examen2_pde.eventos

data class Evento(
    val id: String,
    val titulo: String,
    val descripcion: String,
    val direccion: String,
    val precio: Double,
    val aforo: Int
){
    constructor(): this("", "", "", "", 0.0, 0)
}
