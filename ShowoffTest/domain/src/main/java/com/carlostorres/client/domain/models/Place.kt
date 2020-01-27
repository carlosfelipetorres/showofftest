package com.carlostorres.client.domain.models

import java.io.Serializable

data class Place(var name: String? = null,
                 var location: Location? = null): Serializable

data class Location(
    var city: String? = null,
    var country: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var street: String? = null,
    var zip: String? = null): Serializable