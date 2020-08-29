package com.edbinns.superheroapp.View.UI.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.View.UI.AlertDialog.MessageFactory
import com.edbinns.superheroapp.ViewModel.SuperHeroViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_super_hero.*

class SuperHeroFragment : Fragment() {

    private lateinit var viewModel: SuperHeroViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SuperHeroViewModel::class.java)
        listener()
        loadLastSeenHero()
        viewModel.refreshHero()
        Handler().postDelayed({
            observeViewModel()
        }, 3000)

    }

    fun listener() {
        forwardButton.setOnClickListener {
            rlSuperHero.visibility = View.VISIBLE
            viewModel.nextHero()
            waitObservable()
        }

        backButton.setOnClickListener {
            rlSuperHero.visibility = View.VISIBLE
            viewModel.backHero()
            waitObservable()
        }

        cardSuperHero.setOnClickListener {
            val superhero = viewModel.superhero.value
            val bundle = bundleOf("superhero" to superhero)
            findNavController().navigate(R.id.superheroDetailFragmentDialog, bundle)
        }
    }

    fun waitObservable(){
        Handler().postDelayed({
            observeViewModel()
        }, 3000)
    }

    @SuppressLint("RestrictedApi", "CommitPrefEdits")
    fun observeViewModel() {
        viewModel.superhero.observe(viewLifecycleOwner, Observer<SuperHero> {superhero ->
            tvNameHero.text = superhero.name
            Picasso.get().load(superhero.image?.url).into(imageSuperHero)
            rlSuperHero.visibility = View.INVISIBLE
        })

        viewModel.isSelect.observe(viewLifecycleOwner, Observer<Boolean> {
            if (viewModel.isSelect.value!!) backButton.visibility = View.VISIBLE
            else backButton.visibility = View.INVISIBLE
        })

        viewModel.idHero.observe(viewLifecycleOwner, Observer <Int>{
            val prefs = this.activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)?.edit()
            prefs?.putInt("lastHero",viewModel.idHero.value!!)
            prefs?.apply()
            Log.d("Actual hero: ", "${viewModel.idHero.value}")
        })
        viewModel.message.observe(viewLifecycleOwner, Observer<String> {
            showAlert(it, MessageFactory.TYPE_INFO)
        })
    }
    fun showAlert(message : String?, type : String){
        val error = MessageFactory().getDialog(type,context!!,message)
        error.show()
    }


    fun loadLastSeenHero() {
        val prefs = this.activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        val id = prefs?.getInt("lastHero", 1)
        viewModel.idHero.value = id
        if(id != 1){
            viewModel.isSelect.value = true
        }
    }
}