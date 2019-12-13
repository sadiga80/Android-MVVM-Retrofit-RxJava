package com.example.santhoshadigau.movie_mvvm.ui.popular_movie

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.santhoshadigau.movie_mvvm.R
import com.example.santhoshadigau.movie_mvvm.data.api.MovieDBClient
import com.example.santhoshadigau.movie_mvvm.data.api.MovieDBInterface
import com.example.santhoshadigau.movie_mvvm.data.repository.NetworkState
import kotlinx.android.synthetic.main.activity_main.*

@Suppress("UNCHECKED_CAST")
class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    lateinit var moviePageListRepository: MoviePageListRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiService: MovieDBInterface = MovieDBClient.getClient()
        moviePageListRepository = MoviePageListRepository(apiService)

        viewModel = getViewModel()

        val moviePageListAdapter = PopularMoviePageListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = moviePageListAdapter.getItemViewType(position)
                if (viewType == moviePageListAdapter.MOVIE_VIEW_TYPE) return 1    // Movie_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                              // NETWORK_VIEW_TYPE will occupy all 3 span
            }
        }

        rv_movie_list.layoutManager = gridLayoutManager
        rv_movie_list.setHasFixedSize(true)
        rv_movie_list.adapter = moviePageListAdapter

        viewModel.moviePagedList.observe(this, Observer {
            moviePageListAdapter.submitList(it)
        })

        viewModel.networkState.observe(this, Observer { //observe movie page list in ViewModel
            progress_bar_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            txt_error_popular.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                moviePageListAdapter.setNetworkState(it)
            }
        })

    }

    private fun getViewModel(): MainActivityViewModel {
        return ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return MainActivityViewModel(moviePageListRepository) as T
            }
        })[MainActivityViewModel::class.java]
    }
}
