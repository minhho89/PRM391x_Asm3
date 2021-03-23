package funix.prm.alarmapps.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;

import funix.prm.alarmapps.services.AlarmService;
import funix.prm.alarmapps.services.RescheduleAlarmService;

import static android.content.Intent.ACTION_BOOT_COMPLETED;

/**
 * Handles the broadcast from the AlarmManager
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    public static final String MONDAY = "MONDAY";
    public static final String TUESDAY = "TUESDAY";
    public static final String WEDNESDAY = "WEDNESDAY";
    public static final String THURSDAY = "THURSDAY";
    public static final String FRIDAY = "FRIDAY";
    public static final String SATURDAY = "SATURDAY";
    public static final String SUNDAY = "SUNDAY";
    public static final String RECURRING = "RECURRING";
    public static final String TITLE = "TITLE";

    /**
     * Handles when the BroadcastReceiver receives the broadcast relates to alarm
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // When user resets device, all schedule will be gone,
        // so we need to re-schedule the alarms in case device completed boots.
        if (ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            String toastText = "Alarm reboot";
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            startRescheduleAlarmsService(context);
        } else {
            String toastText = "Alarm received";
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

            // If alarm is not a recurring alarm then start the alarm service right away.
            if (!intent.getBooleanExtra(RECURRING, false)) {
                startAlarmService(context, intent);
            } else {
                // If alarm is a recurring alarm, check that alarm is today or not
                // only if the alarm is today, then start the alarm service.
                if (alarmIsToday(intent)) {
                    startAlarmService(context, intent);
                }
            }
        }
    }


    /**
     * Checks if alarm day is the current day of not
     *
     * @param intent
     * @return
     */
    private boolean alarmIsToday(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int today = calendar.get(Calendar.DAY_OF_WEEK); // Get day of week of current day

        switch (today) {
            case Calendar.MONDAY:
                return (intent.getBooleanExtra(MONDAY, false));
            case Calendar.TUESDAY:
                return (intent.getBooleanExtra(TUESDAY, false));
            case Calendar.WEDNESDAY:
                return (intent.getBooleanExtra(WEDNESDAY, false));
            case Calendar.THURSDAY:
                return (intent.getBooleanExtra(THURSDAY, false));
            case Calendar.FRIDAY:
                return (intent.getBooleanExtra(FRIDAY, false));
            case Calendar.SATURDAY:
                return (intent.getBooleanExtra(SATURDAY, false));
            case Calendar.SUNDAY:
                return (intent.getBooleanExtra(SUNDAY, false));
        }
        return false;
    }

    /**
     * Starts the AlarmService
     *
     * @param context
     * @param intent
     */
    private void startAlarmService(Context context, Intent intent) {
        Intent intentService = new Intent(context, AlarmService.class);
        intentService.putExtra(TITLE, intent.getStringExtra(TITLE));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }
    }

    /**
     * Reschedules all the AlarmService
     *
     * @param context
     */
    private void startRescheduleAlarmsService(Context context) {
        Intent intentService = new Intent(context, RescheduleAlarmService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intentService);
        } else {
            context.startService(intentService);
        }

    }
}
