package com.example.santhoshadigau.movie_mvvm.ui.movie_details

import androidx.lifecycle.LiveData
import com.example.santhoshadigau.movie_mvvm.data.api.MovieDBInterface
import com.example.santhoshadigau.movie_mvvm.data.model.MovieDetails
import com.example.santhoshadigau.movie_mvvm.data.repository.MovieDetialsNetworkDataSource
import com.example.santhoshadigau.movie_mvvm.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService: MovieDBInterface) {

    lateinit var movieDetialsNetworkDataSource: MovieDetialsNetworkDataSource

    fun fetchSingleMovieDetails(
        compositeDisposable: CompositeDisposable,
        movieId: Int
    ): LiveData<MovieDetails> {

        movieDetialsNetworkDataSource =
            MovieDetialsNetworkDataSource(apiService, compositeDisposable);
        movieDetialsNetworkDataSource.fetchMovieDetails(movieId);

        return movieDetialsNetworkDataSource.downloadedMovieResponse
    }

    fun getMovieDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetialsNetworkDataSource.networkState
    }
}