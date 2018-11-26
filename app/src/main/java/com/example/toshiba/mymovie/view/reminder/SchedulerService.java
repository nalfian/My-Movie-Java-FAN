package com.example.toshiba.mymovie.view.reminder;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.model.DatesResponse;
import com.example.toshiba.mymovie.model.ResultsItem;
import com.example.toshiba.mymovie.view.activity.DetailActivity;
import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;

import java.util.List;
import java.util.Locale;
import java.util.Random;


public class SchedulerService extends GcmTaskService {

    public static String TAG = "upcomingMovies";

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG)) {
            loadMovie();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }

        return result;
    }

    private void loadMovie() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        if (locale.toString().equalsIgnoreCase("in_id")) {
            language = "id";
        }
        AndroidNetworking.get("https://api.themoviedb.org/3/movie/now_playing?api_key=221fbf2bcc939e0de03af53af4e1744a")
                .addQueryParameter("language", language)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(DatesResponse.class, new ParsedRequestListener<DatesResponse>() {
                    @Override
                    public void onResponse(DatesResponse response) {
                        displayMovies(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SchedulerService.this, getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showNotification(Context context, String title, String message, int notifId, ResultsItem item) {
        NotificationManager notificationManagerCompat =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("rating", item.getVoteAverage());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("date", item.getReleaseDate());
        intent.putExtra("overview", item.getOverview());
        intent.putExtra("backdroppath", "https://image.tmdb.org/t/p/w500" + item.getBackdropPath());
        intent.putExtra("poster", "https://image.tmdb.org/t/p/w500" + item.getPosterPath());
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context,
                        notifId,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "channelId")
                        .setSmallIcon(R.drawable.ic_notifications_active_24dp)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setSound(alarmSound);

        notificationManagerCompat.notify(notifId, builder.build());
    }

    public void displayMovies(DatesResponse datesResponse) {
        List<ResultsItem> items = datesResponse.getResults();
        int index = new Random().nextInt(items.size());
        ResultsItem item = items.get(index);
        String title = items.get(index).getTitle();
        String message = items.get(index).getOverview();
        int notifId = 200;

        showNotification(getApplicationContext(),
                title,
                message,
                notifId,
                item);
    }
}
