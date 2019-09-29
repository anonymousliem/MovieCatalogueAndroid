package com.anonymousliem.retrofit2.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anonymousliem.retrofit2.Adapter.MovieAdapter;
import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.db.FavoriteDbHelper;

import java.util.ArrayList;

import static com.anonymousliem.retrofit2.R.layout.fragment_favorite;


public class FavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    Context context;
    private FavoriteDbHelper databaseHelper;
    private MovieAdapter adapter;
    private ArrayList<Movie.Results> results = new ArrayList<>();

    public FavoriteFragment() {
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(fragment_favorite, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_favorite_fragment);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        databaseHelper = new FavoriteDbHelper(getActivity());
        adapter = new MovieAdapter(getActivity(), results);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setLayoutManager(linearLayoutManager);
        getAllFavorite();

        return rootView;
    }

    @SuppressLint("StaticFieldLeak")
    private void getAllFavorite() {
        new AsyncTask<Void, Void, Void>() {

            protected Void doInBackground(Void... params) {
                results.clear();
                results.addAll(databaseHelper.getAllFavorite());
                Log.i("RESPONSE: ", "" + results.size());
                return null;
            }

            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }


}
