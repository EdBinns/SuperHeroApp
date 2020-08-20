package com.edbinns.superheroapp.View.Activitys

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Constants
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.ViewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_register.*

class LogInActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 120
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        session()
        setUp()
    }


    fun setUp(){

        btnRegisterLogIn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener {
            rlProgressBar.visibility = View.VISIBLE
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                userViewModel.findUserByID(email)
                val user = userViewModel.user.value
                if (user != null) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                starApp(it.result?.user?.email.toString() ?: "", Constants.BASIC_AUHT)
                            } else {
                                println("${it.exception}")
                                showAlert("A problem has occurred with your log in ${it.exception?.message}")
                            }
                        }
                } else showAlert("You do not have an account registered in the app yet")
                observeViewModel()
            }
        }

        btnGoogle.setOnClickListener {
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this,googleConf)
            googleClient.signOut()
            startActivityForResult(googleClient.signInIntent,GOOGLE_SIGN_IN)
        }


    }

    fun session(){
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider =  prefs.getString("provider", null)

        if(email != null && provider != null ){
            starApp(email,provider)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                rlProgressBar.visibility = View.VISIBLE
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    println("Display name ${account.displayName}")
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                userViewModel.findUserByID(account.email ?: "")
                                val user = userViewModel.user.value
                                if (user != null) {
                                    starApp(account.email ?: "", Constants.GOOGLE_AUTH)
                                } else {
                                    userViewModel.registerUser(account.email!!, "--", account.displayName!!, Constants.GOOGLE_AUTH)
                                    starApp(account.email ?: "", Constants.GOOGLE_AUTH)
                                }
                            } else showAlert("A problem has occurred with your log in")
                            observeViewModel()
                        }
                }
            } catch (e: ApiException) {
                showAlert("A problem has occurred with your log in Api")
            }
        }
    }

    fun observeViewModel(){
        userViewModel.messageResponse.observe(this, Observer<String> {
            showAlert(it)
        })

        userViewModel.isLoading.observe(this, Observer<Boolean>{
            if(it != null)
                rlProgressBar.visibility = View.INVISIBLE
        })

        userViewModel.user.observe(this, Observer<User> {
            Log.d(ContentValues.TAG, "found user")
        })
    }

}