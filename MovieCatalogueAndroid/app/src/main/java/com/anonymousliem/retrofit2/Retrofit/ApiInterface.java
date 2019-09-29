package com.anonymousliem.retrofit2.Retrofit;

import com.anonymousliem.retrofit2.Model.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("movie/popular")
    Call<Movie> getPopular(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page);

    @GET("tv/popular")
    Call<Movie> gettvPopular(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page);

    @GET("search/movie")
    Call<Movie> getSearchMovie(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") String page
    );

    @GET("search/tv")
    Call<Movie> getSearchTv(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("query") String query,
            @Query("page") String page
    );

    @GET("discover/movie")
    Call<Movie> getDiscoverMovie(
            @Query("api_key") String key,
            @Query("language") String language,
            @Query("page") String page,
            @Query("primary_release_date.gte") String gte,
            @Query("primary_release_date.lte") String lte

    );


}