package com.uc.week4retrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.uc.week4retrofit.adapter.GenreAdapter
import com.uc.week4retrofit.adapter.NowPlayingAdapter
import com.uc.week4retrofit.adapter.ProductionCompanyAdapter
import com.uc.week4retrofit.adapter.SpokenLanguageAdapter
import com.uc.week4retrofit.databinding.ActivityMovieDetailBinding
import com.uc.week4retrofit.databinding.CardProductionCompanyBinding
import com.uc.week4retrofit.helper.Const
import com.uc.week4retrofit.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetail : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var genre_adapter: GenreAdapter
    private lateinit var company_adapter: ProductionCompanyAdapter
    private lateinit var spoken_language_adapter: SpokenLanguageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId = intent.getIntExtra("movie_id", 0)
        Toast.makeText(applicationContext, "Movie ID: ${movieId}", Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        viewModel.getMovieDetail(Const.API_KEY, movieId)
        viewModel.movieDetails.observe(this, Observer{
            response->

            if (response != null){
                binding.pbLoading.visibility = View.INVISIBLE
            }
            binding.tvTitleMovieDetail.apply {
                text = response.title
            }

//            binding.imgPosterMovieDetail.apply {
//                val img = "https://image.tmdb.org/t/p/w500/" + response.backdrop_path
//                Glide.with(this).load(img).into(binding.imgPosterMovieDetail)
//            }

            Glide.with(applicationContext).load(Const.IMG_URL+response.backdrop_path).into(binding.imgPosterMovieDetail)

            val layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)

                binding.rvGenreMovie.layoutManager = layoutManager
                genre_adapter = GenreAdapter(response.genres)
                binding.rvGenreMovie.adapter = genre_adapter

                binding.rvProductionCompany.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                company_adapter = ProductionCompanyAdapter(response.production_companies)
                binding.rvProductionCompany.adapter = company_adapter

                binding.rvSpokenLanguage.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
                spoken_language_adapter = SpokenLanguageAdapter(response.spoken_languages)
                binding.rvSpokenLanguage.adapter = spoken_language_adapter

//            Glide.with(applicationContext).load(Const.IMG_URL+response.production_companies).into(binding2.imgProductionCompany)

            binding.tvOverview.apply {
                text = response.overview
            }
        })
    }
}