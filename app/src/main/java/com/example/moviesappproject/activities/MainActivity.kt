package com.example.moviesappproject.activities

import MovieSliderAdapter
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.android.pagingwithflow.adapter.PopularMovieAdapter
import com.example.moviesappproject.R
import com.example.moviesappproject.adapter.DiscoverMovieCardAdapter
import com.example.moviesappproject.adapter.FevoritesMovieAdapter
import com.example.moviesappproject.adapter.GenreAdapter
import com.example.moviesappproject.adapter.SearchMovieAdapter
import com.example.moviesappproject.databinding.ActivityMainBinding
import com.example.moviesappproject.databinding.FragmentSearchBottomSheetBinding
import com.example.moviesappproject.repo.DiscoverMoveViewModel
import com.example.moviesappproject.repo.FavoriteRepository
import com.example.moviesappproject.repo.MovieViewModel
import com.example.moviesappproject.utlis.ConnectionLiveData
import com.example.moviesappproject.utlis.ProgressBarHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: DiscoverMoveViewModel by viewModels()
    private val movieViewModel: MovieViewModel by viewModels()
    var progressBar: ProgressBarHandler? = null
    private lateinit var cld: ConnectionLiveData
    lateinit var movieSliderAdapter: MovieSliderAdapter
    private val favoriteRepository by lazy { FavoriteRepository(this) }
    lateinit var popularMovieAdapter: PopularMovieAdapter
    lateinit var fevoriteMovieAdapter: FevoritesMovieAdapter
    lateinit var genreAdapter: GenreAdapter

    @Inject
    lateinit var discoverMovieCardAdapter: DiscoverMovieCardAdapter

    private val handler = Handler(Looper.getMainLooper())
    private val slideRunnable = Runnable {
        binding.imageSliderMovieFragment.currentItem =
            (binding.imageSliderMovieFragment.currentItem + 1) % movieSliderAdapter.itemCount
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
        initProgressInternet()
        initAPiCall()
        setupSearchBar()
    }

    private fun initAPiCall() {
        cld.observe(this) { isConnected ->
            if (isConnected) {
                movieViewModel.getUpcomingMovieResult()
                movieViewModel.getPopularMovieResult()
                movieViewModel.getTopRatedMovieResult()
                movieViewModel.getGenreMovieResult()
                observeViewModel()
                binding.MovieSeeAllMovie.setOnClickListener {
                    val intent = Intent(this, DiscoverAllMovie::class.java)
                    startActivity(intent)
                }
                binding.layout1.visibility = View.VISIBLE
                binding.layout2.visibility = View.GONE
            } else {
                binding.layout1.visibility = View.GONE
                binding.layout2.visibility = View.VISIBLE
            }
        }
    }

    private fun initProgressInternet() {
        cld = ConnectionLiveData(application)
        progressBar = ProgressBarHandler(this)
        progressBar!!.show()
    }

    private fun observeViewModel() {
        movieViewModel.upcomingLiveData.observe(this@MainActivity, Observer { response ->
            movieSliderAdapter = MovieSliderAdapter(this@MainActivity, response)
            binding.apply {
                progressBar!!.hide()
                imageSliderMovieFragment.adapter = movieSliderAdapter
                imageSliderMovieFragment.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                imageSliderMovieFragment.registerOnPageChangeCallback(object :
                    ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        handler.removeCallbacks(slideRunnable)
                        handler.postDelayed(slideRunnable, 3000) // Change every 3 seconds
                    }
                })
            }
        })

        movieViewModel.popularLiveData.observe(this@MainActivity, Observer { response ->
            binding.apply {
                popularMovieAdapter = PopularMovieAdapter(this@MainActivity, response)
                recyclerviewPopular.apply {
                    adapter = popularMovieAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity, LinearLayoutManager.HORIZONTAL, false
                    )
                }
            }
        })

        movieViewModel.topRatedLiveData.observe(this@MainActivity, Observer { response ->
            movieSliderAdapter = MovieSliderAdapter(this@MainActivity, response)
            binding.apply {
                popularMovieAdapter = PopularMovieAdapter(this@MainActivity, response)
                recyclerviewTopRated.apply {
                    adapter = popularMovieAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )

                }
            }
        })



        movieViewModel.genreLiveData.observe(this@MainActivity, Observer { response ->
            genreAdapter = GenreAdapter(this@MainActivity, response)
            binding.apply {
                recyclerviewGenre.apply {
                    adapter = genreAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity, LinearLayoutManager.HORIZONTAL, false
                    )
                }
            }
        })

        lifecycleScope.launch {
            val favorites = favoriteRepository.getFavorites()
            if(favorites.isEmpty()){
                binding.tvFevorites.visibility = View.GONE
                binding.recyclerviewFevorite.visibility = View.GONE
            } else {
                binding.tvFevorites.visibility = View.VISIBLE
                binding.recyclerviewFevorite.visibility = View.VISIBLE
            }
            fevoriteMovieAdapter = FevoritesMovieAdapter(this@MainActivity, favorites)
            binding.apply {
                recyclerviewFevorite.apply {
                    adapter = fevoriteMovieAdapter
                    layoutManager = LinearLayoutManager(
                        this@MainActivity, LinearLayoutManager.HORIZONTAL, false
                    )
                }
            }
        }

        lifecycleScope.launch {
            mainViewModel.discoverMovieResult.collectLatest { response ->
                binding.apply {
                    recyclerviewDiscover.apply {
                        adapter = discoverMovieCardAdapter
                        layoutManager = LinearLayoutManager(
                            this@MainActivity, LinearLayoutManager.HORIZONTAL, false
                        )
                    }
                }
                discoverMovieCardAdapter.submitData(response)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(slideRunnable)
    }

    private fun setupSearchBar(){
        binding.searchBar.setOnClickListener {
            showSearchBottomSheet()
        }
    }


    private fun showSearchBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this, R.style.FullScreenBottomSheetDialog)
        val binding = FragmentSearchBottomSheetBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(binding.root)

        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetBehavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels

        val searchAdapter = SearchMovieAdapter(emptyList()) { movie ->
            val intent = Intent(this, MovieDetails::class.java).apply {
                putExtra("MOVIE_ID", movie.id)
            }
            startActivity(intent)

            bottomSheetDialog.dismiss() // Close dialog after selecting a movie
        }

        binding.apply {
            recyclerViewSearchResults.apply {
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(
                    this@MainActivity, LinearLayoutManager.VERTICAL, false
                )
            }
        }

        movieViewModel.searchLiveData.observe(this) { movies ->
            searchAdapter.updateMovies(movies)
        }

        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                if (query.isNotEmpty()) {
                    movieViewModel.searchMovies(query)
                } else {
                    searchAdapter.updateMovies(emptyList())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        bottomSheetDialog.setOnDismissListener {
            searchAdapter.updateMovies(emptyList())
            movieViewModel.clearSearchResults()
        }

        bottomSheetDialog.show()
    }



}