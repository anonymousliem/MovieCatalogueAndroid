package com.anonymousliem.retrofit2.Fragment;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.notification.AlarmReceiver;
import com.anonymousliem.retrofit2.notification.MovieReleases;

public class SettingsFragment extends PreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.settings_ui, s);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Preference dailyReminder = findPreference("Daily_Reminder");
        if (dailyReminder != null) {
            dailyReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object newObject) {
                    boolean isChecked = (Boolean) newObject;
                    if (isChecked) {
                        java.util.Calendar cal = java.util.Calendar.getInstance();
                        cal.set(Calendar.HOUR_OF_DAY, 7);
                        cal.set(Calendar.MINUTE, 00);
                        cal.set(Calendar.SECOND, 00);
                        Intent notificationIntent = new Intent(getActivity(), AlarmReceiver.class);
                        PendingIntent broadcast = PendingIntent.getBroadcast(getActivity(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast);

                        Toast.makeText(getContext(), "Notification is Active", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Notification is NOT Active", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                }
            });
        }
        Preference ReleaseReminder = findPreference("Movie_Release");
        if (ReleaseReminder != null) {
            ReleaseReminder.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference arg0, Object newObject) {
                    boolean isChecked = (Boolean) newObject;
                    if (isChecked) {
                        setAlarm(getContext(), "typeRelease", "timeRelease", "release today");
                    } else {
                        Toast.makeText(getContext(), "MovieRelease is NOT Active", Toast.LENGTH_SHORT).show();

                    }
                    return true;
                }
            });
        }
    }

    public void setAlarm(Context context, String type, String time, String message) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(android.icu.util.Calendar.HOUR_OF_DAY, 8);
        cal.set(android.icu.util.Calendar.MINUTE, 00);
        cal.set(android.icu.util.Calendar.SECOND, 00);
        Intent intent = new Intent(context, MovieReleases.class);
        intent.putExtra("messageRelease", message);
        intent.putExtra("typeRelease", type);
        intent.putExtra("timeRelease", time);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 104, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            //    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

        Toast.makeText(context, "MovieRelease set alarm berhasil", Toast.LENGTH_SHORT).show();
    }

}