package com.edbinns.superheroapp.NetWork.Firebase

import java.lang.Exception

interface Callback<T> {
    fun onSuccess(result: T?)

    fun onFailed(exception: Exception)
}