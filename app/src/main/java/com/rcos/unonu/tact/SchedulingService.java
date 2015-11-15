package com.rcos.unonu.tact;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class SchedulingService extends IntentService {

    public SchedulingService() {
        super("SchedulingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AudioVolume audioVolume = new AudioVolume();
        audioVolume.start();
        int volumeMax = audioVolume.getAmplitude();
        audioVolume.stop();

        Context mContext = getApplicationContext();
        AudioManager audioManager = (AudioManager) mContext.getSystemService(mContext.AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(audioManager.STREAM_RING);
        audioManager.setStreamVolume(audioManager.STREAM_RING, volumeMax * maxVolume, audioManager.FLAG_SHOW_UI);
        maxVolume = audioManager.getStreamMaxVolume(audioManager.STREAM_NOTIFICATION);
        audioManager.setStreamVolume(audioManager.STREAM_NOTIFICATION, volumeMax * maxVolume, audioManager.FLAG_SHOW_UI);

        // Release the wake lock provided by the BroadcastReceiver.
        AlarmReceiver.completeWakefulIntent(intent);
    }
}
