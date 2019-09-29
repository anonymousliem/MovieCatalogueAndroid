package com.anonymousliem.retrofit2.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anonymousliem.retrofit2.Adapter.MovieAdapter;
import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.Retrofit.Api;
import com.anonymousliem.retrofit2.Retrofit.ApiInterface;
import com.anonymousliem.retrofit2.Util.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.anonymousliem.retrofit2.R.layout.fragment_movie;


public class MovieFragment extends Fragment {
    Context context;
    MovieAdapter movieAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    SearchView searchView;
    private ArrayList<Movie.Results> results = new ArrayList<>();

    public MovieFragment() {
    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(fragment_movie, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_movie_fragment);
        progressBar = rootView.findViewById(R.id.progressBar_fragment);
        searchView = rootView.findViewById(R.id.searchView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        movieAdapter = new MovieAdapter();

        recyclerView.setLayoutManager(linearLayoutManager);
        return rootView;

    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getMoviePopular();
        if (savedInstanceState != null) {
            results = savedInstanceState.getParcelableArrayList("movie_key");
            movieAdapter.refill(results);
            hideProgressBar();
        }


    }


    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("movie_key", new ArrayList<>(results));
    }

    private void getMoviePopular() {
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getPopular(Constant.KEY, "en-US", "1");

        call.enqueue(new Callback<Movie>() {
            public void onResponse(@Nullable Call<Movie> call, @Nullable Response<Movie> response) {

                assert response != null;
                Movie model = response.body();

                assert model != null;
                List<Movie.Results> results = model.getResults();

                movieAdapter = new MovieAdapter(getActivity(), results);
                recyclerView.setAdapter(movieAdapter);
                movieAdapter.notifyDataSetChanged();
                hideProgressBar();
                Log.i("RESPONSE: ", "" + results.size());

            }

            public void onFailure(@Nullable Call<Movie> call, @Nullable Throwable t) {
                Toast.makeText(getContext(), "failed call", Toast.LENGTH_SHORT).show();
            }
        });


    }


    void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}
