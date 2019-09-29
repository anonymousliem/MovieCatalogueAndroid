package com.anonymousliem.favoriteapp.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.anonymousliem.favoriteapp.R;
import com.anonymousliem.favoriteapp.adapter.MovieAdapter;
import com.anonymousliem.favoriteapp.db.FavoriteContract;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private final int LOAD_FAVORITE_ID = 110;
    private MovieAdapter movieAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view);
        movieAdapter = new MovieAdapter(this, null, true);
        listView.setAdapter(movieAdapter);
        getSupportLoaderManager().initLoader(LOAD_FAVORITE_ID, null, this);

    }

    protected void onResume() {
        super.onResume();
        getSupportLoaderManager().restartLoader(LOAD_FAVORITE_ID, null, this);
    }

    @NotNull
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, FavoriteContract.FavoriteEntry.CONTENT_URI, null, null, null, null);
    }

    public void onLoadFinished(@NotNull Loader<Cursor> loader, Cursor data) {
        movieAdapter.swapCursor(data);
    }


    public void onLoaderReset(@NotNull Loader<Cursor> loader) {
        movieAdapter.swapCursor(null);
    }

    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(LOAD_FAVORITE_ID);
    }
}
