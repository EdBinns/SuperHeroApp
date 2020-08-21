package com.edbinns.superheroapp.NetWork.RretrofitClient

import com.edbinns.superheroapp.NetWork.Constants.COMICS_BASE_URL
import com.edbinns.superheroapp.NetWork.Constants.SUPERHERO_BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ClientService {

    companion object{
        private var instance : ClientService? = null
        fun getInstance(): ClientService?{
            if(instance == null) instance =
                ClientService()
            return instance as ClientService
        }
    }
    fun getSuperHeroService() : ApiService {
        val client = OkHttpClient.Builder().build()
        return getService(SUPERHERO_BASE_URL, client)
    }

    fun getComicsService() : ApiService{
        val client = OkHttpClient.Builder().build()
        return getService(COMICS_BASE_URL, client)
    }
    private fun getService(base_url : String, okHttpClient : OkHttpClient): ApiService {
        //El okHttpClient permite que la app tenga un mejor rendimiento a la hora de realizar consultas
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}