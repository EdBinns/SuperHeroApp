package com.edbinns.superheroapp.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edbinns.superheroapp.Models.Comics.Comic
import com.edbinns.superheroapp.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ComicsAdapter(val comicListener: ItemListener<Comic>) : RecyclerView.Adapter<ComicsAdapter.ViewHolder>() {

    var listComics = ArrayList<Comic>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comics, parent, false))


    override fun getItemCount(): Int = listComics.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val comic = listComics[position]

        holder.tvComicTitle.text = comic.title
        holder.tvPublisherComic.text = comic.publisher
        holder.tvDateComic.text = comic.release_date

        holder.itemView.setOnClickListener {
            comicListener.onItemClicked(comic, position)
        }
    }

    fun updateData(data: List<Comic>) {
        listComics.clear()
        listComics.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvComicTitle = itemView.findViewById<TextView>(R.id.tvTitleComic)
        val tvPublisherComic = itemView.findViewById<TextView>(R.id.tvPublisherComic)
        val tvDateComic = itemView.findViewById<TextView>(R.id.tvDateComic)
    }
}