package com.edbinns.superheroapp.View.Activitys

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.edbinns.superheroapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("providers")
        setUp(email, provider)


        //Guardar datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider", provider)
        prefs.apply()
    }

    fun setUp(email: String?, provider:String?){

    }

    /**
     * buton.setonClick{
     *  val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
         prefs.clear()
         prfes.apply()
     * FirebaseAuth.getinstance.singOut
     * onBackpressed
     * }
     *
     *
     */
}