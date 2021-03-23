package funix.prm.alarmapps.activities;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;
import funix.prm.alarmapps.services.AlarmService;

public class AlarmOnActivity extends AppCompatActivity {
    Button dismissBtn;
    Button snoozeBtn;
    ImageView clockImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_on);

        dismissBtn = findViewById(R.id.fragment_alarm_dismiss_btn);
        snoozeBtn = findViewById(R.id.fragment_alarm_snooze_btn);
        clockImg = findViewById(R.id.fragment_alarm_imageView);

        dismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Stop service
                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        snoozeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                // Increases 10 min from current time
                calendar.add(Calendar.MINUTE, 10);
                AlarmDatabaseHelper.Alarm alarm = new AlarmDatabaseHelper.Alarm(
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        "Snooze",
                        1,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0,
                        0
                );

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarm.schedule(getApplicationContext());
                }

                Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });

        rotateClock();

    }

    /**
     * Animate the clock image
     */
    private void rotateClock() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(clockImg, "rotation",
                0f, 20f, -20f);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(800);
        objectAnimator.start();
    }
}