package com.edbinns.superheroapp.View.Adapters

interface ItemListener<T> {
    fun onItemClicked(item: T, position: Int)
}