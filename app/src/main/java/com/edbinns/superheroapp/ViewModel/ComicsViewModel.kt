package com.edbinns.superheroapp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.NetWork.Repositorys.ComicsRepository


class ComicsViewModel : ViewModel() {

    private val comicsRepository = ComicsRepository()
    var comics : MutableLiveData<List<Comic>> = MutableLiveData()
    var isLoading = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    fun refresh(){
        callComics()
        comics = comicsRepository.getListComics()
        processFinished()
    }

    private fun callComics(){
        comicsRepository.callNewComicsAPI()
    }

    fun processFinished() {
        message = comicsRepository.getMessage()
        isLoading.value = true
    }

}