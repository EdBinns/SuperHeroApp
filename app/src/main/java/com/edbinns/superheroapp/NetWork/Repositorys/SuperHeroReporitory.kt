package com.edbinns.superheroapp.NetWork.Repositorys

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.NetWork.Constants.FAVORITES_COLLECTION_NAME
import com.edbinns.superheroapp.NetWork.Firebase.*
import com.edbinns.superheroapp.NetWork.RretrofitClient.ClientService
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SuperHeroReporitory{


    private val superhero : MutableLiveData<SuperHero> = MutableLiveData()
    private val favorites : MutableLiveData<List<SuperHero>> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()


    fun getSuperHero(): MutableLiveData<SuperHero> {
      return  superhero
    }

    fun callSuperHeroApi(id : String) {
        val client = ClientService.getInstance()
        var hero : SuperHero? = null
       client?.getSuperHeroService()?.getSuperHeroInfo(id)?.enqueue(object : Callback<SuperHero>{
           override fun onResponse(call: Call<SuperHero>, response: Response<SuperHero>) {
               if(response.isSuccessful){
                   hero = response.body()
               }
               println("AlterEgos    ${hero?.biography?.alterEgos}")
               superhero.value = hero
           }
           override fun onFailure(call: Call<SuperHero>, t: Throwable) {
               Log.e("ERROR: ", t.message)
               t.stackTrace
           }
       })
    }


}