package com.javier.findfilms.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.findfilms.Data.Film
import com.javier.findfilms.Data.FilmsService
import com.javier.findfilms.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var filmsList: List<Film> = emptyList()

    lateinit var adapter: FilmAdapter

    val API_KEY = "8e074318"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Para probar el funcionamiento de la API
        search("The Social Network") // titulo sacado mediante el id
        //searchById("tt1285016") // Ejemplo de la documetnacion

        adapter = FilmAdapter(filmsList) { position ->
            val film = filmsList[position]
            //Toast.makeText(this, film.title, Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_FILM_ID, film.id)
            startActivity(intent)
        }

        binding.recyclerViewFilms.adapter = adapter
        binding.recyclerViewFilms.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        // Buscar por el texto introducido en editext
        binding.searchText.setOnKeyListener { _, keyCode, event ->
            if (event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                val query = binding.searchText.text.toString()
                if (query.isNotEmpty()) {
                    search(query)
                }
                return@setOnKeyListener true
            }
            false
        }
    }

    fun search(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = FilmsService.getInstance().searchFilms(query, API_KEY)
            filmsList = response.films

            Log.i("API_OMDB", filmsList.toString())

            CoroutineScope(Dispatchers.Main).launch {
                adapter.updateData(filmsList)
            }
        }
    }

    /*fun searchById(imdbId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val film = FilmsService.getInstance().getFilmById(imdbId, API_KEY)
            Log.i("API_OMDB", film.toString())
        }
    }*/
}