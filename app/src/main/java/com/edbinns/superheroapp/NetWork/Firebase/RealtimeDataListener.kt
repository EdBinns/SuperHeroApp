package com.edbinns.superheroapp.NetWork.Firebase

import java.lang.Exception

interface RealtimeDataListener<T> {

    fun onDataChange(updatedData: T)

    fun onError(exception: Exception)

}