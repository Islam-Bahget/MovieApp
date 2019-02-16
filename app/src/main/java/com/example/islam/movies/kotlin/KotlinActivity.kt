package com.example.islam.movies.kotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.example.islam.movies.Const
import com.example.islam.movies.R
import com.example.islam.movies.fragments.ReAdapter
import com.example.islam.movies.model.Movie
import java.util.*

class KotlinActivity : AppCompatActivity(), ReAdapter.OnMovieClickListener {
    val movies: ArrayList<Movie> = ArrayList()
    override fun onMovieClicked(position: Int) {

        Toast.makeText(this, movies[position]
                .title, Toast.LENGTH_LONG).show()
    }


    fun testObServable() {


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        val recyclerview: RecyclerView = findViewById(R.id.kotlin_recycler)
        var movie1: Movie = Movie()
        var movie: Movie = Movie()
        movie.title = "Islam"
        movie.voteAverage = 8.7
        movie1.title = "Home"
        movie1.voteAverage = 7.8


        movies.add(movie)
        movies.add(movie1)

        recyclerview.layoutManager = GridLayoutManager(this, 2)

        val adapter: ReAdapter = ReAdapter(this, Const.MOVIES)

        adapter.setOnMovieClicked(this)


        recyclerview.adapter = adapter


    }

}
