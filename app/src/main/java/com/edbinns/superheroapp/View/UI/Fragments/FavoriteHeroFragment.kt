package com.edbinns.superheroapp.View.UI.Fragments

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.Models.SuperHero.SuperHero
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.View.Adapters.FavoriteHeroesAdapter
import com.edbinns.superheroapp.View.Adapters.ItemListener
import com.edbinns.superheroapp.View.UI.AlertDialog.MessageFactory
import com.edbinns.superheroapp.ViewModel.FavoritesViewModel
import kotlinx.android.synthetic.main.fragment_favorite_hero.*


class FavoriteHeroFragment : Fragment(),ItemListener<FavoritesSuperhero>  {

    private lateinit var viewModel : FavoritesViewModel
    private lateinit var favoritesAdapter: FavoriteHeroesAdapter
    private  var emailUser: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite_hero, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        val prefs = this.activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        emailUser = prefs?.getString("email", "-")
        viewModel.refresh(emailUser)

        favoritesAdapter= FavoriteHeroesAdapter(this)

        rvFavoriteHero.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = favoritesAdapter
        }

        Handler().postDelayed({
            observeViewModel()
        }, 1500)
    }

    fun observeViewModel(){
        viewModel.favoritesList.observe(viewLifecycleOwner, Observer<List<FavoritesSuperhero>> { favorites ->
            favoritesAdapter.updateData(favorites)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it != null)
                rlFavorites.visibility = View.INVISIBLE
        })

        viewModel.superhero.observe(viewLifecycleOwner, Observer<SuperHero> {
            Log.d("Obtained hero ", it.id)
        })

        viewModel.message.observe(viewLifecycleOwner, Observer<String> {
            showAlert(it, MessageFactory.TYPE_INFO)
        })
    }

    fun showAlert(message : String?, type : String){
        val error = MessageFactory().getDialog(type,context!!,message)
        error.show()
    }
    override fun onItemClicked(item: FavoritesSuperhero, position: Int) {
        viewModel.callSuperHero(item.idHero)
        Handler().postDelayed({
            observeViewModel()
            viewModel.getSuperHero()
            val superhero = viewModel.superhero.value
            val bundle = bundleOf("superhero" to superhero)
            findNavController().navigate(R.id.superheroDetailFragmentDialog, bundle)
        }, 4500)
    }
}