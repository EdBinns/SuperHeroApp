package com.edbinns.superheroapp.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.NetWork.Repositorys.SuperHeroReporitory
import com.edbinns.superheroapp.R

class SuperHeroViewModel : ViewModel() {


    private val superHeroReporitory = SuperHeroReporitory()
    var idHero = MutableLiveData<Int>()
    var superhero = MutableLiveData<SuperHero>()
    var isSelect = MutableLiveData<Boolean>()

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
    }

    fun backHero() {
        idHero.value = idHero.value?.minus(1)
        if (idHero.value!! == 1) {
            isSelect.value = false
        }
        refreshHero()
    }

}