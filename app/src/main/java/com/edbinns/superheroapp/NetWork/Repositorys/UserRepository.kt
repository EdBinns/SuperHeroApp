package com.edbinns.superheroapp.NetWork.Repositorys

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Constants
import com.edbinns.superheroapp.NetWork.Firebase.Callback
import com.edbinns.superheroapp.NetWork.Firebase.FirestoreService
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception

class UserRepository {

    private var firestoreService: FirestoreService = FirestoreService(FirebaseFirestore.getInstance())

    private val message: MutableLiveData<String> = MutableLiveData()
    private val user: MutableLiveData<User> = MutableLiveData()

    fun setUserInFirebase(user: User) {
        firestoreService.setDocument(user, Constants.USERS_COLLECTION_NAME, user.email, object : Callback<Void> {
                override fun onSuccess(result: Void?) {
                    message.value = "Registration completed"
                }

                override fun onFailed(exception: Exception) {
                    Log.e(TAG, "error en setear a un usuario", exception)
                }
            })
    }

    fun getUser(): MutableLiveData<User> {
        return user
    }

    fun findUserByID(username: String) {
        firestoreService.getUserByID(username, object : Callback<User> {

            override fun onSuccess(result: User?) {
                user.value = result
            }

            override fun onFailed(exception: Exception) {
                Log.e(TAG, "error en encontrar a un usuario", exception)
            }
        })
    }

    fun sendMessage(): MutableLiveData<String> {
        return message
    }
}
