package funix.prm.alarmapps.createalarm;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.utils.TimePickerUtil;

/**
 * Creates an alarm (including data) after user picked time by time picker.
 * Retains data by using ViewModel.
 */
public class CreateAlarmFragment extends Fragment {
    View rootView;
    TimePicker timePicker;
    EditText title;
    Button scheduleAlarm;
    CheckBox recurring;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;
    LinearLayout recurringOptions;


    // TODO add CreateAlarmViewModel
    // private CreateAlarmViewModel createAlarmViewModel;


    public CreateAlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.fragment_create_alarm, null);

        timePicker = rootView.findViewById(R.id.fragment_createalarm_timePicker);
        title = rootView.findViewById(R.id.fragment_createalarm_title);
        scheduleAlarm = rootView.findViewById(R.id.fragment_createalarm_scheduleAlarmButton);
        recurring = rootView.findViewById(R.id.fragment_createalarm_Checkrecurring);
        mon = rootView.findViewById(R.id.fragment_createalarm_checkMon);
        tue = rootView.findViewById(R.id.fragment_createalarm_checkTue);
        wed = rootView.findViewById(R.id.fragment_createalarm_checkWed);
        thu = rootView.findViewById(R.id.fragment_createalarm_checkThu);
        fri = rootView.findViewById(R.id.fragment_createalarm_checkFri);
        sat = rootView.findViewById(R.id.fragment_createalarm_checkSat);
        sun = rootView.findViewById(R.id.fragment_createalarm_checkSun);
        recurringOptions = rootView.findViewById(R.id.fragment_createalarm_recurring_options);

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

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO: make an alarm object and insert to database
                scheduleAlarm();
                // TODO: Move to alarmList fragment
                moveToAlarmListFragment();

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    /**
     * Moves to alarmListFragment
     */
    private void moveToAlarmListFragment() {
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

        Toast.makeText(getContext(), intRecurring + "", Toast.LENGTH_SHORT).show();

        // Create an alarm DatabaseHelper to add alarm to database
        AlarmDatabaseHelper alarmDatabaseHelper = new AlarmDatabaseHelper(getContext());
        AlarmDatabaseHelper.Alarm alarm = new AlarmDatabaseHelper.Alarm(
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                title.getText().toString(),
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
        // TODO: put Alarm to alarmManager to make an alarm for system
        alarm.schedule(alarm, getContext());

    }
}