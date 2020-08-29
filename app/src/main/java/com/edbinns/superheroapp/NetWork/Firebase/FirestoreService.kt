package com.edbinns.superheroapp.NetWork.Firebase

import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Constants.FAVORITES_COLLECTION_NAME
import com.edbinns.superheroapp.NetWork.Constants.USERS_COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {

    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>) {
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun getUserByID(id: String,callback: Callback<User>?) {
        firebaseFirestore.collection(USERS_COLLECTION_NAME).document(id).get()
            .addOnSuccessListener {result ->
                if (result.data != null) {
                    callback?.onSuccess(result.toObject(User::class.java))
                } else {
                    callback?.onSuccess(null)
                }
            }
            .addOnFailureListener { exception -> callback?.onFailed(exception) }
    }

    fun getFavorites( emailUser : String?,callback: Callback<List<FavoritesSuperhero>>?) {
        firebaseFirestore.collection(FAVORITES_COLLECTION_NAME).whereEqualTo("emailUser", emailUser).get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val favoritesList = result.toObjects(FavoritesSuperhero::class.java)
                    callback!!.onSuccess(favoritesList)
                    break
                }
            }
            .addOnFailureListener { exception -> callback!!.onFailed(exception) }
    }

    fun deleteFavorite(id: String, callback: Callback<Void>){
        firebaseFirestore.collection(FAVORITES_COLLECTION_NAME).document(id).delete()
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun searchFavorite(id: String, callback: Callback<FavoritesSuperhero>){
        firebaseFirestore.collection(FAVORITES_COLLECTION_NAME).document(id).get()
            .addOnSuccessListener { result ->
                val favoritesList = result.toObject(FavoritesSuperhero::class.java)
                callback.onSuccess(favoritesList)
            }
            .addOnFailureListener{ exception -> callback.onFailed(exception) }
    }

}
