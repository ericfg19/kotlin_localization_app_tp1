package com.example.localization.model

import java.util.*

data class Localizacao (
    val id : Int,
    val latitude : String,
    val longitude : String,
    var horario : Date
)