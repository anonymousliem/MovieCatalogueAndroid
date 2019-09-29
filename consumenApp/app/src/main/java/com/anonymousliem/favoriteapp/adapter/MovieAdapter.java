package com.anonymousliem.favoriteapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.anonymousliem.favoriteapp.R;
import com.anonymousliem.favoriteapp.db.FavoriteContract;
import com.squareup.picasso.Picasso;


public class MovieAdapter extends CursorAdapter {
    public MovieAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_movie, parent, false);
    }


    public Cursor getCursor() {
        return super.getCursor();
    }


    public void bindView(View view, final Context context, final Cursor cursor) {
        if (cursor != null) {
            TextView textViewTitle;
            TextView textViewOverview;
            ImageView imgPoster;
            TextView textViewName;
            textViewName = view.findViewById(R.id.txtTvTitle);
            textViewTitle = view.findViewById(R.id.txtTitle);
            imgPoster = view.findViewById(R.id.thumb);
            textViewOverview = view.findViewById(R.id.txtDesc);
            textViewName.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE));
            textViewTitle.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE));
            textViewTitle.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE));
            textViewOverview.setText(FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS));
            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w185/" + FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH))
                    .placeholder(R.drawable.ic_image)
                    .error(R.drawable.ic_image)
                    .into(imgPoster);


        }
    }
}
