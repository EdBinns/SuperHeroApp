package com.edbinns.superheroapp.ViewModel

import android.os.Handler
import android.provider.ContactsContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.NetWork.Repositorys.FavoritesRepository
import com.edbinns.superheroapp.NetWork.Repositorys.SuperHeroReporitory

class FavoritesViewModel: ViewModel() {

    private val favoritesRepository = FavoritesRepository()
    private val superHeroReporitory = SuperHeroReporitory()
    var favoritesList : MutableLiveData<List<FavoritesSuperhero>> = MutableLiveData()
    var superhero = MutableLiveData<SuperHero>()
    var isLoading = MutableLiveData<Boolean>()

    fun setFavoriteHero(favoritesSuperhero: FavoritesSuperhero){
        favoritesRepository.setFavoriteHeroInFirebase(favoritesSuperhero)
    }

    fun refresh(email: String?){
        callFavoritesList(email)
        favoritesList = favoritesRepository.getFavoritesList()
    }

    private fun callFavoritesList(email: String?){
        favoritesRepository.callFavoritesFromFirebase(email)
        processFinished()
    }

    private fun processFinished() {
        isLoading.value = true
    }

    fun getSuperHero() {
        superhero = superHeroReporitory.getSuperHero()
    }

    fun callSuperHero(idHero: String) {
        superHeroReporitory.callSuperHeroApi(idHero)
        processFinished()
    }

}