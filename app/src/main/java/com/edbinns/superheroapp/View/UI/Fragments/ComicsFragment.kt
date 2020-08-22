package com.edbinns.superheroapp.View.UI.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.View.Adapters.ComicsAdapter
import com.edbinns.superheroapp.View.Adapters.ItemListener
import com.edbinns.superheroapp.ViewModel.ComicsViewModel
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.fragment_comics.*


class ComicsFragment : Fragment() , ItemListener<Comic> {

    private lateinit var comicAdapter: ComicsAdapter
    private lateinit var viewModel: ComicsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ComicsViewModel::class.java)
        viewModel.refresh()

        comicAdapter = ComicsAdapter(this)

        rvComics.apply {
            layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            adapter = comicAdapter
        }

        observeViewModel()
    }


    private fun observeViewModel(){
        viewModel.comics.observe(viewLifecycleOwner, Observer<List<Comic>> { comics ->
            comicAdapter.updateData(comics)
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer<Boolean>{
            if(it != null)
                rlComics.visibility = View.INVISIBLE
        })
    }

    override fun onItemClicked(item: Comic, position: Int) {
        val bundle = bundleOf("comic" to item)
        findNavController().navigate(R.id.comicsDetailFragmentDialog, bundle)
    }


}