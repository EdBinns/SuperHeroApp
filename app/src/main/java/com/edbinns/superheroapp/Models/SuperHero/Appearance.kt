package com.edbinns.superheroapp.Models.SuperHero

data class Appearance(
    var eyeColor: String?,
    var gender: String?,
    var hairColor: String?,
    var height: List<String>?,
    var race: String?,
    var weight: List<String>?
)