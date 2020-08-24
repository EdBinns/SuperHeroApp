package com.edbinns.superheroapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.NetWork.Repositorys.SuperHeroReporitory

class SuperHeroViewModel : ViewModel() {


    private val superHeroReporitory = SuperHeroReporitory()
    var idHero = MutableLiveData<Int>()
    var superhero = MutableLiveData<SuperHero>()
    var isSelect = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    init {
        isSelect.value = false
        idHero.value = 1
    }

    fun refreshHero() {
        callSuperhero()
        superhero = superHeroReporitory.getSuperHero()
    }

    fun callSuperhero() {
        val id = idHero.value.toString()
        superHeroReporitory.callSuperHeroApi(id)
    }

    fun nextHero() {
        idHero.value = idHero.value?.plus(1)
        isSelect.value = true
        refreshHero()
        message = superHeroReporitory.getMessage()
    }

    fun backHero() {
        idHero.value = idHero.value?.minus(1)
        if (idHero.value!! == 1) {
            isSelect.value = false
        }
        refreshHero()
        message = superHeroReporitory.getMessage()
    }

}