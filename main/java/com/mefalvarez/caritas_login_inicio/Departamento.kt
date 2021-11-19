package com.mefalvarez.caritas_login_inicio

data class Departamento (
    val departamento: String,
    val correo: String
        ){

    override fun toString(): String {
        return departamento
    }
}