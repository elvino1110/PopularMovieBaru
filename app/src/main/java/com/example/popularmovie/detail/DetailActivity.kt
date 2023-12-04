package com.example.popularmovie.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.popularmovie.R
import com.example.popularmovie.core.domain.model.Movie
import com.example.popularmovie.core.utils.withDateFormat
import com.example.popularmovie.databinding.ActivityDetailBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

/*@AndroidEntryPoint*/
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DATA = "extra_data"
    }

    private lateinit var binding: ActivityDetailBinding



    private val detailViewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        binding.back.setOnClickListener {
            onBackPressed()
        }

        /*val factory = ViewModelFactory.getInstance(this)
        detailViewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]*/

        val detailMovie = intent.getParcelableExtra<Movie>(EXTRA_DATA)
        showDetailMovie(detailMovie)

    }

    private fun showDetailMovie(detailMovie: Movie?) {
        detailMovie?.let {
            binding.tvTitle.text = detailMovie.title
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500${detailMovie.posterPath}")
                .placeholder(R.drawable.baseline_image_24)
                .into(binding.ivDetailImage)
            binding.tvOverview.text = detailMovie.overview
            binding.tvPopularity.text = detailMovie.popularity.toString()
            binding.tvVote.text = detailMovie.voteCount.toString()
            binding.tvDate.text = detailMovie.releaseDate.withDateFormat()

            var isFavorite = detailMovie.isFavorite
            setFavorite(isFavorite)
            binding.ivSave.setOnClickListener {
                isFavorite = !isFavorite
                detailViewModel.setFavoritePopularMovie(detailMovie, isFavorite)
                setFavorite(isFavorite)
                setSnackbar(isFavorite)
            }
        }
    }

    private fun setSnackbar(favorite: Boolean) {
        if (favorite) {
            val mySnackbar = Snackbar.make(binding.root, R.string.success_save_movie, Snackbar.LENGTH_SHORT)
            mySnackbar.setAction(R.string.see) {
                val uri = Uri.parse("popularmovie://favorites")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            mySnackbar.show()
        } else {
            Toast.makeText(this, R.string.success_delate_movie, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFavorite(favorite: Boolean) {
        if (favorite) {
            binding.ivSave.setImageResource(R.drawable.baseline_bookmark_24)
        } else {
            binding.ivSave.setImageResource(R.drawable.baseline_bookmark_border_24)
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

}