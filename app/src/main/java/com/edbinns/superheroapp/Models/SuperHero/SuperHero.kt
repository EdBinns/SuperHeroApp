package com.edbinns.superheroapp.Models.SuperHero

import java.io.Serializable

class SuperHero  : Serializable {
    var appearance: Appearance? =null
    var biography: Biography? =null
    var connections: Connections? =null
    var id: String? = ""
    var image: Image? =null
    var name: String? = ""
    var powerstats: Powerstats? =null
    var response: String? =null
    var work: Work? =null
}


