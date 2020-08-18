package com.edbinns.superheroapp.NetWork.Firebase

import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.NetWork.Constants.FAVORITES_COLLECTION_NAME
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreService(val firebaseFirestore: FirebaseFirestore) {

    fun setDocument(data: Any, collectionName: String, id: String, callback: Callback<Void>) {
        firebaseFirestore.collection(collectionName).document(id).set(data)
            .addOnSuccessListener { callback.onSuccess(null) }
            .addOnFailureListener { exception -> callback.onFailed(exception) }
    }

    fun getFavorites(callback: Callback<List<FavoritesSuperhero>>?, emailUser : String?) {
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

    fun listenForUpdates(favorites: List<FavoritesSuperhero>, listener: RealtimeDataListener<FavoritesSuperhero>) {
        val favoritesReference = firebaseFirestore.collection(FAVORITES_COLLECTION_NAME)
        for (favorite in favorites) {
            favoritesReference.document(favorite.emailUser).addSnapshotListener { snapshot, e ->
                if (e != null) {
                    listener.onError(e)
                }
                if (snapshot != null && snapshot.exists()) {
                    listener.onDataChange(snapshot.toObject(FavoritesSuperhero::class.java)!!)
                }
            }
        }
    }
}