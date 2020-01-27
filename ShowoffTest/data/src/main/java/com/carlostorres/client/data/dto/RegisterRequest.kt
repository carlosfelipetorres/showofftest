package com.carlostorres.client.data.dto

data class RegisterRequest(val id_comercio: Int,
                           val nombre: String,
                           val apellido: String,
                           val email: String,
                           val cedula: String,
                           val cod_pais: String,
                           val id_ciudad: Int,
                           val celular: String,
                           val genero: String,
                           val cumpleanos: String,
                           val plataforma_origen: String,
                           val uid_cms: String,
                           val tipo_registro: String,
                           val tipo_documento: Int)