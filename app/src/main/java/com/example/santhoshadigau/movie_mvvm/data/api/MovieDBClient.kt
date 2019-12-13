package com.example.santhoshadigau.movie_mvvm.data.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import com.example.santhoshadigau.movie_mvvm.data.api.MovieDBInterface as MovieDBInterface1

const val API_KEY = "3ddf28bc281b571695baab63100abfd7"
const val BASE_URL = "https://api.themoviedb.org/3/"

const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 25


//https://api.themoviedb.org/3/movie/popular?api_key=3ddf28bc281b571695baab63100abfd7&language=en-US&page=1
//https://api.themoviedb.org/3/movie/920?api_key=3ddf28bc281b571695baab63100abfd7&language=en-US
////https://image.tmdb.prg/t/p/w342/ajytr24yva5rwfgar62wbjbsjb.jpg

object MovieDBClient {

    fun getClient(): MovieDBInterface1 {

        val requestInterceptor = Interceptor { chain ->

            val url = chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()

            val request: Request = chain.request()
                .newBuilder()
                .url(url)
                .build()
            return@Interceptor chain.proceed(request)

        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieDBInterface1::class.java)
    }
}