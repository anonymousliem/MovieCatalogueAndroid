package com.anonymousliem.retrofit2.Model;

import android.database.Cursor;

import com.anonymousliem.retrofit2.db.FavoriteContract;
import com.google.gson.annotations.SerializedName;

import static android.provider.BaseColumns._ID;

public class ResultsItem {
    @SerializedName("overview")
    private String overview;
    @SerializedName("title")
    private String title;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("id")
    private String id;

    public ResultsItem() {
    }

    public ResultsItem(Cursor cursor) {
        this.id = FavoriteContract.getColumnString(cursor, _ID);
        this.title = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE);
        this.posterPath = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH);
        this.overview = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS);
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "ResultsItem{" +
                        "overview = '" + overview + '\'' +
                        ",title = '" + title + '\'' +
                        ",poster_path = '" + posterPath + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}

