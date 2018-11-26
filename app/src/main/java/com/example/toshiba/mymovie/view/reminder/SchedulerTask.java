package com.example.toshiba.mymovie.view.reminder;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

public class SchedulerTask {
    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context) {
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(3 * 60 * 1000)
                .setFlex(10)
                .setTag(SchedulerService.TAG)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelTask() {
        if (mGcmNetworkManager != null) {
            mGcmNetworkManager.cancelTask(SchedulerService.TAG, SchedulerService.class);
        }
    }
}
