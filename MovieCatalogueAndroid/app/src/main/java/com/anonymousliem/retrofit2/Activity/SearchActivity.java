package com.anonymousliem.retrofit2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.anonymousliem.retrofit2.Adapter.MovieAdapter;
import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.Retrofit.Api;
import com.anonymousliem.retrofit2.Retrofit.ApiInterface;
import com.anonymousliem.retrofit2.Util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.recycler_search)
    RecyclerView recyclerView;

    MovieAdapter movieAdapter;

    List<Movie.Results> movieList;
    Movie movieResult;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        movieList = new ArrayList<>();
        movieResult = new Movie();

        recyclerView = findViewById(R.id.recycler_search);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        movieAdapter = new MovieAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);

        String movie = getIntent().getStringExtra("query");
        getSearchMovies(movie);

        String tv = getIntent().getStringExtra("query");
        getSearchTv(tv);

    }

    private void getSearchTv(final String query) {
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getSearchTv(Constant.KEY, "en-US", query, "1");

        call.enqueue(new Callback<Movie>() {
            public void onResponse(@Nullable Call<Movie> call, @Nullable Response<Movie> response) {

                assert response != null;
                Movie model = response.body();

                assert model != null;
                List<Movie.Results> results = model.getResults();


                movieAdapter = new MovieAdapter(SearchActivity.this, results);
                recyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
                Log.i("RESPONSE SEARCH: ", "" + results.size());


            }

            public void onFailure(@Nullable Call<Movie> call, @Nullable Throwable t) {
                Toast.makeText(SearchActivity.this, "failed call", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getSearchMovies(final String query) {
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getSearchMovie(Constant.KEY, "en-US", query, "1");

        call.enqueue(new Callback<Movie>() {
            public void onResponse(@Nullable Call<Movie> call, @Nullable Response<Movie> response) {

                assert response != null;
                Movie model = response.body();

                assert model != null;
                List<Movie.Results> results = model.getResults();

                movieAdapter = new MovieAdapter(SearchActivity.this, results);
                recyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
                Log.i("RESPONSE SEARCH: ", "" + results.size());


            }

            public void onFailure(@Nullable Call<Movie> call, @Nullable Throwable t) {
                Toast.makeText(SearchActivity.this, "failed call", Toast.LENGTH_SHORT).show();
            }
        });
    }

}






