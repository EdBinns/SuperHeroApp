package com.edbinns.superheroapp.Models.SuperHero

import java.io.Serializable

data class SuperHero (
    val appearance: Appearance,
    val biography: Biography,
    val connections: Connections,
    val id: String,
    val image: Image,
    val name: String,
    val powerstats: Powerstats,
    val response: String,
    val work: Work
) : Serializable