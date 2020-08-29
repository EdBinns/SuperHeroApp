package com.edbinns.superheroapp.NetWork.RretrofitClient

import com.edbinns.superheroapp.Models.Comics.ComicResponse
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("api/350921979254565/{id}")
    fun getSuperHeroInfo(@Path("id") id: String?) : Call<JsonObject>?

    @GET("comics/v1/new")
    fun getNewComics() :Call<ComicResponse>?

}