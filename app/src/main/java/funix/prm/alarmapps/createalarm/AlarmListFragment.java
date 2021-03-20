package funix.prm.alarmapps.createalarm;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;


public class AlarmListFragment extends Fragment implements OnToggleAlarmListener {
    private AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    private AlarmViewHolder alarmViewHolder;
    private RecyclerView recyclerView;
    private Button btnCreate;

    private AlarmDatabaseHelper dbHelper;
    private List<AlarmDatabaseHelper.Alarm> alarmList;


    public AlarmListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        recyclerView = rootView.findViewById(R.id.fragment_alarmList_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btnCreate = rootView.findViewById(R.id.fragment_alarmList_createBtn);

        dbHelper = new AlarmDatabaseHelper(getContext());
        alarmList = new ArrayList<>();

        storeDataToList();

        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(alarmList, this);
        recyclerView.setAdapter(alarmRecyclerViewAdapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateAlarmFragment fragment = new CreateAlarmFragment();
                FragmentTransaction ft = getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction();
                ft.replace(R.id.viewholder, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    @Override
    public void onToggle(AlarmDatabaseHelper.Alarm alarm) {
        if (alarm.getStarted() == 1) {
            alarm.cancelAlarm(getContext());
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarm.schedule(getContext());
            }
        }
    }

    private void storeDataToList() {
        Cursor cursor = dbHelper.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                AlarmDatabaseHelper.Alarm tmpAlarm =
                        new AlarmDatabaseHelper.Alarm(
                                cursor.getInt(1),
                                cursor.getInt(2),
                                cursor.getString(3),
                                cursor.getInt(4),
                                cursor.getInt(5),
                                cursor.getInt(6),
                                cursor.getInt(7),
                                cursor.getInt(8),
                                cursor.getInt(9),
                                cursor.getInt(10),
                                cursor.getInt(11),
                                cursor.getInt(12));
                alarmList.add(tmpAlarm);
            }
        }
    }

}