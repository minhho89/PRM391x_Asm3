package funix.prm.alarmapps.createalarm;

import funix.prm.alarmapps.data.AlarmDatabaseHelper;

/**
 * Handles interface method for switching toggle button of every alarm
 */
public interface OnToggleAlarmListener {
    void onToggle(AlarmDatabaseHelper.Alarm alarm);
}
