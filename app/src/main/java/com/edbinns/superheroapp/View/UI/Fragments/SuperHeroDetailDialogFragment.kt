package com.edbinns.superheroapp.View.UI.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.media.audiofx.BassBoost
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.edbinns.superheroapp.Models.SuperHero.*
import com.edbinns.superheroapp.R
import com.edbinns.superheroapp.View.UI.AlertDialog.MessageFactory
import com.edbinns.superheroapp.ViewModel.FavoritesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_super_hero_detail_dialog.*


class SuperHeroDetailDialogFragment : DialogFragment() {

    private lateinit var viewModel : FavoritesViewModel


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

        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        val prefs = this.activity?.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE)
        viewModel.emailUser.value = prefs?.getString("email", "-")


        toolbarSuperhero.title = "SuperHeroe Details"
        toolbarSuperhero.navigationIcon = ContextCompat.getDrawable(view.context, R.drawable.ic_close)
        toolbarSuperhero.setTitleTextColor(Color.WHITE)
        toolbarSuperhero.setNavigationOnClickListener {
            dismiss()
        }

        val superhero  = arguments?.getSerializable("superhero") as SuperHero
        viewModel.superhero.value = superhero
        viewModel.getHeroFound()
        observeViewModel()
        listener(superhero)
        Picasso.get().load(superhero.image?.url).into(imageHero)
        tvNameDetailDialog.text = superhero.name
        setBiography(superhero.biography!!)
        setAppearance(superhero.appearance!!)
        setPowerstats(superhero.powerstats!!)
        setWork(superhero.work!!)
        setConnections(superhero.connections!!)
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

    @SuppressLint("RestrictedApi")
    fun observeViewModel(){
        viewModel.message.observe(viewLifecycleOwner, Observer<String> {
            showAlert(it,MessageFactory.TYPE_INFO)
        })

        viewModel.isFavoriteFound.observe(viewLifecycleOwner, Observer<Boolean>{ isFound ->
            if (isFound) {
                btnDelete.visibility = View.VISIBLE
                btnFavorite.visibility = View.INVISIBLE
            }
        })
        viewModel.emailUser.observe(viewLifecycleOwner, Observer<String> {
            println("correo en el detail de heroe  $it")
        })
    }
    fun showAlert(message : String?, type : String){
        val error = MessageFactory().getDialog(type, context!!, message)
        error.show()
    }

    @SuppressLint("CommitPrefEdits")
    private fun listener(superhero: SuperHero) {
        observeViewModel()

        btnFavorite.setOnClickListener {
            viewModel.setFavoriteHero(FavoritesSuperhero(viewModel.emailUser.value, superhero.id!!, superhero.image?.url, superhero.name, superhero.biography?.publisher))
            Handler().postDelayed({ observeViewModel() }, 3000)

        }
        btnDelete.setOnClickListener {
            viewModel.deleteFavoriteHero("${viewModel.emailUser.value}-${superhero.name}-${ superhero.id}")
            Handler().postDelayed({ observeViewModel() }, 3000)
        }

    }

override fun onStart() {
    super.onStart()
    dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
}
}