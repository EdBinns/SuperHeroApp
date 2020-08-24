package com.edbinns.superheroapp.View.UI.AlertDialog

import android.app.AlertDialog
import android.content.Context

class MessageFactory {
    companion object{
        const val TYPE_INFO = "typeInfo"
    }

    fun getDialog(type: String, context: Context, message : String?): AlertDialog.Builder{
        when(type){
            TYPE_INFO ->{
                return  AlertDialog.Builder(context)
                    .setMessage(message)
            }
        }
        return  AlertDialog.Builder(context)
            .setMessage(message)
    }
}