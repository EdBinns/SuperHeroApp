package com.edbinns.superheroapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.NetWork.Repositorys.ComicsRepository
import com.edbinns.superheroapp.View.Adapters.ItemListener

class ComicsViewModel : ViewModel() {

    private val comicsRepository = ComicsRepository()
    var comics : MutableLiveData<List<Comic>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()

    fun refresh(){
        callComics()
        comics = comicsRepository.getListComics()
    }

    private fun callComics(){
        comicsRepository.callNewComicsAPI()
        processFinished()
    }

    fun processFinished() {
        isLoading.value = true
    }

}