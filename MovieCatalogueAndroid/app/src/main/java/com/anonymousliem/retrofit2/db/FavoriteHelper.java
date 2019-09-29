package com.anonymousliem.retrofit2.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class FavoriteHelper {
    private static final String DATABASE_TABLE = FavoriteContract.FavoriteEntry.TABLE_NAME;
    private Context context;

    private SQLiteDatabase sqliteDatabase;

    public FavoriteHelper(Context context) {
        this.context = context;
    }


    public Cursor queryByIdProvider(String id) {
        return sqliteDatabase.query(DATABASE_TABLE, null
                , FavoriteContract.FavoriteEntry._ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return sqliteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                FavoriteContract.FavoriteEntry._ID
        );
    }

    public long insertProvider(ContentValues values) {
        return sqliteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return sqliteDatabase.update(DATABASE_TABLE, values,
                FavoriteContract.FavoriteEntry._ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return sqliteDatabase.delete(DATABASE_TABLE,
                FavoriteContract.FavoriteEntry._ID + " = ?", new String[]{id});
    }

    public void open() throws SQLException {
        FavoriteDbHelper databaseHelper = new FavoriteDbHelper(context);
        sqliteDatabase = databaseHelper.getWritableDatabase();
    }

}
