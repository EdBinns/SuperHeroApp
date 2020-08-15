package com.edbinns.superheroapp.Models.SuperHero

data class Biography(
    val aliases: List<String>,
    val alignment: String,
    val alterEgos: String,
    val firstAppearance: String,
    val fullName: String,
    val placeOfbirth: String,
    val publisher: String
)