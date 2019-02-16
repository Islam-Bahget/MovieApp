package com.example.islam.movies.kotlin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.bumptech.glide.Glide
import com.example.islam.movies.Const
import com.example.islam.movies.R
import com.example.islam.movies.model.Movie
import kotlinx.android.synthetic.main.frag_movie_item.view.*

class KotlinAdapter(val context: Context, val movies: ArrayList<Movie>,
                    val listener: OnKotlinClickListener) : RecyclerView.Adapter<KotlinAdapter.KotlinHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KotlinHolder {

        return KotlinHolder(LayoutInflater.from(context).inflate(R.layout.frag_movie_item, parent, false)
                , listener)
    }

    override fun getItemCount(): Int {
        return movies.size

    }

    override fun onBindViewHolder(holder: KotlinHolder, position: Int) {

        val lastPosition: Int = -1
        val movie: Movie = movies[position]
        holder.movieTitle.text = movie.title
        Glide.with(context).load(Const.IMAGE_URL + movie.posterPath).into(holder.moviePoster)
        holder.movieRate.text = movie.voteAverage.toString()

        val animation: Animation = AnimationUtils.loadAnimation(context,
                R.anim.tr)
    }

    class KotlinHolder(val view: View, val listener: OnKotlinClickListener) : RecyclerView.ViewHolder(view) {


        val moviePoster = view.movie_poster
        val movieTitle = view.movie_title
        val movieRate = view.movie_rate


    }
}