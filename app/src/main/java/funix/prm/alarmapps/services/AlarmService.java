package funix.prm.alarmapps.services;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.activities.AlarmOnActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.TITLE;
import static funix.prm.alarmapps.services.AlarmService.App.CHANNEL_ID;

/**
 * Creates service for activating the Alarm
 */
public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();
        // Creates references to:
        // Alarm sound
        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        // Vibrator
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * Sets what to do when alarm goes on:
     * 1. Plays alarm ring
     * 2. Starts vibration
     * 3. Puts on notification
     *
     * @param intent
     * @param flags
     * @param startId
     * @return START_STICKY
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, AlarmOnActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);


        // Retrieves alarm title
        String alarmTitle = intent.getStringExtra(TITLE) + " alarm";

        // Show notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Alarm is on!!!!!")
                .setSmallIcon(R.drawable.ic_alarm_on)
                .setContentIntent(pendingIntent)
                .build();

        // Start alarm rings repeated
        mediaPlayer.start();

        // Vibrate
        vibrator.vibrate(new long[]{0, 100, 1000}, 0);
        startForeground(1, notification);

        // Move to AlarmOnActivity
        notificationIntent.addFlags(FLAG_ACTIVITY_NEW_TASK);
        startActivity(notificationIntent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class App extends Application {
        // From API Oreo, we need to create an service channel to make a notification
        public static final String CHANNEL_ID = "ALARM_SERVICE_CHANNEL";

        @Override
        public void onCreate() {
            super.onCreate();

            // Create AlarmServiceChannel
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel serviceChannel = new NotificationChannel(
                        CHANNEL_ID,
                        "Alarm Service Channel",
                        NotificationManager.IMPORTANCE_DEFAULT
                );

                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}
