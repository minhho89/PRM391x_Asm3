package funix.prm.alarmapps.activities;

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
import androidx.fragment.app.FragmentTransaction;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.createalarm.AlarmListFragment;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;
import funix.prm.alarmapps.utils.TimePickerUtil;

public class AlarmUpdateActivity extends AppCompatActivity {
    View rootView;
    TimePicker timePicker;
    EditText txtTitle;
    Button editButton;
    Button deleteButton;
    CheckBox recurring;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;
    LinearLayout recurringOptions;

    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        timePicker = findViewById(R.id.update_timePicker);
        txtTitle = findViewById(R.id.update_title);
        editButton = findViewById(R.id.update_edit_AlarmButton);
        deleteButton = findViewById(R.id.update_delete_AlarmButton);
        recurring = findViewById(R.id.update_Checkrecurring);
        mon = findViewById(R.id.update_checkMon);
        tue = findViewById(R.id.update_checkTue);
        wed = findViewById(R.id.update_checkWed);
        thu = findViewById(R.id.update_checkThu);
        fri = findViewById(R.id.update_checkFri);
        sat = findViewById(R.id.update_checkSat);
        sun = findViewById(R.id.update_checkSun);
        recurringOptions = findViewById(R.id.update_recurring_options);

        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
                // TODO: make an alarm object and insert to database
                scheduleAlarm();
                // TODO: Move to alarmList fragment
                moveToAlarmListFragment();

            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        getAndSetIntentData();

    }

    private void getAndSetIntentData() {
        if (getIntent().hasExtra("title")) {
            title = getIntent().getStringExtra("title");

            txtTitle.setText(title);
        }
    }

    /**
     * Moves to alarmListFragment
     */
    private void moveToAlarmListFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AlarmListFragment alarmListFragment = new AlarmListFragment();
        ft.replace(R.id.viewholder, alarmListFragment).commit();
    }

    /**
     * Creates an alarm object prior to user's time pick information,
     * then inserts to database
     */
    private void scheduleAlarm() {
        // Convert boolean to int value to put into database
        int intRecurring = recurring.isChecked() ? 1 : 0;
        int intMon = mon.isChecked() ? 1 : 0;
        int intTue = tue.isChecked() ? 1 : 0;
        int intWed = wed.isChecked() ? 1 : 0;
        int intThu = thu.isChecked() ? 1 : 0;
        int intFri = fri.isChecked() ? 1 : 0;
        int intSat = sat.isChecked() ? 1 : 0;
        int intSun = sun.isChecked() ? 1 : 0;

        // Create an alarm DatabaseHelper to add alarm to database
        AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(this);
        AlarmDatabaseHelper.Alarm alarm = new AlarmDatabaseHelper.Alarm(
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                txtTitle.getText().toString(),
                1,
                intRecurring,
                intMon,
                intTue,
                intWed,
                intThu,
                intFri,
                intSat,
                intSun
        );
        // TODO: update alarm to database


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm.schedule(this);
        }

    }
}