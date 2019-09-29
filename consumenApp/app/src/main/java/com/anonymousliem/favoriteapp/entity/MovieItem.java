package com.anonymousliem.favoriteapp.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anonymousliem.favoriteapp.db.FavoriteContract;

import static android.provider.BaseColumns._ID;

public class MovieItem implements Parcelable {
    public static final Parcelable.Creator<MovieItem> CREATOR = new Parcelable.Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
    private String title;
    private String overview;
    private String backdrop;
    private String id;

    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        this.title = in.readString();
        this.overview = in.readString();
        this.backdrop = in.readString();
        this.id = in.readString();
    }

    public MovieItem(Cursor cursor) {
        this.id = FavoriteContract.getColumnString(cursor, _ID);
        this.title = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE);
        this.backdrop = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH);
        this.overview = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.backdrop);
        dest.writeString(this.id);
    }
}
