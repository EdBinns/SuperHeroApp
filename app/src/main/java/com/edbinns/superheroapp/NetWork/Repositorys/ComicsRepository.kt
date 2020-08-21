package com.edbinns.superheroapp.NetWork.Repositorys

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.Models.Comics.ComicResponse
import com.edbinns.superheroapp.NetWork.RretrofitClient.ClientService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicsRepository {

    private val comics : MutableLiveData<List<Comic>> = MutableLiveData()

    fun getListComics() : MutableLiveData<List<Comic>>{
        return comics
    }


    fun callNewComicsAPI(){
        val client = ClientService.getInstance()
        var comicList : List<Comic>? = null
        client?.getComicsService()?.getNewComics()?.enqueue(object : Callback<ComicResponse>{
            override fun onResponse(call: Call<ComicResponse>, response: Response<ComicResponse>) {
                if(response.isSuccessful){
                   comicList = response.body()?.comics!!
                }
                comics.value = comicList
            }

            override fun onFailure(call: Call<ComicResponse>, t: Throwable) {
                Log.e("ERROR: ", t.message)
                t.stackTrace
            }
        })
    }
}