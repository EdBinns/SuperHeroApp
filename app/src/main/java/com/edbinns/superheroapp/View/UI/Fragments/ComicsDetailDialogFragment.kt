package com.edbinns.superheroapp.View.UI.Fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.R
import kotlinx.android.synthetic.main.fragment_comics_detail_dialog.*


class ComicsDetailDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comics_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarComic.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        toolbarComic.setTitleTextColor(Color.WHITE)
        toolbarComic.setNavigationOnClickListener {
            dismiss()
        }

        val comic = arguments?.getSerializable("comic") as Comic

        tvTitleComic.text = comic.title
        tvPublisherComic.text = comic.publisher
        tvDescriptionComic.text = comic.description
        tvCreatorsComic.text = comic.creators
        tvDateComic.text = comic.release_date
        tvPriceComic.text = comic.price


    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}