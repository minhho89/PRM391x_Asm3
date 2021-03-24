package funix.prm.alarmapps.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;
import funix.prm.alarmapps.utils.TimePickerUtil;

/**
 * Handles update and delete one alarm from database
 */
public class AlarmUpdateActivity extends AppCompatActivity {
    TimePicker timePicker;
    EditText txtTitle;
    Button editButton;
    Button deleteButton;
    CheckBox checkRecurring;
    CheckBox checkMon;
    CheckBox checkTue;
    CheckBox checkWed;
    CheckBox checkThu;
    CheckBox checkFri;
    CheckBox checkSat;
    CheckBox checkSun;
    LinearLayout recurringOptions;

    String title;
    int id, hour, minute, started, recurring, sun, mon, tue, wed, thu, fri, sat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        timePicker = findViewById(R.id.update_timePicker);
        txtTitle = findViewById(R.id.update_title);
        editButton = findViewById(R.id.update_edit_AlarmButton);
        deleteButton = findViewById(R.id.update_delete_AlarmButton);
        checkRecurring = findViewById(R.id.update_Checkrecurring);
        checkMon = findViewById(R.id.update_checkMon);
        checkTue = findViewById(R.id.update_checkTue);
        checkWed = findViewById(R.id.update_checkWed);
        checkThu = findViewById(R.id.update_checkThu);
        checkFri = findViewById(R.id.update_checkFri);
        checkSat = findViewById(R.id.update_checkSat);
        checkSun = findViewById(R.id.update_checkSun);
        recurringOptions = findViewById(R.id.update_recurring_options);

        checkRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recurringOptions.setVisibility(View.VISIBLE);
                } else {
                    recurringOptions.setVisibility(View.GONE);
                }
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();

                moveToAlarmListFragment();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = Integer.valueOf(getIntent().getStringExtra("id"));
                AlarmDatabaseHelper databaseHelper = new AlarmDatabaseHelper(AlarmUpdateActivity.this);
                databaseHelper.deleteOneRow(String.valueOf(id));
                finish();

                moveToAlarmListFragment();
            }
        });


        getAndSetIntentData();

    }

    /**
     * Takes intent data of an alarm from MainActivity
     */
    private void getAndSetIntentData() {
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");
            id = Integer.valueOf(getIntent().getStringExtra("id"));
            hour = Integer.valueOf(getIntent().getStringExtra("hour"));
            minute = Integer.valueOf(getIntent().getStringExtra("minute"));
            recurring = Integer.valueOf(getIntent().getStringExtra("recurring"));
            started = Integer.valueOf(getIntent().getStringExtra("started"));
            mon = Integer.valueOf(getIntent().getStringExtra("mon"));
            tue = Integer.valueOf(getIntent().getStringExtra("tue"));
            wed = Integer.valueOf(getIntent().getStringExtra("wed"));
            thu = Integer.valueOf(getIntent().getStringExtra("thu"));
            fri = Integer.valueOf(getIntent().getStringExtra("fri"));
            sat = Integer.valueOf(getIntent().getStringExtra("sat"));
            sun = Integer.valueOf(getIntent().getStringExtra("sun"));

            txtTitle.setText(title);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                timePicker.setHour(hour);
                timePicker.setMinute(minute);
            } else {
                // Earlier than API level 23 (Android 6.0 Marshmallow)
                timePicker.setCurrentHour(hour);
                timePicker.setCurrentMinute(minute);
            }
            checkRecurring.setChecked(recurring == 1);

            checkMon.setChecked(mon == 1);

            checkTue.setChecked(tue == 1);

            checkWed.setChecked(wed == 1);

            checkThu.setChecked(thu == 1);

            checkFri.setChecked(fri == 1);

            checkSat.setChecked(sat == 1);

            checkSun.setChecked(sun == 1);

        }
    }

    /**
     * Moves to alarmListFragment
     */
    private void moveToAlarmListFragment() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

    /**
     * Creates an alarm object prior to user's time pick information,
     * then inserts to database
     */
    private void scheduleAlarm() {
        // Convert boolean to int value to put into database
        int intRecurring = checkRecurring.isChecked() ? 1 : 0;
        int intMon = checkMon.isChecked() ? 1 : 0;
        int intTue = checkTue.isChecked() ? 1 : 0;
        int intWed = checkWed.isChecked() ? 1 : 0;
        int intThu = checkThu.isChecked() ? 1 : 0;
        int intFri = checkFri.isChecked() ? 1 : 0;
        int intSat = checkSat.isChecked() ? 1 : 0;
        int intSun = checkSun.isChecked() ? 1 : 0;

        // Create an alarm DatabaseHelper to update alarm to database
        AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(this);
        alarmDatabaseHelper.updateData(
                String.valueOf(id),
                String.valueOf(TimePickerUtil.getTimePickerHour(timePicker)),
                String.valueOf(TimePickerUtil.getTimePickerMinute(timePicker)),
                txtTitle.getText().toString(),
                String.valueOf(started),
                String.valueOf(intRecurring),
                String.valueOf(intMon),
                String.valueOf(intTue),
                String.valueOf(intWed),
                String.valueOf(intThu),
                String.valueOf(intFri),
                String.valueOf(intSat),
                String.valueOf(intSun));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            AlarmDatabaseHelper.Alarm alarm = new AlarmDatabaseHelper.Alarm(
                    hour, minute, title, started, recurring, mon, tue, wed, thu, fri, sat, sun);
            alarm.schedule(this);
        }

    }
}