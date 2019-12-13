package com.example.santhoshadigau.movie_mvvm.data.api

import com.example.santhoshadigau.movie_mvvm.data.model.MovieDetails
import com.example.santhoshadigau.movie_mvvm.data.model.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDBInterface {
    //https://api.themoviedb.org/3/movie/popular?api_key=3ddf28bc281b571695baab63100abfd7&language=en-US&page=1
    //https://api.themoviedb.org/3/movie/920?api_key=3ddf28bc281b571695baab63100abfd7&language=en-US
    ////https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<MovieDetails>
}