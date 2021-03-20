package funix.prm.alarmapps.createalarm;

import funix.prm.alarmapps.data.AlarmDatabaseHelper;

public interface OnToggleAlarmListener {
    void onToggle(AlarmDatabaseHelper.Alarm alarm);
}
