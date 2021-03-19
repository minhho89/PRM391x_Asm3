package funix.prm.alarmapps.data;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.Calendar;

import funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver;
import funix.prm.alarmapps.utils.DayUtil;

import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.FRIDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.MONDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.RECURRING;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.SATURDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.SUNDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.THURSDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.TITLE;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.TUESDAY;
import static funix.prm.alarmapps.broadcastReceiver.AlarmBroadcastReceiver.WEDNESDAY;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Alarm.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "alarm_table";
    public static final String COL_ID = "id";
    public static final String COL_HOUR = "hour";
    public static final String COL_MINUTE = "minute";
    public static final String COL_STARTED = "started";
    public static final String COL_RECURRING = "recurring";
    public static final String COL_MONDAY = "monday";
    public static final String COL_TUESDAY = "tuesday";
    public static final String COL_WEDNESDAY = "wednesday";
    public static final String COL_THURSDAY = "thursday";
    public static final String COL_FRIDAY = "friday";
    public static final String COL_SATURDAY = "saturday";
    public static final String COL_SUNDAY = "sunday";
    public static final String COL_TITLE = "title";
    private final Context context;

    public AlarmDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOUR + " INTEGER, " +
                COL_MINUTE + " INTEGER, " +
                COL_STARTED + " INTEGER, " +
                COL_RECURRING + " INTEGER, " +
                COL_MONDAY + " INTEGER, " +
                COL_TUESDAY + " INTEGER, " +
                COL_WEDNESDAY + " INTEGER, " +
                COL_THURSDAY + " INTEGER, " +
                COL_FRIDAY + " INTEGER, " +
                COL_SATURDAY + " INTEGER, " +
                COL_SUNDAY + " INTEGER, " +
                COL_TITLE + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Add new alarm to SQLite database
     */
    public void addAlarm(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_HOUR, alarm.getHour());
        cv.put(COL_MINUTE, alarm.getMinute());
        cv.put(COL_TITLE, alarm.getTitle());
        cv.put(COL_STARTED, alarm.getStarted());
        cv.put(COL_RECURRING, alarm.getRecurring());
        cv.put(COL_MONDAY, alarm.getMon());
        cv.put(COL_TUESDAY, alarm.getTue());
        cv.put(COL_WEDNESDAY, alarm.getWed());
        cv.put(COL_THURSDAY, alarm.getThu());
        cv.put(COL_FRIDAY, alarm.getFri());
        cv.put(COL_SATURDAY, alarm.getSat());
        cv.put(COL_SUNDAY, alarm.getSun());

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            // Fails to insert data
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    public static class Alarm {
        private int id, hour, minute;
        private String title;
        private int started, recurring;
        private int mon, tue, wed, thu, fri, sat, sun;

        public Alarm(int hour, int minute, String title, int started, int recurring,
                     int mon, int tue, int wed, int thu, int fri, int sat, int sun) {
            this.hour = hour;
            this.minute = minute;
            this.title = title;
            this.started = started;
            this.recurring = recurring;
            this.mon = mon;
            this.tue = tue;
            this.wed = wed;
            this.thu = thu;
            this.fri = fri;
            this.sat = sat;
            this.sun = sun;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getHour() {
            return hour;
        }

        public void setHour(int hour) {
            this.hour = hour;
        }

        public int getMinute() {
            return minute;
        }

        public void setMinute(int minute) {
            this.minute = minute;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getStarted() {
            return started;
        }

        public void setStarted(int started) {
            this.started = started;
        }

        public int getRecurring() {
            return recurring;
        }

        public void setRecurring(int recurring) {
            this.recurring = recurring;
        }

        public int getMon() {
            return mon;
        }

        public void setMon(int mon) {
            this.mon = mon;
        }

        public int getTue() {
            return tue;
        }

        public void setTue(int tue) {
            this.tue = tue;
        }

        public int getWed() {
            return wed;
        }

        public void setWed(int wed) {
            this.wed = wed;
        }

        public int getThu() {
            return thu;
        }

        public void setThu(int thu) {
            this.thu = thu;
        }

        public int getFri() {
            return fri;
        }

        public void setFri(int fri) {
            this.fri = fri;
        }

        public int getSat() {
            return sat;
        }

        public void setSat(int sat) {
            this.sat = sat;
        }

        public int getSun() {
            return sun;
        }

        public void setSun(int sun) {
            this.sun = sun;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        public void schedule(Context context) {

            // Get AlarmManager instance
            AlarmManager alarmManager =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            // Intent part
            Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
            intent.putExtra(RECURRING, recurring);
            intent.putExtra(MONDAY, mon);
            intent.putExtra(TUESDAY, tue);
            intent.putExtra(WEDNESDAY, wed);
            intent.putExtra(THURSDAY, thu);
            intent.putExtra(FRIDAY, fri);
            intent.putExtra(SATURDAY, sat);
            intent.putExtra(SUNDAY, sun);
            intent.putExtra(TITLE, title);

            // Create PendingIntent
            PendingIntent alarmPendingIntent =
                    PendingIntent.getBroadcast(context, id, intent, 0);

            // Calendar
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // If alarm time has already passed, increment day by 1
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            }

            if (recurring == 0) {
                // If alarm is not a recurring alarm, set alarm 1 time
                String toastText = null;
                try {
                    toastText = String.format("One time alarm %s scheduled for %s at %02d:%02d",
                            title, DayUtil.toDay(Calendar.DAY_OF_WEEK), hour, minute);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent
                );
            } else {
                // If alarm is a recurring alarm, set alarm repeat
                String toastText = String.format("Recurring alarm %s set for %s at %02d:%02d",
                        title, getRecurringDays(), hour, minute);
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

                // Interval in Millis
                final long RUN_DAILY = 24 * 60 * 60 * 1000;
                alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(), // triggers at
                        RUN_DAILY, //interval Mills
                        alarmPendingIntent
                );
            }
        }

        /**
         * Generate a String of day of week for recurring alarm message
         *
         * @return
         */
        private String getRecurringDays() {
            if (recurring == 0) {
                return null;
            }

            String days = "";
            if (mon == 1) {
                days += "Mo ";
            }
            if (tue == 1) {
                days += "Tu ";
            }
            if (wed == 1) {
                days += "We ";
            }
            if (thu == 1) {
                days += "Th ";
            }
            if (fri == 1) {
                days += "Fr ";
            }
            if (sat == 1) {
                days += "Sa ";
            }
            if (sun == 1) {
                days += "Su ";
            }
            return days;
        }

        /**
         * Cancels alarm (used for toogle button)
         *
         * @param context
         */
        public void cancelAlarm(Context context) {
            AlarmManager alarmManager =
                    (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
            alarmManager.cancel(alarmPendingIntent); // cancel alarm
            this.started = 0; // set started of alarm object value to 0

            String toastText = String.format("Alarm cancelled");
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

        }
    }

}
