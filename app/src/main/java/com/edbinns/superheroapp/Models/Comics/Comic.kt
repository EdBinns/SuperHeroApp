package com.edbinns.superheroapp.Models.Comics

import java.io.Serializable

data class Comic(
    val creators: String,
    val description: String,
    val diamond_id: String,
    val price: String,
    val publisher: String,
    val release_date: String,
    val title: String
) : Serializable