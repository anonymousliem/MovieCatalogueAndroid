package com.anonymousliem.retrofit2.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.anonymousliem.retrofit2.Activity.MainActivity;
import com.anonymousliem.retrofit2.db.FavoriteContract;
import com.anonymousliem.retrofit2.db.FavoriteHelper;

public class FavoriteProvider extends ContentProvider {
    private static final int FAVORITE = 1;
    private static final int FAVORITE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteProvider.FAVORITE);
        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(FavoriteContract.AUTHORITY, FavoriteContract.FavoriteEntry.TABLE_NAME + "/#", FavoriteProvider.FAVORITE_ID);
    }

    FavoriteHelper favoriteDbHelper;

    @Override
    public boolean onCreate() {
        favoriteDbHelper = new FavoriteHelper(getContext());
        favoriteDbHelper.open();
        return false;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case FavoriteProvider.FAVORITE:
                cursor = favoriteDbHelper.queryProvider();
                break;
            case FavoriteProvider.FAVORITE_ID:
                cursor = favoriteDbHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        favoriteDbHelper.open();

        long added;
        switch (sUriMatcher.match(uri)) {

            case FavoriteProvider.FAVORITE:
                added = favoriteDbHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }
        Log.v("insert uri : ", "" + uri);
        getContext().getContentResolver().notifyChange(FavoriteContract.FavoriteEntry.CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return Uri.parse(FavoriteContract.FavoriteEntry.CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        favoriteDbHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case FavoriteProvider.FAVORITE_ID:
                updated = favoriteDbHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(FavoriteContract.FavoriteEntry.CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Nullable
    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        favoriteDbHelper.open();
        int deleted;

        switch (sUriMatcher.match(uri)) {
            case FavoriteProvider.FAVORITE_ID:
                deleted = favoriteDbHelper.deleteProvider(uri.getLastPathSegment());
                Log.v("deleted uri : ", "" + uri);
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(FavoriteContract.FavoriteEntry.CONTENT_URI, new MainActivity.DataObserver(new Handler(), getContext()));

        return deleted;
    }

}