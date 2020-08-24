package com.edbinns.superheroapp.NetWork.Repositorys


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.NetWork.RretrofitClient.ClientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
               superhero.value = hero
           }
           override fun onFailure(call: Call<SuperHero>, t: Throwable) {
               message.value = "An error occurred while loading the next hero"
               Log.e("ERROR: ", t.message)
               t.stackTrace
           }
       })
    }

    fun getMessage():  MutableLiveData<String>{
        return message
    }
}