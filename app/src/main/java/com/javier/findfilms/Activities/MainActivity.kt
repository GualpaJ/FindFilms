package com.javier.findfilms.Activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.javier.findfilms.Data.Film
import com.javier.findfilms.Data.FilmsResponse
import com.javier.findfilms.Data.FilmsService
import com.javier.findfilms.R
import com.javier.findfilms.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    var filmsList: List<Film> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        search("The Social Network") // titulo sacado mediante el id
        searchById("tt1285016") // Ejemplo de la documetnacion
    }

    fun search(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = FilmsService.getInstance().searchFilms(query, "8e074318")
            filmsList = response.films
            Log.i("API_OMDB", filmsList.toString())
        }
    }

    fun searchById(imdbId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val film = FilmsService.getInstance().getFilmById(imdbId, "8e074318")
            Log.i("API_OMDB", film.toString())
        }
    }


}