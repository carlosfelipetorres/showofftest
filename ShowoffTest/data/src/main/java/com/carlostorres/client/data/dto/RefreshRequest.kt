package com.carlostorres.client.data.dto

data class RefreshRequest(val refresh_token: String, val uid_cms: String, val id_sucursal: Int)