package funix.prm.alarmapps.createalarm;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Random;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;
import funix.prm.alarmapps.utils.TimePickerUtil;

/**
 * Creates an alarm (including data) after user picked time by time picker.
 * Retains data by using ViewModel.
 */
public class CreateAlarmFragment extends Fragment {
    View mRootView;
    TimePicker mTimePicker;
    EditText mTitle;
    Button mScheduleAlarm;
    CheckBox mRecurring;
    CheckBox mMon;
    CheckBox mTue;
    CheckBox mWed;
    CheckBox mThu;
    CheckBox mFri;
    CheckBox mSat;
    CheckBox mSun;
    LinearLayout mRecurringOptions;

    public CreateAlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_create_alarm, null);

        mTimePicker = mRootView.findViewById(R.id.fragment_createalarm_timePicker);
        mTitle = mRootView.findViewById(R.id.fragment_createalarm_title);
        mScheduleAlarm = mRootView.findViewById(R.id.fragment_createalarm_scheduleAlarmButton);
        mRecurring = mRootView.findViewById(R.id.fragment_createalarm_Checkrecurring);
        mMon = mRootView.findViewById(R.id.fragment_createalarm_checkMon);
        mTue = mRootView.findViewById(R.id.fragment_createalarm_checkTue);
        mWed = mRootView.findViewById(R.id.fragment_createalarm_checkWed);
        mThu = mRootView.findViewById(R.id.fragment_createalarm_checkThu);
        mFri = mRootView.findViewById(R.id.fragment_createalarm_checkFri);
        mSat = mRootView.findViewById(R.id.fragment_createalarm_checkSat);
        mSun = mRootView.findViewById(R.id.fragment_createalarm_checkSun);
        mRecurringOptions = mRootView.findViewById(R.id.fragment_createalarm_recurring_options);

        mRecurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mRecurringOptions.setVisibility(View.VISIBLE);
                } else {
                    mRecurringOptions.setVisibility(View.GONE);
                }
            }
        });

        mScheduleAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: make an alarm object and insert to database
                scheduleAlarm();
                // TODO: Move to alarmList fragment
                moveToAlarmListFragment();

            }
        });

        // Inflate the layout for this fragment
        return mRootView;
    }

    /**
     * Moves to alarmListFragment
     */
    private void moveToAlarmListFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        AlarmListFragment alarmListFragment = new AlarmListFragment();
        ft.replace(R.id.viewholder, alarmListFragment).commit();
    }

    /**
     * Creates an alarm object prior to user's time pick information,
     * then inserts to database
     */
    private void scheduleAlarm() {
        // Convert boolean to int value to put into database
        int intRecurring = mRecurring.isChecked() ? 1 : 0;
        int intMon = mMon.isChecked() ? 1 : 0;
        int intTue = mTue.isChecked() ? 1 : 0;
        int intWed = mWed.isChecked() ? 1 : 0;
        int intThu = mThu.isChecked() ? 1 : 0;
        int intFri = mFri.isChecked() ? 1 : 0;
        int intSat = mSat.isChecked() ? 1 : 0;
        int intSun = mSun.isChecked() ? 1 : 0;

        // Create an alarm DatabaseHelper to add alarm to database
        AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(getContext());
        AlarmDatabaseHelper.Alarm alarm = new AlarmDatabaseHelper.Alarm(
                new Random().nextInt(Integer.MAX_VALUE),
                TimePickerUtil.getTimePickerHour(mTimePicker),
                TimePickerUtil.getTimePickerMinute(mTimePicker),
                mTitle.getText().toString(),
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
        // Add alarm to database
        alarmDatabaseHelper.addAlarm(alarm);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarm.schedule(getContext());
        }

    }
}