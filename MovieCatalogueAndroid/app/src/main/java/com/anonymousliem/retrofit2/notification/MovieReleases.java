package com.anonymousliem.retrofit2.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.anonymousliem.retrofit2.Activity.MainActivity;
import com.anonymousliem.retrofit2.Model.Movie;
import com.anonymousliem.retrofit2.R;
import com.anonymousliem.retrofit2.Retrofit.Api;
import com.anonymousliem.retrofit2.Retrofit.ApiInterface;
import com.anonymousliem.retrofit2.Util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieReleases extends BroadcastReceiver {
    public List<Movie.Results> listMovie = new ArrayList<>();

    public MovieReleases() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        ApiInterface apiInterface = Api.getUrl().create(ApiInterface.class);
        Call<Movie> call = apiInterface.getDiscoverMovie(Constant.KEY, "en-US", "1", Constant.DATE, Constant.DATE);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                listMovie = response.body().getResults();
                List<Movie.Results> items = response.body().getResults();
                int index = new Random().nextInt(items.size());
                int notifId = 104;
                String title = items.get(index).getTitle() + "Has Been Release Today";
                String message = items.get(index).getOverview();
                sendNotification(context, title, message, notifId);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.d("failed", "onFailure: " + t.toString());
            }
        });
    }

    private void sendNotification(Context context, String title, String desc, int id) {
        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(104, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        Notification notification = builder
                .setContentTitle(title)
                .setContentText(desc)
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.icon_notif)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);
    }




    /*public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MovieReleaseReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID_, intent, 0);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
        Toast.makeText(context, R.string.off_movie_release_reminder, Toast.LENGTH_SHORT).show();
    }*/


}
