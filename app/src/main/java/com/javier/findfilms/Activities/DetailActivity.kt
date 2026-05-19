package com.javier.findfilms.Activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.findfilms.Data.Film
import com.javier.findfilms.Data.FilmsService
import com.javier.findfilms.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    var film: Film? = null

    val API_KEY = "8e074318"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getStringExtra(EXTRA_FILM_ID)

        if (id != null) {
            getFilm(id)
        }
    }

    fun getFilm(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            film = FilmsService.getInstance().getFilmById(id, API_KEY)

            CoroutineScope(Dispatchers.Main).launch {
                loadData()
            }
        }
    }

    fun loadData() {
        film?.let {
            binding.title.text = it.title
            binding.year.text = it.year
            binding.plot.text = it.plot
            binding.runtime.text = it.runtime
            binding.director.text = it.director
            binding.genre.text = it.genre
            binding.country.text = it.country


            Picasso.get().load(it.poster).into(binding.poster)
        }
    }

    companion object {
        const val EXTRA_FILM_ID = "imdbID"
    }
}