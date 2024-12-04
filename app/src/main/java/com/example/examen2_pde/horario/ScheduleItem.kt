package com.example.examen2_pde.horario

data class ScheduleItem(val subject: String, val time: String){
    constructor() : this("", "")
}
