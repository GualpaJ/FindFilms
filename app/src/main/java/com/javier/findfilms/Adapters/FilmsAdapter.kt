package com.javier.findfilms.Activities

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.javier.findfilms.Data.Film
import com.javier.findfilms.databinding.ItemFilmBinding
import com.squareup.picasso.Picasso

class FilmAdapter(var items: List<Film>, val onClick: (position: Int) -> Unit) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(layoutInflater, parent, false)
        return FilmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        val film = items[position]
        holder.render(film)
        holder.binding.root.setOnClickListener {
            onClick(position)
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateData(dataSet: List<Film>) {
        items = dataSet
        notifyDataSetChanged()
    }
}

class FilmViewHolder(val binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(film: Film) {
        binding.title.text = film.title
        binding.year.text = film.year
        Picasso.get().load(film.poster).into(binding.poster)
    }
}