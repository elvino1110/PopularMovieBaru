package com.example.popularmovie.main

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
/*import androidx.activity.viewModels*/
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popularmovie.R
import com.example.popularmovie.core.data.Resource
import com.example.popularmovie.core.ui.MovieAdapter
import com.example.popularmovie.databinding.ActivityMainBinding
import com.example.popularmovie.detail.DetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlinx.coroutines.flow.observeOn

/*@AndroidEntryPoint*/
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()
    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()

        val movieAdapter = MovieAdapter()
        movieAdapter.onItemClick = { selectedData ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DATA, selectedData)
            startActivity(intent)
        }
        binding.fabFavorite.setOnClickListener {
            val uri = Uri.parse("popularmovie://favorites")
            startActivity(Intent(Intent.ACTION_VIEW, uri))
        }


        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchMovie(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        mainViewModel.movie.observe(this) { movie ->
            if (movie != null) {
                when (movie) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        movieAdapter.setData(movie.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.root.visibility = View.VISIBLE
                        binding.viewError.tvError.text = movie.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        }
        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun searchMovie(query: String) {
        mainViewModel.searchMovie(query).observe(this) { movie ->
            when (movie) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.viewError.root.visibility = View.GONE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewError.root.visibility = View.GONE
                    movieAdapter.setData(movie.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.viewError.root.visibility = View.VISIBLE
                    binding.viewError.tvError.text = movie.message ?: getString(R.string.something_wrong)
                }
            }
        }
        with(binding.rvMovie) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = movieAdapter
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
        //supportActionBar?.title = "Popular Movie"
    }
}