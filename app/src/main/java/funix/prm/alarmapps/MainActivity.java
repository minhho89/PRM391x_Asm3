package funix.prm.alarmapps;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import funix.prm.alarmapps.createalarm.CreateAlarmFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO temporary test fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        CreateAlarmFragment alarmFragment = new CreateAlarmFragment();
        ft.add(R.id.viewholder, alarmFragment).commit();
    }
}