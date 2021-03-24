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
    TimePicker mTimePicker;
    EditText mTxtTitle;
    Button mEditButton;
    Button mDeleteButton;
    CheckBox mCheckRecurring;
    CheckBox mCheckMon;
    CheckBox mCheckTue;
    CheckBox mCheckWed;
    CheckBox mCheckThu;
    CheckBox mCheckFri;
    CheckBox mCheckSat;
    CheckBox mCheckSun;
    LinearLayout mRecurringOptions;

    String mTitle;
    int mId, mHour, mMinute, mStarted, mRecurring, mSun, mMon, mTue, mWed, mThu, mFri, mSat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_update);

        mTimePicker = findViewById(R.id.update_timePicker);
        mTxtTitle = findViewById(R.id.update_title);
        mEditButton = findViewById(R.id.update_edit_AlarmButton);
        mDeleteButton = findViewById(R.id.update_delete_AlarmButton);
        mCheckRecurring = findViewById(R.id.update_Checkrecurring);
        mCheckMon = findViewById(R.id.update_checkMon);
        mCheckTue = findViewById(R.id.update_checkTue);
        mCheckWed = findViewById(R.id.update_checkWed);
        mCheckThu = findViewById(R.id.update_checkThu);
        mCheckFri = findViewById(R.id.update_checkFri);
        mCheckSat = findViewById(R.id.update_checkSat);
        mCheckSun = findViewById(R.id.update_checkSun);
        mRecurringOptions = findViewById(R.id.update_recurring_options);

        mCheckRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRecurringOptions.setVisibility(View.VISIBLE);
                } else {
                    mRecurringOptions.setVisibility(View.GONE);
                }
            }
        });

        mEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleAlarm();

                moveToAlarmListFragment();

            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mId = Integer.valueOf(getIntent().getStringExtra("id"));
                AlarmDatabaseHelper databaseHelper = new AlarmDatabaseHelper(AlarmUpdateActivity.this);
                databaseHelper.deleteOneRow(String.valueOf(mId));
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
            mTitle = getIntent().getStringExtra("title");
            mId = Integer.valueOf(getIntent().getStringExtra("id"));
            mHour = Integer.valueOf(getIntent().getStringExtra("hour"));
            mMinute = Integer.valueOf(getIntent().getStringExtra("minute"));
            mRecurring = Integer.valueOf(getIntent().getStringExtra("recurring"));
            mStarted = Integer.valueOf(getIntent().getStringExtra("started"));
            mMon = Integer.valueOf(getIntent().getStringExtra("mon"));
            mTue = Integer.valueOf(getIntent().getStringExtra("tue"));
            mWed = Integer.valueOf(getIntent().getStringExtra("wed"));
            mThu = Integer.valueOf(getIntent().getStringExtra("thu"));
            mFri = Integer.valueOf(getIntent().getStringExtra("fri"));
            mSat = Integer.valueOf(getIntent().getStringExtra("sat"));
            mSun = Integer.valueOf(getIntent().getStringExtra("sun"));

            mTxtTitle.setText(mTitle);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(mHour);
                mTimePicker.setMinute(mMinute);
            } else {
                // Earlier than API level 23 (Android 6.0 Marshmallow)
                mTimePicker.setCurrentHour(mHour);
                mTimePicker.setCurrentMinute(mMinute);
            }
            mCheckRecurring.setChecked(mRecurring == 1);

            mCheckMon.setChecked(mMon == 1);

            mCheckTue.setChecked(mTue == 1);

            mCheckWed.setChecked(mWed == 1);

            mCheckThu.setChecked(mThu == 1);

            mCheckFri.setChecked(mFri == 1);

            mCheckSat.setChecked(mSat == 1);

            mCheckSun.setChecked(mSun == 1);

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
        int intRecurring = mCheckRecurring.isChecked() ? 1 : 0;
        int intMon = mCheckMon.isChecked() ? 1 : 0;
        int intTue = mCheckTue.isChecked() ? 1 : 0;
        int intWed = mCheckWed.isChecked() ? 1 : 0;
        int intThu = mCheckThu.isChecked() ? 1 : 0;
        int intFri = mCheckFri.isChecked() ? 1 : 0;
        int intSat = mCheckSat.isChecked() ? 1 : 0;
        int intSun = mCheckSun.isChecked() ? 1 : 0;

        // Create an alarm DatabaseHelper to update alarm to database
        AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(this);
        alarmDatabaseHelper.updateData(
                String.valueOf(mId),
                String.valueOf(TimePickerUtil.getTimePickerHour(mTimePicker)),
                String.valueOf(TimePickerUtil.getTimePickerMinute(mTimePicker)),
                mTxtTitle.getText().toString(),
                String.valueOf(mStarted),
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
                    mHour, mMinute, mTitle, mStarted, mRecurring, mMon, mTue, mWed, mThu, mFri, mSat, mSun);
            alarm.schedule(this);
        }

    }
}