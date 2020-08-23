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
import com.edbinns.superheroapp.Models.SuperHero.*
import com.edbinns.superheroapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_comics_detail_dialog.*
import kotlinx.android.synthetic.main.fragment_super_hero.*
import kotlinx.android.synthetic.main.fragment_super_hero_detail_dialog.*


class SuperHeroDetailDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_super_hero_detail_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarSuperhero.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        toolbarSuperhero.setTitleTextColor(Color.WHITE)
        toolbarSuperhero.setNavigationOnClickListener {
            dismiss()
        }

        val superhero  = arguments?.getSerializable("superhero") as SuperHero

        Picasso.get().load(superhero.image.url).into(imageHero)
        tvNameDetailDialog.text = superhero.name
        setBiography(superhero.biography)
        setAppearance(superhero.appearance)
        setPowerstats(superhero.powerstats)
        setWork(superhero.work)
        setConnections(superhero.connections)
    }

    private fun setBiography(biography: Biography){
        tvFullNameComplete.text = biography.fullName
        tvAlterEgosComplete.text = biography.alterEgos
        var aliases = ""
        biography.aliases?.forEach{
              aliases = "$it,"
        }
        tvAliasesComplete.text = aliases
        tvPlaceOfBirthComplete.text = biography.placeOfbirth
        tvFirstAppearanceComplete.text = biography.firstAppearance
        tvpublisherComplete.text = biography.publisher
        tvAlignmentComplete.text = biography.alignment
    }

    private fun setAppearance(appearance: Appearance){
        tvGenderComplete.text = appearance.gender
        tvRaceComplete.text = appearance.race
        tvHeightComplete.text = appearance.height?.get(1)
        tvWeightComplete.text = appearance.weight?.get(1)
        tvEyeColorComplete.text = appearance.eyeColor
        tvHairColorComplete.text = appearance.hairColor
    }

    private fun setPowerstats(powerstats: Powerstats){
        tvIntelligenceComplete.text = powerstats.intelligence
        tvStrengthComplete.text = powerstats.strength
        tvSpeedComplete.text = powerstats.speed
        tvDurabilityComplete.text = powerstats.durability
        tvPowerComplete.text = powerstats.power
        tvCombatComplete.text = powerstats.combat
    }

    private fun setWork(work: Work){
        tvOccupationComplete.text = work.occupation
        tvBaseComplete.text = work.base
    }

    private fun setConnections(connections: Connections){
        tvRelativesComplete.text = connections.relatives
        tvGroupAffiliationComplete.text = connections.groupAffiliation
    }

    private fun listener(){

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }
}