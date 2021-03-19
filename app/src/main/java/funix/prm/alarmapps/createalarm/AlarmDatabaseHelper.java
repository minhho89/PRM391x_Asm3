package funix.prm.alarmapps.createalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

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

    void addAlarm(int hour, int minute, String title, int started, int recurring,
                  int mon, int tue, int wed, int thu, int fri, int sat, int sun) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_HOUR, hour);
        cv.put(COL_MINUTE, minute);
        cv.put(COL_TITLE, title);
        cv.put(COL_STARTED, started);
        cv.put(COL_RECURRING, recurring);
        cv.put(COL_MONDAY, mon);
        cv.put(COL_TUESDAY, tue);
        cv.put(COL_WEDNESDAY, wed);
        cv.put(COL_THURSDAY, thu);
        cv.put(COL_FRIDAY, fri);
        cv.put(COL_SATURDAY, sat);
        cv.put(COL_SUNDAY, sun);


        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            // Fails to insert data
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();
        }


    }
}
