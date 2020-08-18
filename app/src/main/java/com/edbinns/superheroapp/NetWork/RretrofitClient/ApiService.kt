package com.edbinns.superheroapp.NetWork.RretrofitClient

import com.edbinns.superheroapp.Models.Comics.ComicResponse
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import retrofit2.*
import retrofit2.http.*
import retrofit2.Callback


interface ApiService {

    @GET("api/350921979254565/{id}")
    fun getSuperHeroInfo(@Path("id") id: String?) : Call<SuperHero>?

    @GET("comics/v1/new")
    fun getNewComics() :Call<ComicResponse>?
}