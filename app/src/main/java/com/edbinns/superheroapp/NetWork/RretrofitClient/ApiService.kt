package com.edbinns.superheroapp.NetWork.RretrofitClient

import com.edbinns.superheroapp.Models.Comics.ComicResponse
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("api/350921979254565/{id}")
    fun getSuperHeroInfo(@Path("id") id: String?) : Call<SuperHero>?

    @GET("comics/v1/new")
    fun getNewComics() :Call<ComicResponse>?
}