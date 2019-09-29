package com.anonymousliem.retrofit2.Model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.anonymousliem.retrofit2.db.FavoriteContract;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import static android.provider.BaseColumns._ID;

public class Movie {

    @SerializedName("results")
    private List<Results> results;
    @SerializedName("total_results")
    private Long mTotalResults;

    public Movie() {
    }

    public Long getmTotalResults() {
        return mTotalResults;
    }

    public void setmTotalResults(Long mTotalResults) {
        this.mTotalResults = mTotalResults;
    }

    public List<Results> getResults() {
        return results;
    }

    public static class Results implements Parcelable {
        public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
            @Override
            public Results createFromParcel(Parcel source) {
                return new Results(source);
            }

            @Override
            public Results[] newArray(int size) {
                return new Results[size];
            }
        };
        @SerializedName("backdrop_path")
        private String backdrop_path;
        @SerializedName("title")
        private String title;
        @SerializedName("name")
        private String name;
        @SerializedName("overview")
        private String overview;
        @SerializedName("release_date")
        private String release_date;
        @SerializedName("id")
        private String id;

        public Results(String id, String title, String backdrop_path, String overview) {
            this.id = id;
            this.title = title;
            this.backdrop_path = backdrop_path;
            this.overview = overview;
        }

        public Results(Cursor cursor) {
            this.id = FavoriteContract.getColumnString(cursor, _ID);
            this.title = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_TITLE);
            this.backdrop_path = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH);
            this.overview = FavoriteContract.getColumnString(cursor, FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS);
        }

        public Results() {
        }

        protected Results(Parcel in) {
            this.backdrop_path = in.readString();
            this.title = in.readString();
            this.name = in.readString();
            this.overview = in.readString();
            this.release_date = in.readString();
            this.id = in.readString();
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.backdrop_path);
            dest.writeString(this.title);
            dest.writeString(this.name);
            dest.writeString(this.overview);
            dest.writeString(this.release_date);
            dest.writeString(this.id);
        }
    }
}