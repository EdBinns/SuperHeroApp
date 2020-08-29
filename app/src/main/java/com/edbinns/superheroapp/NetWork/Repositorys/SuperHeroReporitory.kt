package com.edbinns.superheroapp.NetWork.Repositorys


import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.Developer.Developer
import com.edbinns.superheroapp.Models.SuperHero.*
import com.edbinns.superheroapp.NetWork.RretrofitClient.ClientService
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SuperHeroReporitory{


    private val superhero : MutableLiveData<SuperHero> = MutableLiveData()
    private val message: MutableLiveData<String> = MutableLiveData()


    fun getSuperHero(): MutableLiveData<SuperHero> {
      return  superhero
    }

    fun callSuperHeroApi(id : String) {
        val client = ClientService.getInstance()
        val hero = SuperHero()
       client?.getSuperHeroService()?.getSuperHeroInfo(id)?.enqueue(object : Callback<JsonObject>{
           override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
               if(response.isSuccessful){

                   val heroName = response.body()?.get("name")
                   val name = heroName?.asString
                   val heroID = response.body()?.get("id")?.asString

                   val powerstatsJson =  response.body()?.getAsJsonObject("powerstats")
                   val powerstats =  Gson().fromJson(powerstatsJson,Powerstats::class.java)

                   val biographyJson = response.body()?.getAsJsonObject("biography")
                   val biography = Gson().fromJson(biographyJson,Biography::class.java)
                   biography.apply {
                       fullName = biographyJson?.get("full-name")?.asString!!
                       alterEgos =  biographyJson.get("alter-egos")?.asString!!
                       placeOfbirth =  biographyJson.get("place-of-birth")?.asString!!
                       firstAppearance = biographyJson.get("first-appearance")?.asString!!
                   }
                   val appearanceJson = response.body()?.getAsJsonObject("appearance")
                   val appearance = Gson().fromJson(appearanceJson,Appearance::class.java)
                   appearance.apply {
                       eyeColor = appearanceJson?.get("eye-color")?.asString!!
                       hairColor =  appearanceJson.get("hair-color")?.asString!!
                   }

                   val workJson = response.body()?.getAsJsonObject("work")
                   val work = Gson().fromJson(workJson,Work::class.java)

                   val connectionsJson = response.body()?.getAsJsonObject("connections")
                   val connections =  Gson().fromJson(connectionsJson,Connections::class.java)
                   connections.apply {
                       groupAffiliation = connectionsJson?.get("group-affiliation")?.asString!!
                   }
                   val imageJson = response.body()?.getAsJsonObject("image")
                   val image =  Gson().fromJson(imageJson,Image::class.java)
                   hero.apply {
                       this.name = name
                       this.id = heroID
                       this.biography = biography
                       this.powerstats = powerstats
                       this.appearance = appearance
                       this.work = work
                       this.image = image
                       this.connections = connections
                   }
               }
            superhero.value = hero
           }
           override fun onFailure(call: Call<JsonObject>, t: Throwable) {
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