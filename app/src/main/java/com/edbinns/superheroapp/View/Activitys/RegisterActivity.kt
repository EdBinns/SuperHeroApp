package com.edbinns.superheroapp.View.Activitys

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Constants.BASIC_AUHT
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var userViewModel: UserViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        setUp()
    }

    fun setUp(){
        btnRegister.setOnClickListener {
            rlBase.visibility = View.VISIBLE
            userViewModel.isLoading.value = true
            val email = etEmailRegister.text.toString()
            val password = etPasswordRegister.text.toString()
            val name = etNameRegister.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()){
                if(password.length >= 6){
                    userViewModel.findUserByID(email)
                    val user = userViewModel.user.value
                    if(user != null){
                        showAlert("There is already an account registered to this email $email")
                    }else{
                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                            if(it.isSuccessful){
                                userViewModel.registerUser(email,password,name, BASIC_AUHT)
                                starApp(it.result?.user?.email.toString() ?: "",BASIC_AUHT)
                            }else{
                                showAlert("A problem has occurred with your registration")
                            }
                        }
                    }

                }else showAlert("The password must be greater than or equal to 6 characters")
            }
            observeViewModel()
        }
    }

    fun showAlert(message : String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        val dialog : AlertDialog = builder.create()
        dialog.show()
    }

    fun starApp(email:  String, providers : String){
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("providers", providers)
        }
        startActivity(intent)
    }

    fun observeViewModel(){
        userViewModel.messageResponse.observe(this, Observer<String> {
            showAlert(it)
        })

        userViewModel.isLoading.observe(this, Observer<Boolean>{
            if(it != null)
                rlBase.visibility = View.INVISIBLE
        })

        userViewModel.user.observe(this, Observer<User> {
            Log.d(ContentValues.TAG, "found user")
        })
    }

}