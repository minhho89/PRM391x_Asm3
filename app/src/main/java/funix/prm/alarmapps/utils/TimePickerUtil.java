package funix.prm.alarmapps.utils;

import android.os.Build;
import android.widget.TimePicker;

/**
 * Helps to pick time and deals with differences of Android API versions
 */
public class TimePickerUtil {
    /**
     * Picks hour from timePicker dues to Android API version
     *
     * @param timePicker timepicker
     * @return picked hour
     */
    public static int getTimePickerHour(TimePicker timePicker) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getHour();
        } else {
            // Earlier than API level 23 (Android 6.0 Marshmallow)
            return timePicker.getCurrentHour();
        }
    }


    /**
     * Picks minute from timePicker dues to Android API version
     *
     * @param timePicker timepicker
     * @return picked minute
     */
    public static int getTimePickerMinute(TimePicker timePicker) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return timePicker.getMinute();
        } else {
            // Earlier than API level 23 (Android 6.0 Marshmallow)
            return timePicker.getCurrentMinute();
        }
    }

}
