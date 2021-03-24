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

    private final Context mContext;
    private final Activity mActivity;
    private final LinearLayout mLinearLayout;
    private TextView mTxtAlarmClock;
    private TextView mTxtRecurringDays;
    private TextView mTxtAlarmTitle;
    private Switch mSwtStarted;
    private ImageView mImgRecurring;
    private ImageButton mBtnDelete;
    private OnToggleAlarmListener mToggleListener;

    public AlarmViewHolder(@NonNull View itemView, OnToggleAlarmListener toggleListener,
                           Context context, Activity activity) {
        super(itemView);

        mTxtAlarmClock = itemView.findViewById(R.id.item_txt_clockText);
        mTxtRecurringDays = itemView.findViewById(R.id.item_txt_recurringDays);
        mTxtAlarmTitle = itemView.findViewById(R.id.item_txt_alarmTitle);
        mSwtStarted = itemView.findViewById(R.id.item_swt_started);
        mImgRecurring = itemView.findViewById(R.id.item_img_recurring);
        mLinearLayout = itemView.findViewById(R.id.item_linearLayout);
        this.mContext = context;
        this.mActivity = activity;

        this.mToggleListener = toggleListener;
    }

    public TextView getmTxtAlarmClock() {
        return mTxtAlarmClock;
    }

    public void setmTxtAlarmClock(TextView mTxtAlarmClock) {
        this.mTxtAlarmClock = mTxtAlarmClock;
    }

    public TextView getmTxtRecurringDays() {
        return mTxtRecurringDays;
    }

    public void setmTxtRecurringDays(TextView mTxtRecurringDays) {
        this.mTxtRecurringDays = mTxtRecurringDays;
    }

    public TextView getmTxtAlarmTitle() {
        return mTxtAlarmTitle;
    }

    public void setmTxtAlarmTitle(TextView mTxtAlarmTitle) {
        this.mTxtAlarmTitle = mTxtAlarmTitle;
    }

    public Switch getmSwtStarted() {
        return mSwtStarted;
    }

    public void setmSwtStarted(Switch mSwtStarted) {
        this.mSwtStarted = mSwtStarted;
    }

    public ImageView getmImgRecurring() {
        return mImgRecurring;
    }

    public void setmImgRecurring(ImageView mImgRecurring) {
        this.mImgRecurring = mImgRecurring;
    }

    public ImageButton getmBtnDelete() {
        return mBtnDelete;
    }

    public void setmBtnDelete(ImageButton mBtnDelete) {
        this.mBtnDelete = mBtnDelete;
    }

    public OnToggleAlarmListener getmToggleListener() {
        return mToggleListener;
    }

    public void setmToggleListener(OnToggleAlarmListener mToggleListener) {
        this.mToggleListener = mToggleListener;
    }

    /**
     * Decorates the item view for each alarm and set touch listeners
     *
     * @param alarm
     */
    public void bind(AlarmDatabaseHelper.Alarm alarm) {
        String alarmClockText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        mTxtAlarmClock.setText(alarmClockText);
        mSwtStarted.setChecked(alarm.getStarted() == 1);

        mSwtStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mToggleListener.onToggle(alarm);
            }
        });


        if (alarm.getRecurring() == 1) {
            mImgRecurring.setImageResource(R.drawable.ic_repeat);
            mTxtRecurringDays.setText(alarm.getRecurringDays());
        } else {
            mImgRecurring.setImageResource(R.drawable.ic_repeat_one);
            mTxtRecurringDays.setText("One time alarm");
        }

        if (alarm.getTitle().length() != 0) {
            mTxtAlarmTitle.setText(alarm.getTitle());
        } else {
            mTxtAlarmTitle.setText("No title");
        }

        // Put alarm data to intent to move to AlarmUpdateActivity
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AlarmUpdateActivity.class);
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

                mActivity.startActivityForResult(intent, 1);
            }
        });

    }

}
