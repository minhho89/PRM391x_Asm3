package funix.prm.alarmapps.createalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import funix.prm.alarmapps.R;
import funix.prm.alarmapps.activities.AlarmUpdateActivity;
import funix.prm.alarmapps.data.AlarmDatabaseHelper;

/**
 * Viewholder for AlarmRecyclerViewAdapter
 */
public class AlarmViewHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final Activity activity;

    private TextView txtAlarmClock;
    private TextView txtRecurringDays;
    private TextView txtAlarmTitle;
    private Switch swtStarted;
    private ImageView imgRecurring;
    private ImageButton btnDelete;
    private final LinearLayout linearLayout;
    private OnToggleAlarmListener toggleListener;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener toggleListener,
                           Context context, Activity activity) {
        super(itemView);

        txtAlarmClock = itemView.findViewById(R.id.item_txt_clockText);
        txtRecurringDays = itemView.findViewById(R.id.item_txt_recurringDays);
        txtAlarmTitle = itemView.findViewById(R.id.item_txt_alarmTitle);
        swtStarted = itemView.findViewById(R.id.item_swt_started);
        imgRecurring = itemView.findViewById(R.id.item_img_recurring);
        linearLayout = itemView.findViewById(R.id.item_linearLayout);
        this.context = context;
        this.activity = activity;

        this.toggleListener = toggleListener;
    }

    public TextView getTxtAlarmClock() {
        return txtAlarmClock;
    }

    public void setTxtAlarmClock(TextView txtAlarmClock) {
        this.txtAlarmClock = txtAlarmClock;
    }

    public TextView getTxtRecurringDays() {
        return txtRecurringDays;
    }

    public void setTxtRecurringDays(TextView txtRecurringDays) {
        this.txtRecurringDays = txtRecurringDays;
    }

    public TextView getTxtAlarmTitle() {
        return txtAlarmTitle;
    }

    public void setTxtAlarmTitle(TextView txtAlarmTitle) {
        this.txtAlarmTitle = txtAlarmTitle;
    }

    public Switch getSwtStarted() {
        return swtStarted;
    }

    public void setSwtStarted(Switch swtStarted) {
        this.swtStarted = swtStarted;
    }

    public ImageView getImgRecurring() {
        return imgRecurring;
    }

    public void setImgRecurring(ImageView imgRecurring) {
        this.imgRecurring = imgRecurring;
    }

    public ImageButton getBtnDelete() {
        return btnDelete;
    }

    public void setBtnDelete(ImageButton btnDelete) {
        this.btnDelete = btnDelete;
    }

    public OnToggleAlarmListener getToggleListener() {
        return toggleListener;
    }

    public void setToggleListener(OnToggleAlarmListener toggleListener) {
        this.toggleListener = toggleListener;
    }

    /**
     * Decorates the item view for each alarm and set touch listeners
     *
     * @param alarm
     */
    public void bind(AlarmDatabaseHelper.Alarm alarm) {
        String alarmClockText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        txtAlarmClock.setText(alarmClockText);
        swtStarted.setChecked(alarm.getStarted() == 1);

        swtStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleListener.onToggle(alarm);
            }
        });


        if (alarm.getRecurring() == 1) {
            imgRecurring.setImageResource(R.drawable.ic_repeat);
            txtRecurringDays.setText(alarm.getRecurringDays());
        } else {
            imgRecurring.setImageResource(R.drawable.ic_repeat_one);
            txtRecurringDays.setText("One time alarm");
        }

        if (alarm.getTitle().length() != 0) {
            txtAlarmTitle.setText(alarm.getTitle());
        } else {
            txtAlarmTitle.setText("No title");
        }

        // Put alarm data to intent to move to AlarmUpdateActivity
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlarmUpdateActivity.class);
                intent.putExtra("id", String.valueOf(alarm.getId()));
                intent.putExtra("hour", String.valueOf(alarm.getHour()));
                intent.putExtra("minute", String.valueOf(alarm.getMinute()));
                intent.putExtra("title", String.valueOf(alarm.getTitle()));
                intent.putExtra("recurring", String.valueOf(alarm.getRecurring()));
                intent.putExtra("started", String.valueOf(alarm.getStarted()));
                intent.putExtra("mon", String.valueOf(alarm.getMon()));
                intent.putExtra("tue", String.valueOf(alarm.getTue()));
                intent.putExtra("wed", String.valueOf(alarm.getWed()));
                intent.putExtra("thu", String.valueOf(alarm.getThu()));
                intent.putExtra("fri", String.valueOf(alarm.getFri()));
                intent.putExtra("sat", String.valueOf(alarm.getSat()));
                intent.putExtra("sun", String.valueOf(alarm.getSun()));

                activity.startActivityForResult(intent, 1);
            }
        });

    }

}
