package com.anonymousliem.retrofit2.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.anonymousliem.retrofit2.Fragment.SettingsFragment;
import com.anonymousliem.retrofit2.R;

public class SettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        android.support.v7.preference.PreferenceManager
                .setDefaultValues(this, R.xml.settings_ui, false);


    }

}
