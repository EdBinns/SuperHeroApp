package com.edbinns.superheroapp.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.Models.SuperHero.FavoritesSuperhero
import com.edbinns.superheroapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_super_hero_detail_dialog.*

class FavoriteHeroesAdapter(val favoritesListener: ItemListener<FavoritesSuperhero>): RecyclerView.Adapter<FavoriteHeroesAdapter.ViewHolder>() {

    var listFavorites = ArrayList<FavoritesSuperhero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_favorite_hero, parent, false))


    override fun getItemCount(): Int= listFavorites.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val favorite = listFavorites[position]
        holder.tvItemHeroName.text = favorite.nombre
        holder.tvItemPublisherFavorite.text = favorite.publicador

        Picasso.get().load(favorite.imageURL).into(holder.imageFavorite)

        holder.itemView.setOnClickListener {
            favoritesListener.onItemClicked(favorite, position)
        }

    }

    fun updateData(data: List<FavoritesSuperhero>) {
        listFavorites.clear()
        listFavorites.addAll(data)
        notifyDataSetChanged()
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItemHeroName= itemView.findViewById<TextView>(R.id.tvItemHeroName)
        val tvItemPublisherFavorite= itemView.findViewById<TextView>(R.id.tvItemPublisherFavorite)
        val imageFavorite = itemView.findViewById<ImageView>(R.id.itemHeroImageFavorite)
    }

}