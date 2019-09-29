package com.anonymousliem.retrofit2.Activity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.Util.Constant;
import com.anonymousliem.retrofit2.db.FavoriteContract;
import com.anonymousliem.retrofit2.db.FavoriteDbHelper;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();
    private final AppCompatActivity activity = DetailActivity.this;
    ImageView detailImage;
    Button btnDelete, btnDetails;
    TextView detailDescription, detailTitle, detailTvTitle;
    ProgressBar progressBar;
    Bundle bundle;
    private Movie.Results results;
    private int position;
    private FavoriteDbHelper databaseHelper;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailImage = findViewById(R.id.detail_image);
        detailDescription = findViewById(R.id.detail_description);
        detailTitle = findViewById(R.id.detail_title);
        detailTvTitle = findViewById(R.id.detail_title_tv);
        progressBar = findViewById(R.id.progressBar_detail);
        btnDelete = findViewById(R.id.buttonDelete);
        btnDetails = findViewById(R.id.btnDetails);

        MaterialFavoriteButton materialFavoriteButtonNice =
                findViewById(R.id.favorite_button);

        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            SharedPreferences.Editor editor = getSharedPreferences("favorite", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Added", true);
                            editor.apply();
                            saveFavorite();
                            Snackbar.make(buttonView, "Added to Favorite",
                                    Snackbar.LENGTH_SHORT).show();


                        } else {
                            btnDelete.setVisibility(View.VISIBLE);
                            SharedPreferences.Editor editor = getSharedPreferences("favorite", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", true);
                            editor.apply();
                            String id = bundle.getString("id_movie");
                            Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(id).build();
                            getContentResolver().delete(uri, null, null);
                            Snackbar.make(buttonView, "Removed from Favorite",
                                    Snackbar.LENGTH_SHORT).show();
                        }

                    }
                }
        );

        getData();
        DeleteFromButton();
    }


    public void saveFavorite() {
        databaseHelper = new FavoriteDbHelper(activity);
        Movie.Results favorite = new Movie.Results();

        favorite.setTitle(detailTitle.getText().toString().trim());
        favorite.setBackdrop_path(bundle.getString(getString(R.string.intent_backdrop_path)));
        favorite.setOverview(detailDescription.getText().toString().trim());

        ContentValues values = new ContentValues();
        bundle = getIntent().getExtras();
        assert bundle != null;
        String title = bundle.getString("title_movie");
        String overview = bundle.getString(getString(R.string.intent_overview));
        String image = bundle.getString(getString(R.string.intent_backdrop_path));
        String id = bundle.getString("id_movie");
        values.put(FavoriteContract.FavoriteEntry._ID, id);
        values.put(FavoriteContract.FavoriteEntry.COLUMN_TITLE, title);
        values.put(FavoriteContract.FavoriteEntry.COLUMN_POSTER_PATH, image);
        values.put(FavoriteContract.FavoriteEntry.COLUMN_PLOT_SYNOPSIS, overview);
        Uri uri = getContentResolver().insert(FavoriteContract.FavoriteEntry.CONTENT_URI, values);
        if (uri != null) {
            Log.d(TAG, "Uri " + uri);
        }
    }

    void DeleteFromButton() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                results = getIntent().getParcelableExtra("EXTRA_FAVORITE");
                if (results != null) {
                    position = getIntent().getIntExtra("EXTRA_POSITION", 1);
                }
                String overview = Objects.requireNonNull(getIntent().getExtras()).getString("overview");
                databaseHelper = new FavoriteDbHelper(DetailActivity.this);
                databaseHelper.deleteFavorite(overview);

                String id = bundle.getString("id_movie");
                Uri uri = FavoriteContract.FavoriteEntry.CONTENT_URI.buildUpon().appendPath(id).build();
                getContentResolver().delete(uri, null, null);
                Toast.makeText(getApplicationContext(), "success remove from favorite", Toast.LENGTH_SHORT).show();

                Log.d(TAG, "Uri deleted ada dong " + uri);

            }
        });
    }

    void getData() {
        bundle = getIntent().getParcelableExtra("movies_key");
        progressBar.setVisibility(View.VISIBLE);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            progressBar.setVisibility(View.VISIBLE);
            detailDescription.setText(bundle.getString(getString(R.string.intent_overview)));
            detailTitle.setText(bundle.getString("title_movie"));
            detailTvTitle.setText(bundle.getString("name"));
            Picasso.get()
                    .load(Constant.BACKDROP_PATH + bundle.getString(getString(R.string.intent_backdrop_path)))
                    .placeholder(R.drawable.ic_launcher_background).into(detailImage);

            Objects.requireNonNull(getSupportActionBar()).setTitle(bundle.getString(getString(R.string.intent_title)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

}