package com.edbinns.superheroapp.NetWork.Repositorys

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.NetWork.Constants
import com.edbinns.superheroapp.NetWork.Firebase.Callback
import com.edbinns.superheroapp.NetWork.Firebase.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class FavoritesRepository {
    private var firestoreService: FirestoreService = FirestoreService(FirebaseFirestore.getInstance())
    private val favorites : MutableLiveData<List<FavoritesSuperhero>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()
    private val isHeroFound : MutableLiveData<Boolean> = MutableLiveData()


    fun setFavoriteHeroInFirebase(favorite : FavoritesSuperhero){
        firestoreService.setDocument(favorite, Constants.FAVORITES_COLLECTION_NAME,"${favorite.emailUser}-${favorite.nombre}-${favorite.idHero}", object : Callback<Void> {
                override fun onSuccess(result: Void?) {
                    message.value = "Superhero added to favorites list"
                    Log.d("Success", "Superhero added to favorites list")
                }

                override fun onFailed(exception: Exception) {
                    message.value = "An error occurred while adding to favorites"
                    Log.e(ContentValues.TAG, "Could not add to favorites list", exception)
                }
            })
    }

    fun deleteFavoriteHeroFromFirebase(documentID : String){
        firestoreService.deleteFavorite(documentID,object : Callback<Void>{
            override fun onSuccess(result: Void?) {
                message.value = "Hero removed from favorites"
            }

            override fun onFailed(exception: Exception) {
                message.value = "An error occurred while deleting the hero"
                Log.e(ContentValues.TAG, "Could not delete hero", exception)
            }

        } )
    }

    fun searchFavoriteHeroInFirebase(documentID : String){
        firestoreService.searchFavorite(documentID, object : Callback<FavoritesSuperhero>{
            override fun onSuccess(result: FavoritesSuperhero?) {
                isHeroFound.value = result != null
            }
            override fun onFailed(exception: Exception) {
                message.value = "An error occurred while searching the hero"
                Log.e(ContentValues.TAG, "Could not search hero", exception)
            }
        })
    }

    fun getHeroFound(): MutableLiveData<Boolean>{
        return  isHeroFound
    }

    fun getFavoritesList() : MutableLiveData<List<FavoritesSuperhero>> {
        return favorites
    }

    fun callFavoritesFromFirebase(email: String?) {
        firestoreService.getFavorites(email, object : Callback<List<FavoritesSuperhero>> {
            override fun onSuccess(result: List<FavoritesSuperhero>?) {
                favorites.value = result
                Log.d("Success", "get favorites list")
            }

            override fun onFailed(exception: Exception) {
                message.value = "An error occurred while loading the favorites list"
                Log.e(ContentValues.TAG, "Error with get favorites list", exception)
            }
        })
    }

    fun getMessage():  MutableLiveData<String>{
        return message
    }
}