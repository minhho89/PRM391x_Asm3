package funix.prm.alarmapps.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.createalarm.AlarmListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO temporary test fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        AlarmListFragment alarmListFragment = new AlarmListFragment();
        ft.add(R.id.viewholder, alarmListFragment).commit();
    }
}