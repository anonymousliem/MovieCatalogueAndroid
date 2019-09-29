package com.anonymousliem.retrofit2.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.anonymousliem.retrofit2.Fragment.FavoriteFragment;
import com.anonymousliem.retrofit2.Fragment.MovieFragment;
import com.anonymousliem.retrofit2.Fragment.TvFragment;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.db.FavoriteContract;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {
    SearchView searchView;
    private Fragment pageContent = new MovieFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;

            switch (item.getItemId()) {
                case R.id.navigation_dashboard:

                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
                case R.id.navigation_home:

                    fragment = new MovieFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                            .commit();

                    return true;
                case R.id.navigation_favorite:

                    fragment = new FavoriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.content, fragment, fragment.getClass().getSimpleName())
                            .commit();
                    return true;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.content, pageContent).commit();
            return false;

        }
    };

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("ShowToast")
            public boolean onQueryTextSubmit(String movie) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", movie);
                startActivity(intent);
                return false;
            }


            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        final MenuItem myActionMenuItem2 = menu.findItem(R.id.action_search_tv);
        searchView = (SearchView) myActionMenuItem2.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("ShowToast")
            public boolean onQueryTextSubmit(String tv) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                intent.putExtra("query", tv);
                startActivity(intent);
                return false;
            }

            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        if (item.getItemId() == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.navigation_home);
        }
        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(FavoriteContract.FavoriteEntry.CONTENT_URI, true, myObserver);
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

        }
    }


}

