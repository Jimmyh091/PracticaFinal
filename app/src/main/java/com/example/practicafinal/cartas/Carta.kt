package com.example.practicafinal.cartas

data class Carta (
    var id_firebase : String? = null,
    var nombre: String = "Card",
    var descripcion: String = "Description",
    var categoria: String = "Category",
    var precio : Float = -1f,
    var imagen : String? = null,
    var stock : Int = -1
)