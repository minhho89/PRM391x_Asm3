package funix.prm.alarmapps.createalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AlarmDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Alarm.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "alarm_table";
    public static final String COL_ID = "_id";
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
                " (" + COL_ID + "INTEGER PRIMARY KEY, " +
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

    }
}
