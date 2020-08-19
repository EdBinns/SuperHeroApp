package com.edbinns.superheroapp.View.Activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.edbinns.superheroapp.NetWork.Constants.BASIC_AUHT
import com.edbinns.superheroapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        setUp()
    }

    fun setUp(){
        btnRegister.setOnClickListener {
            if(etEmailRegister.text.isNotEmpty() && etPasswordRegister.text.isNotEmpty()){
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(etEmailRegister.text.toString(), etPasswordRegister.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){
                        starApp(it.result?.user?.email.toString() ?: "",BASIC_AUHT)
                    }else showAlert()
                }
            }
        }
    }

    fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Ha ocurrido un problema con su registro")
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

}