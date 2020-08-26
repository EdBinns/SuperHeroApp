package com.edbinns.superheroapp.View.UI.Fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.edbinns.superheroapp.Models.Developer.Developer
import com.edbinns.superheroapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_super_hero_detail_dialog.*

class AboutFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDeveloperInfo()
    }

    fun setDeveloperInfo(){
        val developer = Developer()
        Picasso.get().load(developer.photoURL).into(ivDeveloperPhoto)
        tvDeveloperName.text = developer.developerName
        tvDeveloperJobtitle.text = developer.jobTitle
        tvDeveloperWorkplace.text = developer.workPlace
        tvDeveloperBiography.text = developer.developerBiography


        btnLinkedIn.setOnClickListener {
            var intent: Intent

            try {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(developer.linkedInURL))
            } catch (e: Exception) {
                intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(developer.linkedInURL)
                )
            }
            startActivity(intent)
        }

        btnGithub.setOnClickListener {
            var intent: Intent

            try {
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(developer.githubURL))
            } catch (e: Exception) {
                intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse(developer.githubURL)
                )
            }
            startActivity(intent)
        }

    }

}