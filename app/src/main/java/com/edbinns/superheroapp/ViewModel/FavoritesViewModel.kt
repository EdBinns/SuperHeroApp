package com.edbinns.superheroapp.ViewModel


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
    var emailUser = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    var isFavoriteFound = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()


    fun setFavoriteHero(favoritesSuperhero: FavoritesSuperhero) {
        favoritesRepository.setFavoriteHeroInFirebase(favoritesSuperhero)
    }

    fun deleteFavoriteHero(documentID : String){
        favoritesRepository.deleteFavoriteHeroFromFirebase(documentID)
    }

    fun getHeroFound() {
        isFavoriteFound = favoritesRepository.getHeroFound()
        searchHero()
    }

    private fun searchHero(){

        val documentID = "${emailUser.value}-${superhero.value?.name}-${superhero.value?.id}"
        favoritesRepository.searchFavoriteHeroInFirebase(documentID)
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
        message = favoritesRepository.getMessage()
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