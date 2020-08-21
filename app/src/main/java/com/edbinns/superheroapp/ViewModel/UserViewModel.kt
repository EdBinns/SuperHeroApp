package com.edbinns.superheroapp.ViewModel


import android.app.Activity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Repositorys.UserRepository
import com.edbinns.superheroapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import java.security.AccessControlContext

import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import com.google.android.gms.auth.api.signin.internal.GoogleSignInOptionsExtensionParcelable
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class UserViewModel : ViewModel() {

    private val userReporitory = UserRepository()
    var messageResponse : MutableLiveData<String> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    var user : MutableLiveData<User> = MutableLiveData()

    fun registerUser(email: String, password: String, name: String, provider: String) {
        val user = User(email,name,  password, provider)
        userReporitory.setUserInFirebase(user)
        processFinished()
    }

    fun findUserByID(id : String){
        userReporitory.findUserByID(id)
        user = userReporitory.getUser()
        println("Print de viewmodel  ${user.value?.email}" )
    }

    fun getMessage()  {
        messageResponse =  userReporitory.sendMessage()
    }

    private fun processFinished() {
        isLoading.value = true
    }

}