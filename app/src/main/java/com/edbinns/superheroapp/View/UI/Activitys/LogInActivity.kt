package com.edbinns.superheroapp.View.UI.Activitys

import  com.edbinns.superheroapp.View.UI.AlertDialog.MessageFactory
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edbinns.superheroapp.Models.User.User
import com.edbinns.superheroapp.NetWork.Constants
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.View.UI.AlertDialog.MessageFactory.Companion.TYPE_INFO
import com.edbinns.superheroapp.ViewModel.UserViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    private val GOOGLE_SIGN_IN = 120
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        observeViewModel()
        session()
        setUp()
    }


    fun setUp(){

        btnRegisterLogIn.setOnClickListener {
            observeViewModel()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btnLogIn.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            observeViewModel()
                            starApp(it.result?.user?.email.toString() ?: "", Constants.BASIC_AUHT)
                        } else {
                            observeViewModel()
                            showAlert("A problem has occurred with your log in ${it.exception?.message}")
                        }
                    }
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
            observeViewModel()
            starApp(email,provider)
        }
    }
    fun showAlert(message : String){
        val error = MessageFactory().getDialog(TYPE_INFO,this,message)
        error.show()
    }

    fun starApp(email:  String, providers : String){
        observeViewModel()
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("email", email)
            putExtra("providers", providers)
        }
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        rlProgressBar.visibility = View.VISIBLE
        if(requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                userViewModel.findUserByID(account.email ?: "")
                                Handler().postDelayed({
                                    val user = userViewModel.user.value
                                    if (user != null) {
                                        starApp(account.email ?: "", Constants.GOOGLE_AUTH)
                                    } else {
                                        userViewModel.registerUser(account.email!!, "--", account.displayName!!, Constants.GOOGLE_AUTH)
                                        starApp(account.email ?: "", Constants.GOOGLE_AUTH)
                                    }
                                }, 5000)
                            } else showAlert("A problem has occurred with your log in")
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